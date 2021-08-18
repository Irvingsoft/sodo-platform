package cool.sodo.zuul.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.zuul.service.OauthClientService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ZUUL 测试接口
 *
 * @author TimeChaser
 * @date 2021/5/26 17:55
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Resource
    private OauthClientService oauthClientService;

    @RequestMapping(value = "client/{clientId}", method = RequestMethod.GET)
    public Result client(@PathVariable String clientId) {
        return Result.success(oauthClientService.getOauthClientIdentity(clientId));
    }
}
