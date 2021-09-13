package cool.sodo.catkin.starter.feign;

import cool.sodo.catkin.starter.feign.impl.CatkinClientServiceFallbackFactory;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author TimeChaser
 * @date 2021/9/13 15:36
 */
@FeignClient(name = Constants.SODO_CATKIN, fallbackFactory = CatkinClientServiceFallbackFactory.class, decode404 = true)
public interface CatkinClientService {

    @RequestMapping(value = "id")
    Result id(@RequestParam(value = "token") String token,
              @RequestParam(value = "bizType") String bizType,
              @RequestParam(value = "batchSize") Integer batchSize);

    @RequestMapping(value = "id/simple")
    Long idSimple(@RequestParam(value = "token") String token,
                    @RequestParam(value = "bizType") String bizType);

    @RequestMapping(value = "id/segment")
    Result segmentId(@RequestParam(value = "token") String token,
                     @RequestParam(value = "bizType") String bizType);

    @RequestMapping(value = "id/segment/simple")
    String segmentIdSimple(@RequestParam(value = "token") String token,
                           @RequestParam(value = "bizType") String bizType);
}
