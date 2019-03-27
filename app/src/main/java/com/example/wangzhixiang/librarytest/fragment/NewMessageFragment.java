package com.example.wangzhixiang.librarytest.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzhixiang.librarytest.MainActivity;
import com.example.wangzhixiang.librarytest.R;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewMessageFragment extends Fragment {
    public static final int REQUEST_CODE_IMAGE = 101;
    private static final int REQUEST_STORAGE_STATE = 1;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private AppCompatActivity mActivity;
    @BindView(R.id.pick_button)
    Button pickButton;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(mActivity).inflate(R.layout.test_layout, null);
//        View root = View.inflate(mActivity, R.layout.test_layout, null);
        ButterKnife.bind(this, root);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        List<String> list = new ArrayList<>();
//        for (int i=0; i<20;i++){
//            list.add("test"+i);
//        }
//        recyclerView.setAdapter(new NewMessageFragment.MyAdapter(mActivity, list));
        return root;
    }
    @OnClick(R.id.pick_button)
    public void pickImage(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_STATE);
            }else {
                startPick();
            }
        }else {
            startPick();
        }
    }
    private void startPick(){
        SImagePicker.from(this)
                .maxCount(9)
                .rowCount(3)
                .showCamera(true)
                .pickMode(SImagePicker.MODE_IMAGE)
                .forResult(REQUEST_CODE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            final ArrayList<String> pathList =
                    data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            Log.i("path", pathList.get(0));
            final boolean original =
                    data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
//            pickAdapter.setNewData(pathList);
            Toast.makeText(mActivity, "原图：" + original, Toast.LENGTH_LONG).show();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<NewMessageFragment.MyAdapter.viewHolder>{
        private List<String> mList;
        private Context mContext;
        MyAdapter(Context context, List list){
            mContext = context;
            mList = list;
        }
        @NonNull
        @Override
        public NewMessageFragment.MyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mContext, R.layout.message_card, null);
            return new NewMessageFragment.MyAdapter.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewMessageFragment.MyAdapter.viewHolder viewHolder, int i) {
            viewHolder.textView.setText(mList.get(i));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
        class viewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv_username);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_STATE && hasAllPermissionsGranted(grantResults)){
            Toast.makeText(mActivity, "grant readstorage", Toast.LENGTH_LONG).show();
            startPick();
        }else{
            showPermissionDialog();
        }
    }private boolean hasAllPermissionsGranted(int[] grantResults) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。\n该权限只为读取图库，不会用作非法用途。");
        // 拒绝, 退出应用
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
            Toast.makeText(mActivity, "nonono", Toast.LENGTH_LONG).show();
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
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mActivity.getPackageName()));
        startActivity(intent);
    }
}
