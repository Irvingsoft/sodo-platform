package cool.sodo.auth.controller;

import cool.sodo.auth.entity.AuthorizeRequest;
import cool.sodo.auth.entity.GrantType;
import cool.sodo.auth.service.OauthAuthService;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 授权接口
 *
 * @author TimeChaser
 * @date 2021/7/12 23:52
 */
@RestController
@RequestMapping(value = "authorize")
@Api(tags = "授权")
public class AuthorizationController {

    @Resource
    private OauthAuthService oauthAuthService;

    @ApiOperation(value = "Authorize 请求", notes = "获取令牌", produces = "application/json")
    @PostMapping("")
    public Result authorize(@RequestBody @Valid AuthorizeRequest authorizeRequest, HttpServletRequest request) {

        GrantType grantType = oauthAuthService.getGrantType(authorizeRequest);

        if (GrantType.AUTHCODE.equals(grantType)) {
            if (StringUtil.isEmpty(authorizeRequest.getCode())) {
                return Result.of(ResultEnum.BAD_REQUEST);
            }

            return Result.success(oauthAuthService.authorize(authorizeRequest, request));
        }

        return Result.of(ResultEnum.INVALID_GRANT);
    }
}
