package cool.sodo.housekeeper.controller;

import cool.sodo.common.base.domain.OauthApi;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.OauthApiDTO;
import cool.sodo.housekeeper.service.OauthApiService;
import cool.sodo.log.annotation.BusinessLogger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * API 管理接口
 *
 * @author TimeChaser
 * @date 2021/5/30 20:58
 */
@RestController
@RequestMapping(value = "api")
public class OauthApiController {

    @Resource
    private OauthApiService oauthApiService;

    @PostMapping(value = "")
    public Result insertOauthApi(@RequestBody @Valid OauthApi oauthApi, @CurrentUser User user) {

        oauthApiService.insertOauthApi(oauthApi, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "/{apiId}")
    @BusinessLogger(businessType = Constants.BUSINESS_DELETE, message = "删除 OauthApi")
    public Result deleteOauthApi(@PathVariable String apiId, @CurrentUser User user) {

        oauthApiService.deleteOauthApi(apiId, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "/list")
    public Result deleteOauthApi(@RequestBody List<String> apiIdList, @CurrentUser User user) {

        oauthApiService.deleteOauthApi(apiIdList, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateOauthApi(@RequestBody @Valid OauthApi oauthApi, @CurrentUser User user) {

        oauthApiService.updateOauthApi(oauthApi, user.getUserId());
        return Result.success();
    }

    @GetMapping(value = "{apiId}")
    public Result getOauthApiInfoDetail(@PathVariable String apiId) {

        return Result.success(oauthApiService.getOauthApiInfoDetail(apiId));
    }

    @PostMapping(value = "page")
    public Result pageOauthApiInfo(@RequestBody OauthApiDTO oauthApiDTO) {

        return Result.success(oauthApiService.pageOauthApiInfo(oauthApiDTO));
    }

    /**
     * 查询所有属性 Use == true 的 OauthApi 列表
     *
     * @author TimeChaser
     * @date 2021/6/7 8:49
     */
    @GetMapping(value = "list/use")
    public Result listOauthApiBaseUse() {

        return Result.success(oauthApiService.listOauthApiBaseUse());
    }

    /**
     * 根据 ClientId 查询 OauthApi 列表，OauthApi 的属性 Use == true
     *
     * @author TimeChaser
     * @date 2021/6/7 8:49
     */
    @GetMapping(value = "list/use/{clientId}")
    public Result listOauthApiBaseUse(@PathVariable String clientId) {

        return Result.success(oauthApiService.listOauthApiBaseUse(clientId));
    }
}
