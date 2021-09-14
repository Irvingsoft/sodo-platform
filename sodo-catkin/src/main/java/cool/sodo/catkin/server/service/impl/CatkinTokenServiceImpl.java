package cool.sodo.catkin.server.service.impl;

import cool.sodo.catkin.server.domain.CatkinToken;
import cool.sodo.catkin.server.mapper.CatkinTokenMapper;
import cool.sodo.catkin.server.service.CatkinTokenService;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author TimeChaser
 * @date 2021/9/12 17:47
 */
@Service
@Slf4j
public class CatkinTokenServiceImpl implements CatkinTokenService {

    private final static Map<String, Set<String>> TOKEN_TO_BIZ_TYPES = new HashMap<>(Constants.HASHMAP_SIZE_DEFAULT);

    @Resource
    private CatkinTokenMapper catkinTokenMapper;

    @PostConstruct
    private synchronized void init() {

        log.info("Catkin token init begin");
        List<CatkinToken> catkinTokens = listAll();
        if (!StringUtil.isEmpty(catkinTokens)) {
            for (CatkinToken catkinToken : catkinTokens) {
                if (!TOKEN_TO_BIZ_TYPES.containsKey(catkinToken.getToken())) {
                    TOKEN_TO_BIZ_TYPES.put(catkinToken.getToken(), new HashSet<>());
                }
                TOKEN_TO_BIZ_TYPES.get(catkinToken.getToken()).add(catkinToken.getBizType());
            }
        }
        log.info("Catkin token init success, token size:{}", StringUtil.isEmpty(catkinTokens) ? 0 : catkinTokens.size());
    }

    /**
     * 1分钟刷新一次token
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void refresh() {
        log.info("refresh token begin");
        init();
    }

    public List<CatkinToken> listAll() {
        return catkinTokenMapper.selectList(null);
    }

    @Override
    public boolean validate(String token, String bizType) {

        if (StringUtil.isEmpty(bizType) || StringUtil.isEmpty(token)) {
            return false;
        }
        Set<String> bizTypes = TOKEN_TO_BIZ_TYPES.get(token);
        return !StringUtil.isEmpty(bizTypes) && bizTypes.contains(bizType);
    }
}
