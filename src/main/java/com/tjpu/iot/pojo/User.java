package com.tjpu.iot.pojo;

public class User {
    private String userId;

    private String userMobile;

    private String userPassword;

    private String userName;

    private String userCompany;

    private String userLocal;

    private Integer userState;

    private String userPermission;

    public User() {
    }

    public User(String userId, String userMobile, String userPassword, String userName, String userCompany, String userLocal, Integer userState, String userPermission) {
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
        this.userPermission = userPermission == null ? null : userPermission.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userLocal='" + userLocal + '\'' +
                ", userState=" + userState +
                ", userPermission='" + userPermission + '\'' +
                '}';
    }
}