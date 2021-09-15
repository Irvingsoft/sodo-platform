package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.starter.domain.Address;
import cool.sodo.user.mapper.AddressMapper;
import cool.sodo.user.service.AddressService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {

    public static final String ERROR_SELECT = "Address 不存在！";
    public static final String ERROR_INSERT = "地址信息插入失败！ UserId：";
    public static final String ERROR_UPDATE = "地址信息更新失败！ AddressId：";
    public static final String ERROR_UPDATE_ID = "AddressId为空，地址信息更新失败！ UserId：";
    public static final String ERROR_DELETE = "地址信息删除失败！ AddressId：";
    public static final String ERROR_USER_ADDRESS = "地址信息与当前用户不匹配！ UserId：%s，AddressId：%s";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private AddressMapper addressMapper;

    private LambdaQueryWrapper<Address> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Address> addressLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                addressLambdaQueryWrapper.select(Address::getAddressId, Address::getUserId, Address::getSchoolId, Address::getAreaId);
                break;
            case SELECT_BASE:
                addressLambdaQueryWrapper.select(Address::getAddressId, Address::getUserId, Address::getSchoolId, Address::getAreaId
                        , Address::getDetail, Address::getName, Address::getPhone
                        , Address::getGender, Address::getDefaultAddress);
                break;
            default:
                break;
        }

        return addressLambdaQueryWrapper;
    }

    @Override
    @CacheEvict(cacheNames = "address", key = "#root.args[1]")
    public void insertAddress(Address address) {

        changeDefaultAddress(address, address.getUserId());
        if (addressMapper.insert(address) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT, address);
        }
    }

    @Override
    @CacheEvict(cacheNames = "address", key = "#root.args[1]")
    public void updateAddress(Address address) {

        Address addressOld = getAddress(address.getAddressId());
        changeDefaultAddress(address, addressOld.getUserId());

        addressOld.update(address);
        if (addressMapper.updateById(addressOld) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, addressOld);
        }
    }

    private void changeDefaultAddress(Address address, String userId) {
        if (address.getDefaultAddress()) {
            Address defaultAddressBefore = getDefaultAddressNullable(userId);
            if (defaultAddressBefore != null) {
                defaultAddressBefore.setDefaultAddress(false);
                if (addressMapper.updateById(defaultAddressBefore) <= 0) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, defaultAddressBefore);
                }
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = "address", key = "#root.args[1]")
    public void removeAddress(String addressId) {

        if (addressMapper.deleteById(addressId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE, addressId);
        }
    }

    @Override
    @Cacheable(cacheNames = "address", key = "#root.args[0]")
    public List<Address> listAddressBase(String userId) {

        LambdaQueryWrapper<Address> queryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        queryWrapper.eq(Address::getUserId, userId)
                .orderByDesc(Address::getDefaultAddress)
                .orderByDesc(Address::getUpdateAt);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public Address getAddress(String addressId) {

        Address address = addressMapper.selectById(addressId);
        if (StringUtil.isEmpty(address)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return address;
    }

    @Override
    public Address getDefaultAddressNullable(String userId) {

        LambdaQueryWrapper<Address> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Address::getUserId, userId)
                .eq(Address::getDefaultAddress, true);
        return addressMapper.selectOne(queryWrapper);
    }

    @Override
    public Address getAddressBase(String addressId) {

        LambdaQueryWrapper<Address> addressLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        addressLambdaQueryWrapper.eq(Address::getAddressId, addressId);

        Address address = addressMapper.selectOne(addressLambdaQueryWrapper);
        if (StringUtil.isEmpty(address)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return address;
    }

    @Override
    public Address getAddressIdentity(String addressId) {

        LambdaQueryWrapper<Address> addressLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        addressLambdaQueryWrapper.eq(Address::getAddressId, addressId);
        Address address = addressMapper.selectOne(addressLambdaQueryWrapper);
        if (StringUtil.isEmpty(address)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return address;
    }

    @Override
    public Address getAddressIdentityNullable(String addressId) {

        LambdaQueryWrapper<Address> addressLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        addressLambdaQueryWrapper.eq(Address::getAddressId, addressId);
        return addressMapper.selectOne(addressLambdaQueryWrapper);
    }
}
