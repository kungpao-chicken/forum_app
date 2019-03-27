package com.example.wangzhixiang.librarytest;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imnjh.imagepicker.ImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.io.File;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NineGridView.setImageLoader(new GlideImageLoader());
        initOkGo();
        initImagePicker();
    }

    private void initImagePicker() {
        SImagePicker.init(new PickerConfig.Builder()
                .setAppContext(this)
                .setImageLoader(new ImagePickerImageLoader(this))
                .setToolbaseColor(getResources().getColor(R.color.colorPrimary))
                .build());
    }
    private class ImagePickerImageLoader implements ImageLoader{
        Context context;
        public ImagePickerImageLoader(Context context){
            this.context = context;
        }
        @Override
        public void bindImage(ImageView imageView, Uri uri, int width, int height) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.drawable.place);
            requestOptions.override(width, height);
            requestOptions.dontAnimate();
            Glide.with(this.context).load(uri).apply(requestOptions).into(imageView);
        }

        @Override
        public void bindImage(ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.drawable.place);
            requestOptions.dontAnimate();
            Glide.with(context).load(uri).apply(requestOptions).into(imageView);
        }

        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public ImageView createFakeImageView(Context context) {
            return new ImageView(context);
        }
    }
    private void initOkGo() {
        try {
            OkGo.getInstance().init(this)
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .setRetryCount(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Picasso 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url, int from) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.place);
            requestOptions.error(R.drawable.place);
            if (from == 1){
                Glide.with(context).load(url)
                        .apply(requestOptions)
                        .into(imageView);
            }else{
                File file = new File(url);
                Glide.with(context).load(file)
                        .apply(requestOptions)
                        .into(imageView);
            }

        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
