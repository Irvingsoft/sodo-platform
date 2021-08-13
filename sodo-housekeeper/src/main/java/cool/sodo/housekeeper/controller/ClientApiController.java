package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.BusinessLogger;
import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.ClientApi;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Result;
import cool.sodo.housekeeper.service.ClientApiService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户端与 API 对应关系接口
 *
 * @author TimeChaser
 * @date 2021/5/31 22:49
 */
@RestController
@RequestMapping(value = "client-api")
public class ClientApiController {

    public static final String ERROR_EMPTY = "ApiId 或 ClientId 不能为空！";
    public static final String ERROR_INSERT = "ClientApi 已存在！";

    @Resource
    private ClientApiService clientApiService;

    @PostMapping(value = "list")
    @BusinessLogger(businessType = Constants.BUSINESS_INSERT, message = "插入 Client 与 Api 对应关系")
    public Result insertClientApiList(@RequestBody List<ClientApi> clientApiList, @CurrentUser User user) {

        for (ClientApi clientApi : clientApiList) {

            if (StringUtils.isEmpty(clientApi.getApiId()) || StringUtils.isEmpty(clientApi.getClientId())) {
                return Result.badRequest(ERROR_EMPTY);
            }
            if (!StringUtils.isEmpty(clientApi.getId())
                    && !StringUtils.isEmpty(clientApiService.getClientApiIdentityNullable(clientApi.getId()))) {
                return Result.badRequest(ERROR_INSERT);
            }
            clientApi.setCreateBy(user.getUserId());
        }
        clientApiService.insertClientApi(clientApiList);
        return Result.success();
    }

    @PostMapping(value = "api/{apiId}")
    @BusinessLogger(businessType = Constants.BUSINESS_UPDATE, message = "根据 Api 修改 Client 与 Api 对应关系")
    public Result updateClientApiOfOauthApi(@PathVariable String apiId, @RequestBody List<String> clientIdList, @CurrentUser User user) {

        clientApiService.updateClientApiOfOauthApi(apiId, clientIdList, user.getUserId());
        return Result.success();
    }

    @PostMapping(value = "clientId/{clientId}")
    @BusinessLogger(businessType = Constants.BUSINESS_UPDATE, message = "根据 Client 修改 Client 与 Api 对应关系")
    public Result updateClientApiOfOauthClient(@PathVariable String clientId, @RequestBody List<String> apiIdList, @CurrentUser User user) {

        clientApiService.updateClientApiOfOauthClient(clientId, apiIdList, user.getUserId());
        return Result.success();
    }
}
