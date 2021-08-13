package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

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

        if (!StringUtils.isEmpty(address.getSchoolId())) {
            this.schoolId = address.getSchoolId();
        }
        if (!StringUtils.isEmpty(address.getAreaId())) {
            this.areaId = address.getAreaId();
        }
        if (!StringUtils.isEmpty(address.getDetail())) {
            this.detail = address.getDetail();
        }
        if (!StringUtils.isEmpty(address.getName())) {
            this.name = address.getName();
        }
        if (!StringUtils.isEmpty(address.getPhone())) {
            this.phone = address.getPhone();
        }
        if (!StringUtils.isEmpty(address.getGender())) {
            this.gender = address.getGender();
        }
        if (!StringUtils.isEmpty(address.getDefaultAddress())) {
            this.defaultAddress = address.getDefaultAddress();
        }
    }
}
