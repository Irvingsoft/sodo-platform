package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.OauthApiRequest;
import cool.sodo.housekeeper.mapper.OauthApiMapper;
import cool.sodo.housekeeper.service.ClientApiService;
import cool.sodo.housekeeper.service.OauthApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OauthApiServiceImpl implements OauthApiService {

    public static final String ERROR_INSERT = "API 信息新增失败！";
    public static final String ERROR_UPDATE = "API 信息更新失败！";
    public static final String ERROR_UPDATE_REQUEST = "API 请求信息更新失败！";
    public static final String ERROR_DELETE = "API 信息删除失败！";
    public static final String ERROR_SELECT = "API 不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;
    public static final int SELECT_REQUEST_NUM = 3;

    @Resource
    private OauthApiMapper oauthApiMapper;
    @Resource
    private ClientApiService clientApiService;
    @Resource
    private CommonUserService userService;

    private LambdaQueryWrapper<OauthApi> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthApiLambdaQueryWrapper.select(OauthApi::getApiId, OauthApi::getPath, OauthApi::getInUse,
                        OauthApi::getAuth, OauthApi::getLog, OauthApi::getRequestLimit, OauthApi::getLimitPeriod, OauthApi::getLimitNum);
                break;
            case SELECT_BASE:
                oauthApiLambdaQueryWrapper.select(OauthApi::getApiId, OauthApi::getName, OauthApi::getPath, OauthApi::getDescription);
                break;
            case SELECT_INFO:
                oauthApiLambdaQueryWrapper.select(OauthApi::getApiId, OauthApi::getName, OauthApi::getPath,
                        OauthApi::getDescription, OauthApi::getService, OauthApi::getMethod, OauthApi::getRequestDay,
                        OauthApi::getRequestWeek, OauthApi::getRequestMonth, OauthApi::getRequestAll,
                        OauthApi::getInUse, OauthApi::getAuth, OauthApi::getLog, OauthApi::getRequestLimit,
                        OauthApi::getLimitPeriod, OauthApi::getLimitNum, OauthApi::getCreateAt,
                        OauthApi::getCreateBy, OauthApi::getUpdateAt, OauthApi::getUpdateBy);
                break;
            case SELECT_REQUEST_NUM:
                oauthApiLambdaQueryWrapper.select(OauthApi::getRequestDay, OauthApi::getRequestWeek,
                        OauthApi::getRequestMonth, OauthApi::getRequestAll);
                break;
            default:
                break;
        }
        return oauthApiLambdaQueryWrapper;
    }

    @Override
    public void insertOauthApi(OauthApi oauthApi, String userId) {

        if (!StringUtil.isEmpty(oauthApi.getApiId())
                && !StringUtil.isEmpty(getOauthApiIdentityNullable(oauthApi.getApiId()))) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "OauthApi 已存在！");
        }
        if (!validateOauthApiOfInsert(oauthApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "已存在相同 Path 和 Method 的记录！");
        }

        oauthApi.init(userId);
        if (oauthApiMapper.insert(oauthApi) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
        clientApiService.insertClientApiOfOauthApi(oauthApi.getApiId(), oauthApi.getClientIdList(), oauthApi.getCreateBy());
    }

    @Override
    public void deleteOauthApi(String id, String userId) {

        OauthApi oauthApi = getOauthApi(id);
        oauthApi.delete(userId);
        updateOauthApi(oauthApi);
        if (oauthApiMapper.deleteById(id) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
        clientApiService.deleteClientApiByOauthApi(id);
    }

    @Override
    public void deleteOauthApi(List<String> apiIdList, String userId) {

        if (StringUtil.isEmpty(apiIdList)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "请至少选择一条 OauthApi！");
        }

        for (String apiId : apiIdList) {
            OauthApi oauthApi = getOauthApi(apiId);
            oauthApi.delete(userId);
            updateOauthApi(oauthApi);
        }
        if (oauthApiMapper.deleteBatchIds(apiIdList) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
        clientApiService.deleteClientApiByOauthApi(apiIdList);
    }

    @Override
    public void updateOauthApi(OauthApi oauthApi, String userId) {

        if (StringUtil.isEmpty(oauthApi.getApiId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "ApiId 不能为空！");
        }
        if (!validateOauthApiOfUpdate(oauthApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "已存在相同 Path 和 Method 的记录！");
        }

        OauthApi oauthApiOld = getOauthApi(oauthApi.getApiId());
        oauthApiOld.update(oauthApi, userId);
        if (oauthApiMapper.updateById(oauthApiOld) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
        clientApiService.updateClientApiOfOauthApi(oauthApi.getApiId(), oauthApi.getClientIdList(), oauthApi.getUpdateBy());
    }

    @Override
    public void updateOauthApi(OauthApi oauthApi) {
        if (oauthApiMapper.updateById(oauthApi) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    public void updateOauthApiRequestNum(OauthApi oauthApi) {

        LambdaUpdateWrapper<OauthApi> oauthApiLambdaUpdateWrapper = Wrappers.lambdaUpdate(oauthApi);
        oauthApiLambdaUpdateWrapper.eq(OauthApi::getApiId, oauthApi.getApiId());
        if (!StringUtil.isEmpty(oauthApi.getRequestDay())) {
            oauthApiLambdaUpdateWrapper.set(OauthApi::getRequestDay, oauthApi.getRequestDay());
        }
        if (!StringUtil.isEmpty(oauthApi.getRequestWeek())) {
            oauthApiLambdaUpdateWrapper.set(OauthApi::getRequestWeek, oauthApi.getRequestWeek());
        }
        if (!StringUtil.isEmpty(oauthApi.getRequestMonth())) {
            oauthApiLambdaUpdateWrapper.set(OauthApi::getRequestMonth, oauthApi.getRequestMonth());
        }
        if (oauthApiMapper.update(null, oauthApiLambdaUpdateWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE_REQUEST);
        }
    }

    @Override
    public void updateOauthApiAccessByAsync(String apiId) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_REQUEST_NUM);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getApiId, apiId);
        OauthApi oauthApi = oauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);

        LambdaUpdateWrapper<OauthApi> oauthApiLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        oauthApiLambdaUpdateWrapper.eq(OauthApi::getApiId, apiId)
                .set(OauthApi::getRequestDay, oauthApi.getRequestDay() + 1)
                .set(OauthApi::getRequestWeek, oauthApi.getRequestWeek() + 1)
                .set(OauthApi::getRequestMonth, oauthApi.getRequestMonth() + 1)
                .set(OauthApi::getRequestAll, oauthApi.getRequestAll() + 1);
        if (oauthApiMapper.update(null, oauthApiLambdaUpdateWrapper) <= 0) {
            throw new AsyncException(ERROR_UPDATE);
        }
    }

    @Override
    public OauthApi getOauthApi(String id) {

        OauthApi oauthApi = oauthApiMapper.selectById(id);
        if (StringUtil.isEmpty(oauthApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthApi;
    }

    @Override
    public OauthApi getOauthApiIdentity(String id) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getApiId, id);
        OauthApi oauthApi = oauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
        if (oauthApi == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthApi;
    }

    @Override
    public OauthApi getOauthApiIdentityNullable(String id) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getApiId, id);
        return oauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
    }

    @Override
    public OauthApi getOauthApiIdentityNullable(String path, String method) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getPath, path)
                .eq(OauthApi::getMethod, method);
        return oauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
    }

    @Override
    public OauthApi getOauthApiInfoDetail(String apiId) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getApiId, apiId);
        OauthApi oauthApi = oauthApiMapper.selectOne(oauthApiLambdaQueryWrapper);
        if (oauthApi == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        if (!StringUtil.isEmpty(oauthApi.getCreateBy())) {
            oauthApi.setCreator(userService.getUserBase(oauthApi.getCreateBy()));
        }
        if (!StringUtil.isEmpty(oauthApi.getUpdateBy())) {
            oauthApi.setUpdater(userService.getUserBase(oauthApi.getUpdateBy()));
        }
        oauthApi.setClientIdList(clientApiService.listClientApiClientId(apiId));
        return oauthApi;
    }

    @Override
    public IPage<OauthApi> pageOauthApiInfo(OauthApiRequest oauthApiRequest) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        if (!StringUtil.isEmpty(oauthApiRequest.getClientId())) {
            List<String> apiIdList = clientApiService.listClientApiApiId(oauthApiRequest.getClientId());
            if (apiIdList.size() == 0) {
                apiIdList.add("");
            }
            oauthApiLambdaQueryWrapper.in(OauthApi::getApiId
                    , apiIdList);
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getService())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getService, oauthApiRequest.getService());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getMethod())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getMethod, oauthApiRequest.getMethod());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getInUse())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getInUse, oauthApiRequest.getInUse());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getAuth())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getAuth, oauthApiRequest.getAuth());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getRequestLimit())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getRequestLimit, oauthApiRequest.getRequestLimit());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getLog())) {
            oauthApiLambdaQueryWrapper.eq(OauthApi::getLog, oauthApiRequest.getLog());
        }
        if (!StringUtil.isEmpty(oauthApiRequest.getContent())) {
            oauthApiLambdaQueryWrapper.and(wrapper ->
                    wrapper.like(OauthApi::getName, oauthApiRequest.getContent())
                            .or()
                            .like(OauthApi::getDescription, oauthApiRequest.getContent())
            );
        }
        oauthApiLambdaQueryWrapper.orderByDesc(OauthApi::getCreateAt)
                .orderByDesc(OauthApi::getUpdateAt);
        Page<OauthApi> oauthApiPage = new Page<>(oauthApiRequest.getPageNum(), oauthApiRequest.getPageSize());
        return oauthApiMapper.selectPage(oauthApiPage, oauthApiLambdaQueryWrapper);
    }

    @Override
    public List<OauthApi> listOauthApiBaseUse() {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        oauthApiLambdaQueryWrapper.eq(OauthApi::getInUse, true)
                .orderByAsc(OauthApi::getPath);
        return oauthApiMapper.selectList(oauthApiLambdaQueryWrapper);
    }

    @Override
    public List<OauthApi> listOauthApiBaseUse(String clientId) {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        oauthApiLambdaQueryWrapper.in(OauthApi::getApiId, clientApiService.listClientApiApiId(clientId))
                .eq(OauthApi::getInUse, true)
                .orderByAsc(OauthApi::getPath);
        return oauthApiMapper.selectList(oauthApiLambdaQueryWrapper);
    }

    @Override
    public List<OauthApi> listOauthApiInfo() {

        LambdaQueryWrapper<OauthApi> oauthApiLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        return oauthApiMapper.selectList(oauthApiLambdaQueryWrapper);
    }

    @Override
    public Boolean validateOauthApiOfInsert(OauthApi oauthApi) {
        return StringUtil.isEmpty(getOauthApiIdentityNullable(oauthApi.getPath(), oauthApi.getMethod()));
    }

    @Override
    public Boolean validateOauthApiOfUpdate(OauthApi oauthApi) {

        OauthApi oauthApiIdentity = getOauthApiIdentityNullable(oauthApi.getPath(), oauthApi.getMethod());
        return StringUtil.isEmpty(oauthApiIdentity) || oauthApiIdentity.getApiId().equals(oauthApi.getApiId());
    }
}
