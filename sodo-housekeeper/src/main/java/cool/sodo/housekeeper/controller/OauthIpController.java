package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.OauthIpRequest;
import cool.sodo.housekeeper.service.OauthIpService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "ip")
public class OauthIpController {

    public static final String ERROR_INSERT = "OauthIp 已存在！";
    public static final String ERROR_DELETE = "OauthIp 不存在！";
    public static final String ERROR_UPDATE = "IpId 不能为空！";

    @Resource
    private OauthIpService oauthIpService;

    @PostMapping(value = "")
    public Result insertOauthIp(@RequestBody OauthIp oauthIp, @CurrentUser User user) {

        if (!StringUtil.isEmpty(oauthIp.getIpId())
                && !StringUtil.isEmpty(oauthIpService.getOauthIpIdentityNullable(oauthIp.getIpId()))) {
            return Result.badRequest(ERROR_INSERT);
        }
        oauthIp.setCreateBy(user.getUserId());
        oauthIpService.insertOauthIp(oauthIp);
        return Result.success();
    }

    @DeleteMapping(value = "{ipId}")
    public Result deleteOauthIp(@PathVariable String ipId, @CurrentUser User user) {

        if (StringUtil.isEmpty(oauthIpService.getOauthIpIdentityNullable(ipId))) {
            return Result.badRequest(ERROR_DELETE);
        }
        oauthIpService.deleteOauthIp(ipId);
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateOauthIp(@RequestBody OauthIp oauthIp, @CurrentUser User user) {

        if (StringUtil.isEmpty(oauthIp.getIpId())) {
            return Result.badRequest(ERROR_UPDATE);
        }
        oauthIp.setUpdateBy(user.getUserId());
        oauthIpService.updateOauthIp(oauthIp);
        return Result.success();
    }

    @PostMapping(value = "page")
    public Result pageOauthIpInfo(@RequestBody OauthIpRequest oauthIpRequest) {

        return Result.success(oauthIpService.pageOauthIpInfo(oauthIpRequest));
    }

    // TODO 日志 Log 管理、
    //  API 参数校验、
    //  账号密码登录
}
