package com.example.androiddemo2.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android_serialport_api.SerialPortFinder;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.androiddemo2.Fragment.Fragment2_package.Fragment21;
import com.example.androiddemo2.Fragment.Fragment2_package.Fragment22;
import com.example.androiddemo2.R;
import com.example.androiddemo2.SerialPort.SerialPortUtil;
import com.example.androiddemo2.Sqlite.TestCase;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

//继承抽象类,实现Fragment,推荐片段
public class Fragment2 extends Fragment implements View.OnClickListener{

    //串口操作对象
    private SerialPortUtil serialPortUtil;

    //返回fragment3中的内容
    private View FragmentView;


    //初始化fragment类
    private Fragment21 f21;
    private Fragment22 f22;

    //按钮
    private RelativeLayout buttom1;
    private RelativeLayout buttom2;

    //数据操作
    private TestCase testCase;



    //Fragment管理器,用于在Activity中操作Fragment
    private FragmentManager fragmentManager;
    //对Fragment进行增加删除等操作
    private FragmentTransaction Transaction;


    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment2, container, false);
        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttom1 = getActivity().findViewById(R.id.R21);
        buttom2 = getActivity().findViewById(R.id.R22);


        buttom1.setOnClickListener(this);
        buttom2.setOnClickListener(this);

        fragmentManager = getFragmentManager();


        //获取可用串口
        try {
            SerialPortFinder prot = new SerialPortFinder();
            prot.getAllDevicesPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        serialPortUtil = new SerialPortUtil();
//        //开启串口传入串口名,波特率
//        serialPortUtil.openSerialPort("/dev/ttyS0",9600,0);

    }

    @Override
    public void onClick(View v) {

        //开启事务
        Transaction = fragmentManager.beginTransaction();
        //隐藏所有的fragment
        hideAllFragment(Transaction);

        //获取下导航标签
        RelativeLayout r1 = getActivity().findViewById(R.id.rl_second_layout);
        RelativeLayout r2 = getActivity().findViewById(R.id.main2);
        RelativeLayout r3 = getActivity().findViewById(R.id.main3);

        //(View.INVISIBLE,不显示，仍占有内存;View.GONE,不显示,也不占内存;View.VISIBLE,显示)
        r1.setVisibility(View.GONE);

        //获取当前点击的按钮
        switch (v.getId()) {
            case R.id.R21:

                //向下位机发出指令
//                System.out.println("发出十六进制数据");
//                for (int i =0;i<2;i++){
//                    serialPortUtil.sendSerialPort("FE");
//                }

                testCase = new TestCase(getActivity());
                testCase.insert();
                testCase.close();

                //显示隐藏的导航栏
                r2.setVisibility(View.VISIBLE);
                r2.setSelected(true);

                if (f21 == null) {
                    //创建f21对象
                    f21 = new Fragment21();
                    //通过事务将f21添加到mainFragment
                    Transaction.add(R.id.fragment_content, f21,"f21");
                    Log.d("Fragment2", "f21首次添加显示");
                } else {
                    //显示隐藏的f21
                    Transaction.show(f21);
                    Log.d("Fragment2", "f21显示");
                }
                break;

            case R.id.R22:

                //向下位机发出指令
                serialPortUtil.sendSerialPort("EF");

                //显示隐藏的导航栏
                r3.setVisibility(View.VISIBLE);
                r3.setSelected(true);

                if (f22 == null) {
                    //创建Fragment22对象
                    f22 = new Fragment22();
                    //通过事务将f22添加到mainFragment
                    Transaction.add(R.id.fragment_content, f22,"f22");
                    Log.d("Fragment2", "f22首次添加显示");
                } else {
                    //显示隐藏的f22
                    Transaction.show(f22);
                    Log.d("Fragment2", "f22显示");
                }
                break;

        }
        Transaction.commit();
    }

    //隐藏fragmtne
    private void hideAllFragment(FragmentTransaction transaction) {

        Fragment f1 = fragmentManager.findFragmentByTag("f1");
        Fragment f2 = fragmentManager.findFragmentByTag("f2");
        Fragment f3 = fragmentManager.findFragmentByTag("f3");

        if (f1 != null) {
            transaction.hide(f1);
            Log.d("Fragment2", "f1隐藏");
        }
        if (f2 != null) {
            transaction.hide(f2);
            Log.d("Fragment2", "f2隐藏");
        }
        if (f21 != null) {
            transaction.hide(f21);
            Log.d("Fragment2", "f21隐藏");
        }
        if (f22 != null) {
            transaction.hide(f22);
            Log.d("Fragment2", "f22隐藏");
        }
        if (f3 != null) {
            transaction.hide(f3);
            Log.d("Fragment2", "f3隐藏");
        }

    }

}


