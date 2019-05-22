package com.tjpu.iot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** 用户编号 */
    private String userId;

    /** 用户手机号 */
    private String userMobile;

    /** 用户密码 */
    @JsonProperty(access = WRITE_ONLY)
    private String userPassword;

    /** 用户姓名 */
    private String userName;

    /** 用户所在公司 */
    private String userCompany;

    /** 用户所在公司编号 */
    private String userCompanyId;

    /** 用户所在区域 */
    private String userLocal;

    /** 用户状态 0离线 1在线 */
    private String userState;

    /** 用户权限 */
    private String userPermission;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userLocal='" + userLocal + '\'' +
                ", userState=" + userState +
                ", userPermission='" + userPermission + '\'' +
                '}';
    }
}