package com.tjpu.iot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

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

    /** 用户所在区域 */
    private String userLocal;

    /** 用户状态 0离线 1在线 */
    private String userState;

    /** 用户权限 */
    private String userPermission;

    public User(String userId, String userMobile, String userPassword, String userName, String userCompany, String userLocal, String userState, String userPermission) {
        this.userId = userId;
        this.userMobile = userMobile;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userCompany = userCompany;
        this.userLocal = userLocal;
        this.userState = userState;
        this.userPermission = userPermission;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany == null ? null : userCompany.trim();
    }

    public String getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(String userLocal) {
        this.userLocal = userLocal == null ? null : userLocal.trim();
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission == null ? null : userPermission.trim();
    }

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