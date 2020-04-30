package com.example.androiddemo2;


import android.Manifest;
import android.app.DownloadManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.example.androiddemo2.Bean.MySystem;
import com.example.androiddemo2.Dialog.Dialog1;
import com.example.androiddemo2.Fragment.Fragment1;
import com.example.androiddemo2.Fragment.Fragment2;
import com.example.androiddemo2.Fragment.Fragment3;
import com.example.androiddemo2.Receiver.ScreenOffAdminReceiver;
import com.example.androiddemo2.Sqlite.TestCase;
import com.example.androiddemo2.Treads.Sleep_Treads;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "mainActivity";

    //初始化fragment类
    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;

    //从标签中获取fragment
    private Fragment f21;
    private Fragment f22;

    //底部5个按钮
    private RelativeLayout buttom1;
    private RelativeLayout buttom2;
    private RelativeLayout buttom21;
    private RelativeLayout buttom22;
    private RelativeLayout buttom3;

    private View view;



    //Fragment管理器,用于在Activity中操作Fragment
    private FragmentManager fragmentManager;
    //对Fragment进行增加删除等操作
    private FragmentTransaction Transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自动横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //加载主界面
        setContentView(R.layout.activity_main);
        //获取Fragment管理对象
        fragmentManager = getSupportFragmentManager();

        //初始化视图
        initView();

        //初始化系统设置
        initSystem();



    }


    private void initView() {
        //底部导航
        buttom1 = findViewById(R.id.rl_first_layout);
        buttom2 = findViewById(R.id.rl_second_layout);
        buttom21 = findViewById(R.id.main2);
        buttom22 = findViewById(R.id.main3);
        buttom3 = findViewById(R.id.rl_third_layout);


        //给3个按钮设置点击监听
        buttom1.setOnClickListener(this);
        buttom2.setOnClickListener(this);
        buttom21.setOnClickListener(this);
        buttom22.setOnClickListener(this);
        buttom3.setOnClickListener(this);


        //默认第一个首页被选中高亮显示
        buttom1.setSelected(true);
        //开启一个事务，用于Fragment的一系列处理
        Transaction = fragmentManager.beginTransaction();
        //将mainFragment的内容替换为Fragment1
        f1 = new Fragment1();
        Transaction.add(R.id.fragment_content, f1,"f1");
        Log.d("mainActivity", "f1默认初始化显示");

        //提交当前事务
        Transaction.commit();
    }

    @Override//点击切换Fragment
    public void onClick(View v) {
        //开启事务
        Transaction = fragmentManager.beginTransaction();
        //隐藏Fragment
        hideAllFragment(Transaction);

        //获取当前点击的按钮
        switch (v.getId()) {
            //首页
            case R.id.rl_first_layout:
                //取消所有按钮的选中状态
                seleted();
                //设置按钮选中状态
                buttom1.setSelected(true);
                //移除原来的f1
                Transaction.remove(f1);
                //新建f1对象
                f1 = new Fragment1();
                //将更新后的f1对象加入到管理器中
                Transaction.add(R.id.fragment_content, f1,"f1");
                Log.d("mainActivity", "f1显示");
                break;
            //主菜单
            case R.id.rl_second_layout:
                seleted();
                buttom2.setSelected(true);
                if (f2 == null) {
                    f2 = new Fragment2();
                    Transaction.add(R.id.fragment_content, f2,"f2");
                    Log.d("mainActivity", "f2首次添加显示");
                } else {
                    Transaction.show(f2);
                    Log.d("mainActivity", "f2显示");
                }
                break;
            //f21
            case R.id.main2:
                seleted();
                buttom21.setSelected(true);
                Transaction.show(f21);
                Log.d("mainActivity", "f21显示");
                break;
            //f22
            case R.id.main3:
                seleted();
                buttom22.setSelected(true);
                Transaction.show(f22);
                Log.d("mainActivity", "f22显示");

                break;
            //设置
            case R.id.rl_third_layout:
                seleted();
                buttom3.setSelected(true);
                if (f3 == null) {
                    f3 = new Fragment3();
                    Transaction.add(R.id.fragment_content, f3,"f3");
                    Log.d("mainActivity", "f3首次添加显示");
                } else {
                    Transaction.show(f3);
                    Log.d("mainActivity", "f3显示");
                }
                break;


        }

        //提交事务
        Transaction.commit();
    }

    //设置所有按钮都是默认都不选中
    private void seleted() {
        buttom1.setSelected(false);
        buttom2.setSelected(false);
        buttom21.setSelected(false);
        buttom22.setSelected(false);
        buttom3.setSelected(false);
    }

    //隐藏fragmtne
    private void hideAllFragment(FragmentTransaction transaction){
        f21 = fragmentManager.findFragmentByTag("f21");
        f22 = fragmentManager.findFragmentByTag("f22");

        if (f1 != null) {
            transaction.hide(f1);
            Log.d("mainActivity", "隐藏f1");
        }
        if (f2 != null) {
            transaction.hide(f2);
            Log.d("mainActivity", "隐藏f2");
        }
        if (f21 != null) {
            transaction.hide(f21);
            Log.d("mainActivity", "隐藏f21");
        }
        if (f22 != null) {
            transaction.hide(f22);
            Log.d("mainActivity", "隐藏f22");
        }
        if (f3 != null) {
            transaction.hide(f3);
            Log.d("mainActivity", "隐藏f3");
        }
    }

    //返回主菜单
    public void goHome(View v){

        Transaction = fragmentManager.beginTransaction();

        //隐藏Fragment
        hideAllFragment(Transaction);
        //取消所有选中项
        seleted();
        //导航可见
        buttom2.setVisibility(View.VISIBLE);
        buttom2.setSelected(true);

        //导航隐藏
        buttom21.setVisibility(View.GONE);
        buttom22.setVisibility(View.GONE);

        Transaction.show(f2);
        Transaction.commit();

        Log.d("mainActivity", "显示f2");
    }

    //退出
    public void over1(View v){
        view = getLayoutInflater().inflate(R.layout.dialog1, null);
        Dialog1 dialog1 = new Dialog1(this, view, R.style.Dialog1);
        dialog1.setCancelable(true);
        dialog1.show();
    }


    TestCase testCase;
    MySystem mySystem;
    DevicePolicyManager policyManager;
    ComponentName adminReceiver;
    PowerManager mPowerManager;
    Sleep_Treads sleep_treads;
    //初始化系统设置
    public   void initSystem() {
        testCase = new TestCase(this);
        mySystem = new MySystem();
        mySystem = testCase.selectSystem();

        //----亮度调节----
        //如果是第一次进入系统则初始化设置,在系统中插入默认设置
        if (mySystem == null) {
            testCase.insertSystem();
        }
        //设置系统屏幕亮度
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        //设置屏幕的亮度
        int liangdu = testCase.selectSystem().getSystem1();
        layoutParams.screenBrightness = (float) liangdu / 100;
        testCase.close();
        //重新设置窗口的属性
        getWindow().setAttributes(layoutParams);



        //----初始化休眠权限----
        adminReceiver = new ComponentName(MainActivity.this, ScreenOffAdminReceiver.class);
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        policyManager = (DevicePolicyManager) MainActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,  adminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启后就可以使用锁屏功能了...");
        startActivityForResult(intent, 0);
        //创建线程执行休眠
        sleep_treads = new Sleep_Treads();
        testCase = new TestCase(this);
        sleep_treads.setTime(testCase.selectSystem().getSystem2()*100000,policyManager);
        testCase.close();
        sleep_treads.start();


    }





    //触屏监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){

            System.out.println("触摸屏幕");
            sleep_treads.interrupt();

            System.out.println("重置休眠时间");
            sleep_treads = new Sleep_Treads();
            testCase = new TestCase(this);
            sleep_treads.setTime(testCase.selectSystem().getSystem2()*100000,policyManager);
            testCase.close();
            sleep_treads.start();
        }
        return super.onTouchEvent(event);
    }



}