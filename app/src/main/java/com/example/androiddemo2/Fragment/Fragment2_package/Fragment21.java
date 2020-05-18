package com.example.androiddemo2.Fragment.Fragment2_package;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androiddemo2.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static org.greenrobot.eventbus.EventBus.TAG;

public class Fragment21 extends Fragment {

    private View FragmentView;
    private EditText edit;

    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment21, container, false);

        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //注册EventBus
        EventBus.getDefault().register(this);


    }

    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventMainThread(String readInt){
        Log.d(TAG,"获取到了从传感器发送到Android主板的串口数据");



        edit = getActivity().findViewById(R.id.edit212);
        edit.setText(readInt);
    }

}


