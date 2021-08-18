package cool.sodo.user.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.Address;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.common.util.StringUtil;
import cool.sodo.user.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "address")
@Api(tags = "地址")
public class AddressController {

    public static final String ERROR_INSERT = "Address 已经存在！";
    public static final String ERROR_UPDATE = "AddressID 不能为空！";
    public static final String ERROR_USER_TO_ADDRESS = "地址信息与当前用户不匹配！";

    @Resource
    private AddressService addressService;

    @PostMapping(value = "")
    @ApiOperation(value = "添加地址")
    public Result insertAddress(@RequestBody @Valid Address address, @CurrentUser User user) {

        if (!StringUtil.isEmpty(address.getAddressId())
                && !StringUtil.isEmpty(addressService.getAddressIdentityNullable(address.getAddressId()))) {
            return Result.badRequest(ERROR_INSERT);
        }
        address.setUserId(user.getUserId());
        addressService.insertAddress(address);
        return Result.success();
    }

    @DeleteMapping(value = "/{addressId}")
    @ApiOperation(value = "根据 ID 删除当前用户的地址")
    public Result deleteAddress(@PathVariable String addressId, @CurrentUser User user) {

        if (!addressService.getAddressIdentity(addressId).getUserId().equals(user.getUserId())) {
            return Result.badRequest(ERROR_USER_TO_ADDRESS);
        }
        addressService.removeAddress(addressId);
        return Result.success();
    }

    @PatchMapping(value = "")
    @ApiOperation(value = "更新地址")
    public Result updateAddress(@RequestBody Address address, @CurrentUser User user) {

        if (StringUtil.isEmpty(address.getAddressId())) {
            return Result.badRequest(ERROR_UPDATE);
        }
        if (!addressService.getAddressIdentity(address.getAddressId()).getUserId().equals(user.getUserId())) {
            return Result.badRequest(ERROR_USER_TO_ADDRESS);
        }
        addressService.updateAddress(address);
        return Result.success();
    }

    @GetMapping(value = "/{addressId}")
    @ApiOperation(value = "根据 addressId 查询当前用户的地址")
    public Result getAddress(@PathVariable String addressId, @CurrentUser User user) {

        Address address = addressService.getAddressBase(addressId);
        if (!address.getUserId().equals(user.getUserId())) {
            return Result.badRequest(ERROR_USER_TO_ADDRESS);
        }
        return Result.success();
    }

    @GetMapping(value = "list")
    @ApiOperation(value = "查询当前用户的所有地址，并按状态和修改时间排序")
    public Result listAddress(@CurrentUser User user) {

        return Result.success(addressService.listAddressBase(user.getUserId()));
    }
}
