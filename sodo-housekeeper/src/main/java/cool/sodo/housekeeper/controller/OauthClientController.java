package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.housekeeper.entity.OauthClientRequest;
import cool.sodo.housekeeper.service.OauthClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 客户端管理接口
 *
 * @author TimeChaser
 * @date 2021/5/31 22:48
 */
@RestController
@RequestMapping(value = "client")
public class OauthClientController {

    @Resource
    private OauthClientService oauthClientService;

    @PostMapping(value = "")
    public Result insertOauthClient(@RequestBody OauthClient oauthClient, @CurrentUser User user) {

        oauthClientService.insertOauthClient(oauthClient, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "/{clientId}")
    public Result deleteOauthClient(@PathVariable String clientId, @CurrentUser User user) {

        oauthClientService.deleteOauthClient(clientId, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateOauthClient(@RequestBody OauthClient oauthClient, @CurrentUser User user) {

        oauthClientService.updateOauthClient(oauthClient, user.getUserId());
        return Result.success();
    }

    @GetMapping(value = "{clientId}")
    public Result getOauthClientInfoDetail(@PathVariable String clientId) {

        return Result.success(oauthClientService.getOauthClientInfoDetail(clientId));
    }

    @PostMapping(value = "page")
    public Result pageOauthClientInfo(@RequestBody OauthClientRequest oauthClientRequest) {

        return Result.success(oauthClientService.pageOauthClientInfo(oauthClientRequest));
    }

    @GetMapping(value = "list/use")
    public Result listOauthClientBaseUse() {

        return Result.success(oauthClientService.listOauthClientBaseUse());
    }

}