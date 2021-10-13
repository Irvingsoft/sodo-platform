package cool.sodo.auth.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.domain.AccessToken;
import cool.sodo.common.core.mapper.CommonAccessTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author TimeChaser
 * @date 2021/10/12 15:15
 */
@Component
@Slf4j
public class AccessTokenSchedule {

    @Resource
    private CommonAccessTokenMapper accessTokenMapper;

    /**
     * 每日 0：0：0 执行
     *
     * @author TimeChaser
     * @date 2021/10/12 15:15
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetRequestDay() {

        LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
        accessTokenLambdaQueryWrapper.lt(AccessToken::getExpireAt, new Date());
        accessTokenMapper.delete(accessTokenLambdaQueryWrapper);
        log.info("Remove expired AccessToken daily!");
    }
}
