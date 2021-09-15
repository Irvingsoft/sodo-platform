package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.starter.domain.Schedule;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.ScheduleMapper;
import cool.sodo.goods.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleServiceImpl implements ScheduleService {

    public static final String ERROR_SELECT = "日程不存在！";
    public static final String ERROR_INSERT = "日程新增失败！";
    public static final String ERROR_DELETE = "日程删除失败！";
    public static final String ERROR_UPDATE = "日程更新失败！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_ALL = 1;

    @Resource
    private ScheduleMapper scheduleMapper;

    private LambdaQueryWrapper<Schedule> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = Wrappers.lambdaQuery();
        scheduleLambdaQueryWrapper.select(Schedule::getOpenHour, Schedule::getOpenMinute
                , Schedule::getCloseHour, Schedule::getCloseMinute);

        switch (type) {
            case SELECT_ALL: {
                scheduleLambdaQueryWrapper.select(Schedule::getScheduleId);
            }
            break;
            case SELECT_BASE:
            default:
                break;
        }
        return scheduleLambdaQueryWrapper;
    }

    @Override
    public void insertSchedule(Schedule schedule) {
        if (scheduleMapper.insert(schedule) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_INSERT, schedule.getObjectId());
        }
    }

    @Override
    public void deleteSchedule(String scheduleId) {
        if (scheduleMapper.deleteById(scheduleId) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, scheduleId);
        }
    }

    @Override
    public void updateSchedule(Schedule schedule) {
        Schedule oldSchedule = getSchedule(schedule.getScheduleId());
        oldSchedule.update(schedule);
        if (scheduleMapper.updateById(oldSchedule) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, schedule.getScheduleId());
        }
    }

    @Override
    public Schedule getSchedule(String scheduleId) {
        Schedule schedule = scheduleMapper.selectById(scheduleId);
        if (schedule == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, scheduleId);
        }
        return schedule;
    }

    @Override
    public List<Schedule> listScheduleBaseByObject(String objectId) {

        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        scheduleLambdaQueryWrapper.eq(Schedule::getObjectId, objectId);

        return scheduleMapper.selectList(scheduleLambdaQueryWrapper);
    }

    @Override
    public List<Schedule> listScheduleByObject(String objectId) {

        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_ALL);
        scheduleLambdaQueryWrapper.eq(Schedule::getObjectId, objectId);

        return scheduleMapper.selectList(scheduleLambdaQueryWrapper);
    }

    @Override
    public Boolean isInSchedule(String objectId) {

        Calendar openAt = Calendar.getInstance();
        Calendar closeAt = Calendar.getInstance();
        Date now = new Date();

        List<Schedule> scheduleList = listScheduleBaseByObject(objectId);
        for (Schedule schedule : scheduleList) {
            openAt.set(Calendar.HOUR, schedule.getOpenHour());
            openAt.set(Calendar.MINUTE, schedule.getOpenMinute());
            closeAt.set(Calendar.HOUR, schedule.getCloseHour());
            closeAt.set(Calendar.MINUTE, schedule.getCloseMinute());
            if (now.after(openAt.getTime()) && now.before(closeAt.getTime())) {
                return true;
            }
        }

        return false;
    }
}
