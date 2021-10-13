package cool.sodo.housekeeper.schedule;

import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.housekeeper.service.OauthApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * OauthApi RequestNum 刷新日程
 *
 * @author TimeChaser
 * @date 2021/7/20 10:57
 */
@Component
@Slf4j
public class OauthApiSchedule {

    public static final int ZERO = 0;

    @Resource
    private OauthApiService oauthApiService;

    /**
     * 每日 0：0：0 执行
     *
     * @author TimeChaser
     * @date 2021/7/20 10:23
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetRequestDay() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            if (oauthApi.getRequestDay().equals(ZERO)) {
                continue;
            }
            oauthApi.setRequestDay(ZERO);
            oauthApiService.updateOauthApiAccessDailyByScheduleAsync(oauthApi);
        }
        log.info("Reset request counts daily!");
    }

    /**
     * 每周一 0：0：0 执行
     *
     * @author TimeChaser
     * @date 2021/7/20 10:23
     */
    @Scheduled(cron = "0 0 0 * * 2")
    public void resetRequestWeek() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            if (oauthApi.getRequestWeek().equals(ZERO)) {
                continue;
            }
            oauthApi.setRequestWeek(ZERO);
            oauthApiService.updateOauthApiAccessWeeklyByScheduleAsync(oauthApi);
        }
        log.info("Reset request counts weekly!");
    }

    /**
     * 每月一号
     *
     * @author TimeChaser
     * @date 2021/7/20 10:23
     */
    @Scheduled(cron = "0 0 0 1 * *")
    public void resetRequestMonth() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            if (oauthApi.getRequestMonth().equals(ZERO)) {
                continue;
            }
            oauthApi.setRequestMonth(ZERO);
            oauthApiService.updateOauthApiAccessMonthlyByScheduleAsync(oauthApi);
        }
        log.info("Reset request counts monthly!");
    }
}
