package com.example.wangzhixiang.librarytest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.wangzhixiang.librarytest.control.NetworkControl;
import com.example.wangzhixiang.librarytest.entity.DeviceInfo;
import com.example.wangzhixiang.librarytest.entity.User;
import com.example.wangzhixiang.librarytest.fragment.HomeFragment;
import com.example.wangzhixiang.librarytest.fragment.MessageFragment;
import com.example.wangzhixiang.librarytest.fragment.PeopleFragment;
import com.example.wangzhixiang.librarytest.utils.Utils;
import com.githang.statusbar.StatusBarCompat;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.one_lin)
    LinearLayout oneLinearLayout;
    @BindView(R.id.two_lin)
    LinearLayout twoLinearLayout;
    @BindView(R.id.three_lin)
    LinearLayout threeLinearLayout;

    HomeFragment homeFragment;
    MessageFragment messageFragment;
    PeopleFragment peopleFragment;
    FragmentManager fragmentManager;
    private static final int REQUEST_PHONE_STATE = 0;

    private static final String PACKAGE_URL_SCHEME = "package:";
//    private static PermissionListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (Build.VERSION.SDK_INT >=23){
        //申请 获取手机信息 权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
            }else{
                uploadDeviceInfo();
            }

        }
        fragmentManager = getSupportFragmentManager();
//        oneLinearLayout.setClickable(true);
        oneLinOnclik();
        StatusBarCompat.setStatusBarColor(this,  getResources().getColor(R.color.topbarBackground), true);
    }

    private void uploadDeviceInfo() {
        DeviceInfo deviceInfo = Utils.getDeviceInfo(this);
//        Log.i("deviceinfo", deviceInfo.toString());
        HashMap stringObjectMap = Utils.ObjectToMap(deviceInfo);
        String sign = Utils.getAppVerify(stringObjectMap);
        Log.i("sign", sign);
////        NetworkControl.login(stringObjectMap);
//        Log.i("deviceinfo", deviceInfo.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATE){
            for (int i=0;i <permissions.length; ++i){
                if (permissions[i].equals(Manifest.permission.READ_PHONE_STATE)){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        uploadDeviceInfo();
                    }else{
                        showPermissionDialog();
                    }
                    break;
                }
            }
        }


    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        if (grantResults.length >0){
            for (int i=0; i < grantResults.length; i++){
                int grandResult = grantResults[i];
                if (grandResult == PackageManager.PERMISSION_DENIED){
                    return false;
                }
            }
        }
        return true;
    }

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。\n该权限只为保障用户信息安全，不会用作非法用途。");
        // 拒绝, 退出应用
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.one_lin)
    public void oneLinOnclik(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (messageFragment != null)
            transaction.hide(messageFragment);
        if (peopleFragment != null)
            transaction.hide(peopleFragment);
        if (homeFragment == null){
            homeFragment = new HomeFragment();
            transaction.add(R.id.frame, homeFragment);
            transaction.show(homeFragment);
        }else{
            transaction.show(homeFragment);
        }
        transaction.commit();
    }
    @OnClick(R.id.two_lin)
    public void twoLinOnclik(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (peopleFragment != null)
            transaction.hide(peopleFragment);
        if (messageFragment == null){
            messageFragment = new MessageFragment();
            transaction.add(R.id.frame, messageFragment);
            transaction.show(messageFragment);
        }else{
            transaction.show(messageFragment);
        }
        transaction.commit();
    }
    @OnClick(R.id.three_lin)
    public void threeLinOnclik(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (messageFragment != null)
            transaction.hide(messageFragment);
        if (peopleFragment == null){
            peopleFragment = new PeopleFragment();
            transaction.add(R.id.frame, peopleFragment);
            transaction.show(peopleFragment);
        }else{
            transaction.show(peopleFragment);
        }
        transaction.commit();
    }
    @Subscribe
    public void loginResults(User user){
        if (user != null){
            Toast.makeText(this, "get user", Toast.LENGTH_LONG).show();
        }
    }
}
