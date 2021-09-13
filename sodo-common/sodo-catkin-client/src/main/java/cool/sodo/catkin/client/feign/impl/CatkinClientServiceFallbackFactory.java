package cool.sodo.catkin.client.feign.impl;

import cool.sodo.catkin.client.feign.CatkinClientService;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import feign.hystrix.FallbackFactory;

/**
 * @author TimeChaser
 * @date 2021/9/13 16:12
 */
public class CatkinClientServiceFallbackFactory implements FallbackFactory<CatkinClientService> {

    @Override
    public CatkinClientService create(Throwable throwable) {
        throw new SoDoException(ResultEnum.SERVER_ERROR, "Catkin 服务获取分布式 ID 失败！");
    }
}
