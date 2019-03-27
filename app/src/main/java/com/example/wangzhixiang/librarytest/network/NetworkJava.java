package com.example.wangzhixiang.librarytest.network;


import com.example.wangzhixiang.librarytest.entity.User;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkJava {
    private static final String HEADAPI = "";
    public static Observable<Response<User>> userLogin(Map<String, String> params){
        return OkGo.<User>get(HEADAPI + "/user/register")
                .params(params)
                .converter(new BeanConvert<>(User.class))
                .adapt(new ObservableResponse<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
