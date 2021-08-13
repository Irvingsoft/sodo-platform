package cool.sodo.order.invocation;

import cool.sodo.order.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "goods", path = "goods", fallback = GoodsHystrix.class, configuration = MultipartSupportConfig.class)
public interface GoodsService {


}
