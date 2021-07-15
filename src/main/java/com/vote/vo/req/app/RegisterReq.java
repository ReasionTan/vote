package com.vote.vo.req.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@ApiModel("用户登记请求VO")
public class RegisterReq {

    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9]\\)?$", message = "请输入正确的身份证号码")
    @ApiModelProperty(value = "身份证号码", required = true)
    private String idCard;

    @Email(message = "请输入正确的邮箱")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterReq{" +
                "idCard='" + idCard + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
