package cool.sodo.housekeeper.controller;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.domain.OauthClient;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.starter.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.OauthClientDTO;
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
    public Result pageOauthClientInfo(@RequestBody OauthClientDTO oauthClientDTO) {

        return Result.success(oauthClientService.pageOauthClientInfo(oauthClientDTO));
    }

    @GetMapping(value = "list/use")
    public Result listOauthClientBaseUse() {

        return Result.success(oauthClientService.listOauthClientBaseUse());
    }

}
