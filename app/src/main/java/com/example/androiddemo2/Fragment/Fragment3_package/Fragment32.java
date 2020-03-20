package com.example.androiddemo2.Fragment.Fragment3_package;

import android.os.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androiddemo2.Bean.MySystem;
import com.example.androiddemo2.Dialog.Dialog3;
import com.example.androiddemo2.Bean.Flag;
import com.example.androiddemo2.R;
import com.example.androiddemo2.Sqlite.TestCase;
import com.example.androiddemo2.SystemInfo.Systeminfomation;
import org.greenrobot.eventbus.EventBus;


public class Fragment32 extends Fragment implements Runnable{

    private View FragmentView;
    private Spinner spinner321;
    private static final String[] spinner321List={"10分钟后休眠","20分钟后休眠","30分钟后休眠","60分钟后休眠"};
    private ArrayAdapter<String> adapter;
    private SeekBar SeekBar321;
    private SeekBar SeekBar322;
    private SeekBar SeekBar323;
    private Button button231;
    private TextView nowtime;

    private Handler handler;
    private MySystem mySystem;

    //休眠标识
    private Flag flag;



    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment32, container, false);
        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到下拉列表
        spinner321=(Spinner)getActivity().findViewById(R.id.spinner321);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,spinner321List);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner321.setAdapter(adapter);

        //设置系统时间
        nowtime = getActivity().findViewById(R.id.nowtime);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                nowtime.setText((String)msg.obj);
            }
        };
        new Thread(this).start();


        //得到拖动条
        SeekBar321 = (SeekBar) getActivity().findViewById(R.id.seekBar321);
        SeekBar322 = (SeekBar) getActivity().findViewById(R.id.seekBar322);
        SeekBar323 = (SeekBar) getActivity().findViewById(R.id.seekBar323);
        button231 = getActivity().findViewById(R.id.buttom321);
        Switch switch1 = (Switch) getActivity().findViewById(R.id.switch321);
        Switch switch2 = (Switch) getActivity().findViewById(R.id.switch322);



        //读取记录
        TestCase testCase = new TestCase(getActivity());
        mySystem = new MySystem();
        mySystem =testCase.selectSystem();
        //设置屏幕的亮度
        int liangdu = testCase.selectSystem().getSystem1();
        SeekBar321.setProgress(liangdu);
        testCase.close();

        //SeekBar321监听,亮度调节
        SeekBar321.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
            int temp;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //取得屏幕的属性
                WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
                temp = progress;
                //设置屏幕的亮度
                layoutParams.screenBrightness = (float) progress/100;
                //重新设置窗口的属性
                getActivity().getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TestCase testCase = new TestCase(getActivity());
                testCase.updateSystem(1,temp);
                testCase.close();
            }
        });


        //菜单点击监听
        spinner321.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), spinner321List[position], Toast.LENGTH_SHORT).show();
                flag = new Flag();
                flag.setNo1("sleep");
                flag.setNo2(spinner321List[position]);
                EventBus.getDefault().post(flag);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "选择2", Toast.LENGTH_SHORT).show();

            }

        });

        switch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SeekBar SeekBar1 = getActivity().findViewById(R.id.seekBar322);
                if(isChecked){
                    SeekBar1.setVisibility(View.VISIBLE);
                }else{
                    SeekBar1.setVisibility(View.GONE);
                }

            }

        });

        switch2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SeekBar SeekBar2 = getActivity().findViewById(R.id.seekBar323);
                if(isChecked){
                    SeekBar2.setVisibility(View.VISIBLE);
                }else{
                    SeekBar2.setVisibility(View.GONE);
                }

            }

        });

        button231.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                //系统时间修改弹窗
                final Dialog3 dialog = new Dialog3(getActivity(),R.style.Dialog1);
                dialog.show();
                dialog.setLeftButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //String datetime="20131023.112800";
                        if(dialog.time1 == null && dialog.time2 == null){



                        }else if(dialog.time1 == null && dialog.time2 !=null){

                        }else if(dialog.time1 != null && dialog.time2 == null){

                        }else {

                        }

                        dialog.dismiss();
                    }
                });
                dialog.setRightButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


    }

    @Override
    public void run() {
        try {
            while(true){
                String str = Systeminfomation.getTime();
                handler.sendMessage(handler.obtainMessage(100,str));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
