package com.example.wangzhixiang.librarytest.entity;

public class DeviceInfo {
    private String imei;
    private String androidId;
    private String serialNumber;
    private String macNumber;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMacNumber() {
        return macNumber;
    }

    public void setMacNumber(String macNumber) {
        this.macNumber = macNumber;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",imei, androidId, serialNumber, macNumber);
    }

}
