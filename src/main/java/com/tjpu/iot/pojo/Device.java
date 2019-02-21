package com.tjpu.iot.pojo;

public class Device {
    private String deviceId;

    private String deviceName;

    private String deviceModel;

    private String deviceType;

    private String deviceProtocol;

    private String deviceCompany;

    private String deviceRunState;

    private String deviceUserId;

    /** 设备纬度 */
    private String deviceLatitude;

    /** 设备经度 */
    private String deviceLongitude;

    private String deviceLocal;

    private String deviceState;

    private String deviceRemark;

    public Device() {
    }

    public Device(String deviceId, String deviceName, String deviceModel, String deviceType, String deviceProtocol, String deviceCompany, String deviceRunState, String deviceUserId, String deviceLatitude, String deviceLongitude, String deviceLocal, String deviceState, String deviceRemark) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.deviceType = deviceType;
        this.deviceProtocol = deviceProtocol;
        this.deviceCompany = deviceCompany;
        this.deviceRunState = deviceRunState;
        this.deviceUserId = deviceUserId;
        this.deviceLatitude = deviceLatitude;
        this.deviceLongitude = deviceLongitude;
        this.deviceLocal = deviceLocal;
        this.deviceState = deviceState;
        this.deviceRemark = deviceRemark;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceProtocol() {
        return deviceProtocol;
    }

    public void setDeviceProtocol(String deviceProtocol) {
        this.deviceProtocol = deviceProtocol == null ? null : deviceProtocol.trim();
    }

    public String getDeviceCompany() {
        return deviceCompany;
    }

    public void setDeviceCompany(String deviceCompany) {
        this.deviceCompany = deviceCompany == null ? null : deviceCompany.trim();
    }

    public String getDeviceRunState() {
        return deviceRunState;
    }

    public void setDeviceRunState(String deviceRunState) {
        this.deviceRunState = deviceRunState == null ? null : deviceRunState.trim();
    }

    public String getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(String deviceUserId) {
        this.deviceUserId = deviceUserId == null ? null : deviceUserId.trim();
    }

    public String getDeviceLatitude() {
        return deviceLatitude;
    }

    public void setDeviceLatitude(String deviceLatitude) {
        this.deviceLatitude = deviceLatitude == null ? null : deviceLatitude.trim();
    }

    public String getDeviceLongitude() {
        return deviceLongitude;
    }

    public void setDeviceLongitude(String deviceLongitude) {
        this.deviceLongitude = deviceLongitude == null ? null : deviceLongitude.trim();
    }

    public String getDeviceLocal() {
        return deviceLocal;
    }

    public void setDeviceLocal(String deviceLocal) {
        this.deviceLocal = deviceLocal == null ? null : deviceLocal.trim();
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState == null ? null : deviceState.trim();
    }

    public String getDeviceRemark() {
        return deviceRemark;
    }

    public void setDeviceRemark(String deviceRemark) {
        this.deviceRemark = deviceRemark == null ? null : deviceRemark.trim();
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceProtocol='" + deviceProtocol + '\'' +
                ", deviceCompany='" + deviceCompany + '\'' +
                ", deviceRunState='" + deviceRunState + '\'' +
                ", deviceUserId='" + deviceUserId + '\'' +
                ", deviceLatitude='" + deviceLatitude + '\'' +
                ", deviceLongitude='" + deviceLongitude + '\'' +
                ", deviceLocal='" + deviceLocal + '\'' +
                ", deviceState='" + deviceState + '\'' +
                ", deviceRemark='" + deviceRemark + '\'' +
                '}';
    }
}