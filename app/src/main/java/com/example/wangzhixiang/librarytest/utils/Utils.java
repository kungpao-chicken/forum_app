package com.example.wangzhixiang.librarytest.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.wangzhixiang.librarytest.entity.DeviceInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    static {
        System.loadLibrary("crypto");
    }
    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        deviceInfo.setImei(imei);

        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        deviceInfo.setAndroidId(androidId);
//        String serialNumber = android.os.Build.SERIAL;
        @SuppressLint("MissingPermission") String serialNumber = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();

        deviceInfo.setSerialNumber(serialNumber);
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        deviceInfo.setMacNumber(macAddress);
        return deviceInfo;
    }
    public static HashMap<String, String> ObjectToMap(Object object){

        HashMap<String, String> stringObjectMap = JSON.parseObject(JSON.toJSONString(object), new TypeReference<HashMap<String, String>>(){});
        return stringObjectMap;
    }
    public native static String getAppVerify(Map map);
}
