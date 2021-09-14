package cool.sodo.housekeeper.controller;

import cool.sodo.common.starter.domain.OauthIp;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.starter.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.OauthIpDTO;
import cool.sodo.housekeeper.service.OauthIpService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/2 21:13
 */
@RestController
@RequestMapping(value = "ip")
public class OauthIpController {

    @Resource
    private OauthIpService oauthIpService;

    @PostMapping(value = "")
    public Result insertOauthIp(@RequestBody @Valid OauthIp oauthIp, @CurrentUser User user) {

        oauthIpService.insertOauthIp(oauthIp, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "{ipId}")
    public Result deleteOauthIp(@PathVariable String ipId, @CurrentUser User user) {

        oauthIpService.deleteOauthIp(ipId, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "/list")
    public Result deleteOauthIp(@RequestBody List<String> ipIdList, @CurrentUser User user) {

        oauthIpService.deleteOauthIp(ipIdList, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateOauthIp(@RequestBody @Valid OauthIp oauthIp, @CurrentUser User user) {

        oauthIpService.updateOauthIp(oauthIp, user.getUserId());
        return Result.success();
    }

    @PostMapping(value = "page")
    public Result pageOauthIpInfo(@RequestBody OauthIpDTO oauthIpDTO) {
        return Result.success(oauthIpService.pageOauthIpInfo(oauthIpDTO));
    }
}
