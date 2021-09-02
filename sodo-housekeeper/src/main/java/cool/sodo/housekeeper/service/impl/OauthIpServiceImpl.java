package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.OauthIpDTO;
import cool.sodo.housekeeper.mapper.OauthIpMapper;
import cool.sodo.housekeeper.service.OauthIpService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/2 22:44
 */
@Service
public class OauthIpServiceImpl implements OauthIpService {

    public static final String ERROR_INSERT = "OauthIp 新增失败！";
    public static final String ERROR_DELETE = "OauthIp 删除失败！";
    public static final String ERROR_UPDATE = "OauthIp 更新失败！";
    public static final String ERROR_SELECT = "OauthIp 不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private OauthIpMapper oauthIpMapper;

    private LambdaQueryWrapper<OauthIp> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getClientId);
                break;
            case SELECT_BASE:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getIp, OauthIp::getClientId
                        , OauthIp::getValid, OauthIp::getValidNum);
                break;
            case SELECT_INFO:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getIp, OauthIp::getDescription
                        , OauthIp::getClientId, OauthIp::getValid, OauthIp::getValidNum, OauthIp::getCreateBy
                        , OauthIp::getCreateAt, OauthIp::getUpdateBy, OauthIp::getUpdateAt);
                break;
            default:
                break;
        }
        return oauthIpLambdaQueryWrapper;
    }

    @Override
    public void insertOauthIp(OauthIp oauthIp, String createBy) {

        oauthIp.init(createBy);
        check(null, oauthIp.getIp(), oauthIp.getClientId());
        if (oauthIpMapper.insert(oauthIp) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }

    @Override
    public void deleteOauthIp(String ipId, String deleteBy) {

        OauthIp oauthIp = getOauthIp(ipId);
        oauthIp.delete(deleteBy);
        updateOauthIp(oauthIp);
        if (oauthIpMapper.deleteById(ipId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteOauthIp(List<String> ipIdList, String deleteBy) {

        if (StringUtil.isEmpty(ipIdList)) {
            return;
        }
        for (String ipId : ipIdList) {
            OauthIp oauthIp = getOauthIp(ipId);
            oauthIp.delete(deleteBy);
            updateOauthIp(oauthIp);
        }
        if (oauthIpMapper.deleteBatchIds(ipIdList) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void updateOauthIp(OauthIp oauthIp) {
        if (oauthIpMapper.updateById(oauthIp) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    public void updateOauthIp(OauthIp oauthIp, String updateBy) {

        OauthIp oauthIpOld = getOauthIp(oauthIp.getIpId());
        oauthIpOld.update(oauthIp, updateBy);
        check(oauthIpOld.getIpId(), oauthIpOld.getIp(), oauthIpOld.getClientId());
        if (oauthIpMapper.updateById(oauthIpOld) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    @Async
    public void updateOauthIpValidNumByAsync(String ipId) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();
        oauthIpLambdaQueryWrapper.select(OauthIp::getValidNum)
                .eq(OauthIp::getIpId, ipId);
        OauthIp oauthIp = oauthIpMapper.selectOne(oauthIpLambdaQueryWrapper);
        LambdaUpdateWrapper<OauthIp> oauthIpLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        oauthIpLambdaUpdateWrapper.eq(OauthIp::getIpId, ipId)
                .set(OauthIp::getValidNum, oauthIp.getValidNum() + 1);
        if (oauthIpMapper.update(null, oauthIpLambdaUpdateWrapper) <= 0) {
            throw new AsyncException(ERROR_UPDATE);
        }
    }

    @Override
    public OauthIp getOauthIp(String ipId) {

        OauthIp oauthIp = oauthIpMapper.selectById(ipId);
        if (StringUtil.isEmpty(oauthIp)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthIp;
    }

    @Override
    public IPage<OauthIp> pageOauthIpInfo(OauthIpDTO oauthIpDTO) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        oauthIpLambdaQueryWrapper.eq(OauthIp::getClientId, oauthIpDTO.getClientId())
                .eq(OauthIp::getValid, oauthIpDTO.getValid());
        if (!StringUtil.isEmpty(oauthIpDTO.getContent())) {
            oauthIpLambdaQueryWrapper.and(
                    wrapper -> wrapper.like(OauthIp::getIp, oauthIpDTO.getContent())
                            .or()
                            .like(OauthIp::getDescription, oauthIpDTO.getContent())
            );
        }
        return oauthIpMapper.selectPage(new Page<>(oauthIpDTO.getPageNum(), oauthIpDTO.getPageSize()), oauthIpLambdaQueryWrapper);
    }

    @Override
    public void check(String ipId, String ip, String clientId) {

        if (!StringUtil.isEmpty(ipId)) {
            OauthIp oauthIp = getOauthIp(ipId);
            if (oauthIp.getIp().equals(ip)) {
                return;
            }
        }
        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();
        oauthIpLambdaQueryWrapper.eq(OauthIp::getIp, ip)
                .eq(OauthIp::getClientId, clientId);
        if (oauthIpMapper.selectCount(oauthIpLambdaQueryWrapper) != 0) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "该客户端已存在 IP 为：" + ip + " 的记录！");
        }
    }
}
