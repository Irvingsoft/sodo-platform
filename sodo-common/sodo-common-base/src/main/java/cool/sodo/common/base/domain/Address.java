package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import cool.sodo.common.base.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "address")
public class Address implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String addressId;

    private String userId;

    @NotNull(message = "学校不能为空")
    private String schoolId;

    @NotNull(message = "地区不能为空")
    private String areaId;

    @NotNull(message = "地址详情不能为空")
    private String detail;

    @NotNull(message = "姓名不能为空")
    private String name;

    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    private Boolean defaultAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    public void update(Address address) {

        if (!StringUtil.isEmpty(address.getSchoolId())) {
            this.schoolId = address.getSchoolId();
        }
        if (!StringUtil.isEmpty(address.getAreaId())) {
            this.areaId = address.getAreaId();
        }
        if (!StringUtil.isEmpty(address.getDetail())) {
            this.detail = address.getDetail();
        }
        if (!StringUtil.isEmpty(address.getName())) {
            this.name = address.getName();
        }
        if (!StringUtil.isEmpty(address.getPhone())) {
            this.phone = address.getPhone();
        }
        if (!StringUtil.isEmpty(address.getGender())) {
            this.gender = address.getGender();
        }
        if (!StringUtil.isEmpty(address.getDefaultAddress())) {
            this.defaultAddress = address.getDefaultAddress();
        }
    }
}
