package com.example.wangzhixiang.librarytest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangzhixiang.librarytest.R;
import com.example.wangzhixiang.librarytest.adapter.MessageRecyclerViewAdapter;
import com.example.wangzhixiang.librarytest.entity.CardItem;
import com.example.wangzhixiang.librarytest.entity.CardPic;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HotMessageFragment extends Fragment {
    private AppCompatActivity mActivity;
    @BindView(R.id.hot_recyclerview)
    RecyclerView recyclerView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(mActivity, R.layout.hot_message, null);
        ButterKnife.bind(this, root);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<CardItem> list = new ArrayList<>();
        for (int i=0; i<20;i++){
            CardItem cardItem = new CardItem();
            cardItem.content = "test";
            cardItem.evaluatereplys = null;
            cardItem.userName = "magician";
            cardItem.creatTime = "2019/3/21 10:44";
            List attachment = new ArrayList<CardPic>();
            for (int j=0;j<3; j++){
                CardPic cardPic = new CardPic();
                cardPic.setImageUrl("http://qiniu.snroom.com/@/default/all/0/bdee19e3d79b409ea00e56d2e076a796.png?e=1553139168&token=wf4SSB-waNgLkuLALYVcy7oRHRCbNc-dHILPEsFI:ljG9Q5L_SUlqxO-H4JH7MkpGVRI=");
                cardPic.setSmallImageUrl("http://qiniu.snroom.com/@/default/all/0/bdee19e3d79b409ea00e56d2e076a796.png?e=1553139168&token=wf4SSB-waNgLkuLALYVcy7oRHRCbNc-dHILPEsFI:ljG9Q5L_SUlqxO-H4JH7MkpGVRI=");
                cardPic.setFrom(1);
                attachment.add(cardPic);
            }
            CardPic cardPic = new CardPic();
            cardPic.setImageUrl("/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-03-25-21-23-29-353_com.example.wangzhixiang.librarytest.png");
            cardPic.setSmallImageUrl("/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-03-25-21-23-29-353_com.example.wangzhixiang.librarytest.png");
            cardPic.setFrom(0);
            attachment.add(cardPic);
            cardItem.setAttachments(attachment);
            list.add(cardItem);
        }
        recyclerView.setAdapter(new MessageRecyclerViewAdapter(mActivity, list));
        return root;
    }

}
