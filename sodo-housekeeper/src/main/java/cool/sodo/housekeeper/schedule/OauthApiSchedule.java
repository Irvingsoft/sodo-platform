package cool.sodo.housekeeper.schedule;

import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.housekeeper.service.OauthApiService;
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
    @Scheduled(cron = "0 0 0 ? * *")
    public void resetRequestDay() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            oauthApi.setRequestDay(ZERO);
            oauthApiService.updateOauthApiRequestNum(oauthApi);
        }
    }

    /**
     * 每周一 0：0：0 执行
     *
     * @author TimeChaser
     * @date 2021/7/20 10:23
     */
    @Scheduled(cron = "0 0 0 ? * 2")
    public void resetRequestWeek() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            oauthApi.setRequestWeek(ZERO);
            oauthApiService.updateOauthApiRequestNum(oauthApi);
        }
    }

    /**
     * 每月一号
     *
     * @author TimeChaser
     * @date 2021/7/20 10:23
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetRequestMonth() {

        List<OauthApi> oauthApiList = oauthApiService.listOauthApiInfo();
        for (OauthApi oauthApi : oauthApiList) {
            oauthApi.setRequestMonth(ZERO);
            oauthApiService.updateOauthApiRequestNum(oauthApi);
        }
    }
}
