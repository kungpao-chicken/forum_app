package com.example.wangzhixiang.librarytest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangzhixiang.librarytest.R;
import com.example.wangzhixiang.librarytest.customview.CircleImageView;
import com.example.wangzhixiang.librarytest.entity.CardItem;
import com.example.wangzhixiang.librarytest.entity.CardPic;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.viewHolder>{
    private static final boolean DEBUG = true;
    private List<CardItem> mList;
    private Context mContext;
    public MessageRecyclerViewAdapter(Context context, List list){
        mContext = context;
        mList = list;
    }
    @NonNull
    @Override
    public MessageRecyclerViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_card, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerViewAdapter.viewHolder viewHolder, int i) {
        CardItem item = mList.get(i);
//            viewHolder.textView.setText(mList.get(i));
        viewHolder.username.setText(item.userName);
        viewHolder.content.setText(item.content);
        viewHolder.createTime.setText(item.creatTime);
        if (item.evaluatereplys == null){
            viewHolder.comments.setVisibility(View.GONE);
        }else{
            viewHolder.comments.setVisibility(View.VISIBLE);
            viewHolder.comments.setAdapter(new CommentsAdapter(mContext, item.evaluatereplys));
        }
        setImage(mContext, viewHolder.avatar, item.avatar == null?null:item.avatar.getPicUrl());
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<CardPic> imageDetails = item.getAttachments();
        if (imageDetails != null) {
            for (CardPic imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail.getSmallImageUrl());
                info.setBigImageUrl(imageDetail.getImageUrl());
                info.setFrom(imageDetail.getFrom());
                imageInfo.add(info);
            }
        }
        viewHolder.nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
    }

    private void setImage(Context mContext, CircleImageView avatar, String avatarUrl) {
        if (DEBUG){
            Glide.with(mContext).load(R.mipmap.ic_category_1).into(avatar);
        }else{
            Glide.with(mContext).load(avatarUrl).into(avatar);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class viewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_content)
        TextView content;
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.tv_username) TextView username;
        @BindView(R.id.tv_createTime) TextView createTime;
        @BindView(R.id.avatar) CircleImageView avatar;
        @BindView(R.id.lv_comments)
        ListView comments;
        private View rootView;
        private PopupWindow window;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.more)
        public void more(View view){
            View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_reply, null);
            window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setOutsideTouchable(true);
            window.setFocusable(true);
            window.setAnimationStyle(R.style.popup_more_anim);
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            popupView.measure(0, 0);
            int xoff = -popupView.getMeasuredWidth();
            int yoff = -(popupView.getMeasuredHeight() + view.getHeight()) / 2;
            window.showAsDropDown(view, xoff, yoff);
        }
    }
}

