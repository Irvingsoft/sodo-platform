package cool.sodo.catkin.base.generator.impl;

import cool.sodo.catkin.base.entity.Result;
import cool.sodo.catkin.base.entity.ResultCode;
import cool.sodo.catkin.base.entity.SegmentId;
import cool.sodo.catkin.base.generator.IdGenerator;
import cool.sodo.catkin.base.service.SegmentIdService;
import cool.sodo.catkin.base.util.NamedThreadFactory;
import cool.sodo.common.base.exception.SoDoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TimeChaser
 * @date 2021/9/12 10:38
 */
public class CacheIdGenerator implements IdGenerator {

    protected String bizType;
    protected SegmentIdService segmentIdService;
    protected volatile SegmentId current;
    protected volatile SegmentId next;
    private volatile boolean loadingNext;
    private final Object lock = new Object();
    private final ExecutorService executorService =
            new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new NamedThreadFactory("catkin-generator"));

    public CacheIdGenerator(String bizType, SegmentIdService segmentIdService) {
        this.bizType = bizType;
        this.segmentIdService = segmentIdService;
        loadCurrent();
    }

    public synchronized void loadCurrent() {

        if (current == null || !current.useful()) {
            if (next == null) {
                this.current = querySegmentId();
            } else {
                current = next;
                next = null;
            }
        }
    }

    private SegmentId querySegmentId() {

        String message = null;
        try {
            SegmentId segmentId = segmentIdService.getNextSegmentId(bizType);
            if (segmentId != null) {
                return segmentId;
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
        throw new SoDoException("error query segmentId: " + message);
    }

    public void loadNext() {

        if (next == null && !loadingNext) {
            synchronized (lock) {
                if (next == null && !loadingNext) {
                    loadingNext = true;
                    executorService.submit(() -> {
                        try {
                            next = querySegmentId();
                        } finally {
                            loadingNext = false;
                        }
                    });
                }
            }
        }
    }

    @Override
    public Long nextId() {

        while (true) {
            if (current == null) {
                loadCurrent();
                continue;
            }
            Result result = current.nextId();
            if (result.getCode() == ResultCode.OVER) {
                loadCurrent();
            } else {
                if (result.getCode() == ResultCode.LOADING) {
                    loadNext();
                }
                return result.getId();
            }
        }
    }

    @Override
    public List<Long> nextId(Integer batchSize) {

        ArrayList<Long> ids = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            ids.add(nextId());
        }
        return ids;
    }
}
