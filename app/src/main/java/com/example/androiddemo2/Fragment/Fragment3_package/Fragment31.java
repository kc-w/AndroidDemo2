package com.example.androiddemo2.Fragment.Fragment3_package;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androiddemo2.R;

public class Fragment31 extends Fragment{

    private View FragmentView;

    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment31, container, false);

        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Switch switch1 = (Switch) getActivity().findViewById(R.id.switch311);
        Switch switch2 = (Switch) getActivity().findViewById(R.id.switch312);
        Switch switch3 = (Switch) getActivity().findViewById(R.id.switch313);



        //switch1点击监听
        switch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button button1 = getActivity().findViewById(R.id.buttom311);
                if(isChecked){
                    button1.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "选中", Toast.LENGTH_LONG).show();
                }else{
                    button1.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
                }

            }

            });
        //switch2点击监听
        switch2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button button2 = getActivity().findViewById(R.id.buttom312);
                if(isChecked){
                    button2.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "选中", Toast.LENGTH_LONG).show();
                }else{
                    button2.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
                }

            }

        });
        //switch3点击监听
        switch3.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button button3 = getActivity().findViewById(R.id.buttom313);
                if(isChecked){
                    button3.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "选中", Toast.LENGTH_LONG).show();
                }else{
                    button3.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
                }

            }

        });
        }
}

