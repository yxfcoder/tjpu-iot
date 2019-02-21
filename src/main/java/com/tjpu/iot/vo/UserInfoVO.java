package com.tjpu.iot.vo;

public class UserInfoVO {
    private String userId;

    private String userMobile;

    private String userName;

    private String userCompany;

    private String userLocal;

    private Integer userState;

    private String userPermission;

    public UserInfoVO() {
    }

    public UserInfoVO(String userId, String userMobile, String userName, String userCompany, String userLocal, Integer userState, String userPermission) {
        this.userId = userId;
        this.userMobile = userMobile;
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
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(String userLocal) {
        this.userLocal = userLocal;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
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
