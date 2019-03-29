package com.example.wangzhixiang.librarytest.control;

import com.example.wangzhixiang.librarytest.entity.User;
import com.example.wangzhixiang.librarytest.network.NetworkJava;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetworkControl {
    public static void login(Map params){
        NetworkJava.userLogin(params).subscribe(new Observer<Response<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(Response<User> userResponse) {
                EventBus.getDefault().post(new MessageEvent(userResponse.body()));
            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
