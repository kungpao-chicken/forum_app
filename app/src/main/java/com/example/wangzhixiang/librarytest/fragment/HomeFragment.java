package com.example.wangzhixiang.librarytest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.wangzhixiang.librarytest.R;
import com.example.wangzhixiang.librarytest.entity.MenuItem;
import com.stx.xhb.pagemenulibrary.PageMenuLayout;
import com.stx.xhb.pagemenulibrary.holder.AbstractHolder;
import com.stx.xhb.pagemenulibrary.holder.PageMenuViewHolderCreator;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    private AppCompatActivity mActivity;
    @BindView(R.id.banner)
    MZBannerView mMZBanner;
    @BindView(R.id.pagemenu)
    PageMenuLayout pageMenuLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
//    @BindView(R.id.tabs)
//    PagerSlidingTabStrip tabStrips;
    @BindView(R.id.toolbar_me)
    Toolbar toolbar;
    List list;
    List homeEntrances;
    List fragmentList;
    public static final int []BANNER = new int[]{R.mipmap.banner1,R.mipmap.banner2, R.mipmap.banner3};
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.home_page, null);
        ButterKnife.bind(this, view);
        initData();
        pageMenuLayout.setPageDatas(homeEntrances, new MyMenuViewHolderCreator(mActivity));
        list = new ArrayList();
        for (int banner :BANNER){
            list.add(banner);
        }
        mMZBanner.setPages(list, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        toolbarTab.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
        toolbar.setTitle(""); //现在没有标题栏了 应该没有关系了
        mActivity.setSupportActionBar(toolbar);

    }
    //Banner
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(final Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "你想要什么呢？", Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
    public static class MyMenuViewHolderCreator implements PageMenuViewHolderCreator {
        Context myContext;
        MyMenuViewHolderCreator(Context context){
            myContext = context;
        }
        @Override
        public AbstractHolder createHolder(View itemView) {
            return new AbstractHolder(itemView) {
                private TextView entranceName;
                private ImageView entranceIcon;
                @Override
                protected void initView(View itemView) {
                    entranceName = itemView.findViewById(R.id.entrance_name);
                    entranceIcon = itemView.findViewById(R.id.entrance_image);
                    int screenWidth = myContext.getResources().getDisplayMetrics().widthPixels;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(screenWidth/4.0f));
                    itemView.setLayoutParams(layoutParams);
                }

                @Override
                public void bindView(android.support.v7.widget.RecyclerView.ViewHolder holder, final Object data, int pos) {
                    entranceName.setText(((MenuItem)data).getItemName());
                    entranceIcon.setImageResource(((MenuItem)data).getResource());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(myContext, ((MenuItem) data).getItemName(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            };
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_home_entrance;
        }
    }
    public class MyPagerAdapter extends FragmentPagerAdapter{
        FragmentManager fragmentManager;
        List<Fragment> mFragmentList;
        private final String[] title=new String[]{"热门", "最新", "关注"} ;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            fragmentManager = fm;
            mFragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return (CharSequence) title[position];
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

    }
    private void initData() {
        homeEntrances = new ArrayList<>();//网络获取
        homeEntrances.add(new MenuItem("美食", R.mipmap.ic_category_0));
        homeEntrances.add(new MenuItem("电影", R.mipmap.ic_category_1));
        homeEntrances.add(new MenuItem("酒店住宿", R.mipmap.ic_category_2));
        homeEntrances.add(new MenuItem("生活服务", R.mipmap.ic_category_3));
        homeEntrances.add(new MenuItem("KTV", R.mipmap.ic_category_4));
        //小tab
        fragmentList = new ArrayList();
        fragmentList.add(new HotMessageFragment());
        fragmentList.add(new NewMessageFragment());
        fragmentList.add(new FollowMessageFragment());
    }
}
