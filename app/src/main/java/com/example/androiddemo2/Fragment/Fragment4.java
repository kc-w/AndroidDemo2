package com.example.androiddemo2.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androiddemo2.Fragment.Fragment4_package.Fragment41;
import com.example.androiddemo2.Fragment.Fragment4_package.Fragment42;
import com.example.androiddemo2.R;
import com.google.android.material.tabs.TabLayout;

public class Fragment4 extends Fragment {

    private ViewPager2 vpTab;
    private TabLayout tabVp;


    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment4, container, false);
    }

//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//        //获取适配器
//        RgAdapter adapter = new RgAdapter(this);
//
//        //viewpager组件
//        vpTab = getActivity().findViewById(R.id.vp_tb);
//        //tab栏
//        tabVp = getActivity().findViewById(R.id.tb_vp);
//
//        //组件绑定适配器
//        vpTab.setAdapter(adapter);
//        //设置选项卡文字默认为黑色,选中时为蓝色
//        tabVp.setTabTextColors(Color.parseColor("#111111"),Color.parseColor("#0371DD"));
//
//
//
//        //设置选项卡内容文字
//        tabVp.addTab(tabVp.newTab().setText("第一个界面"));
//        tabVp.addTab(tabVp.newTab().setText("第二个界面"));
//
//
//        //适配器加入fragment
//        adapter.addFragment(new Fragment41());
//        adapter.addFragment(new Fragment42());
//
//        //设置首次加载时显示第几个fragment
//        vpTab.setCurrentItem(0);
//
//
//
//        tabVp.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            //点击选中监听
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                Log.d(getClass().getSimpleName(), "onTabSelected:11111111111 ");
//
//                vpTab.setCurrentItem(tab.getPosition());
//            }
//
//            //离开标签监听
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.d(tab.getText().toString(), "onTabUnselected:2222222222 ");
//
//            }
//
//            //重复点击标签监听
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                Log.d(getClass().getSimpleName(), "onTabReselected:33333333333 ");
//
//            }
//        });
//
//        //手势滑动监听
//        vpTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                //设置滑动选项卡状态(当前滚动位置,偏移,滑动是否更新选项卡颜色)
//                tabVp.setScrollPosition(position, 0, true);
//
//                Log.d(getClass().getSimpleName(), "onPageSelected:4444444444444 ");
//            }
//        });
//
//
//    }


}
