package com.example.androiddemo2.Fragment.Fragment3_package;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androiddemo2.R;
import com.example.androiddemo2.SystemInfo.Systeminfomation;

public class Fragment33 extends Fragment {

    private View FragmentView;
    private TextView text335;

    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment33, container, false);
        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        text335 = getActivity().findViewById(R.id.text331);
        //总存储
        String TotalStorage = String.valueOf((float) Systeminfomation.getTotalInternalMemorySize()/1024/1024/1024);
        //可用存储
        String AvailableStorage = String.valueOf((float) Systeminfomation.getAvailableInternalMemorySize()/1024/1024/1024);
        text335.setText(AvailableStorage+"GB可用,共"+TotalStorage+"GB");



    }



}
