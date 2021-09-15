package cool.sodo.order.invocation;

import cool.sodo.openfeign.starter.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "goods", path = "goods", fallback = GoodsHystrix.class, configuration = FeignConfig.class)
public interface GoodsService {


}
