package com.example.androiddemo2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.androiddemo2.Fragment.Fragment3_package.Fragment31;
import com.example.androiddemo2.Fragment.Fragment3_package.Fragment32;
import com.example.androiddemo2.Fragment.Fragment3_package.Fragment33;
import com.example.androiddemo2.R;

//继承抽象类,实现Fragment,推荐片段
public class Fragment3 extends Fragment implements View.OnClickListener {


    //返回fragment3中的内容
    private View FragmentView;

    //初始化fragment类
    private Fragment31 f31;
    private Fragment32 f32;
    private Fragment33 f33;

    //左边3个按钮
    private RelativeLayout buttom31;
    private RelativeLayout buttom32;
    private RelativeLayout buttom33;

//    private LayoutInflater inflater;
//    //缓存Fragment view
//    private Context mainActivity;

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
        FragmentView = inflater.inflate(R.layout.fragment3, container, false);
        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mainActivity = getActivity();
//        inflater = LayoutInflater.from(getActivity());

        //初始化第一个freament31
        buttom31 = (RelativeLayout) getActivity().findViewById(R.id.f31);
        buttom32 = (RelativeLayout) getActivity().findViewById(R.id.f32);
        buttom33 = (RelativeLayout) getActivity().findViewById(R.id.f33);

        buttom31.setOnClickListener(this);
        buttom32.setOnClickListener(this);
        buttom33.setOnClickListener(this);

        fragmentManager = getFragmentManager();

        //默认第一个首页被选中高亮显示
        buttom31.setSelected(true);
        //开启一个事务，用于Fragment的一系列处理
        Transaction = fragmentManager.beginTransaction();
        //将mainFragment的内容替换为Fragment1
        f31 = new Fragment31();
        Transaction.replace(R.id.fragment3_content, f31);

        //提交当前事务
        Transaction.commit();

    }

    @Override
    public void onClick(View v) {

        //开启事务
        Transaction = fragmentManager.beginTransaction();
        //隐藏Fragment
        hideAllFragment(Transaction);

        //获取当前点击的按钮
        switch (v.getId()) {
            //f31
            case R.id.f31:
                //取消所有按钮的选中状态
                seleted();
                //设置按钮选中状态
                buttom31.setSelected(true);
                if (f31 == null) {
                    //创建Fragment1对象
                    f31 = new Fragment31();
                    //通过事务将Fragment1添加到mainFragment
                    Transaction.add(R.id.fragment_content, f31);
                } else {
                    //显示隐藏的Fragment1
                    Transaction.show(f31);
                }
                break;
            //f32
            case R.id.f32:
                seleted();
                buttom32.setSelected(true);
                if (f32 == null) {
                    f32 = new Fragment32();
                    Transaction.add(R.id.fragment3_content, f32);
                } else {
                    Transaction.show(f32);
                }
                break;
            //f33
            case R.id.f33:
                seleted();
                buttom33.setSelected(true);
                if (f33 == null) {
                    f33 = new Fragment33();
                    Transaction.add(R.id.fragment3_content, f33);
                } else {
                    Transaction.show(f33);
                }
                break;
        }
        //提交事务
        Transaction.commit();

    }


    //设置所有按钮都是默认都不选中
    private void seleted() {
        buttom31.setSelected(false);
        buttom32.setSelected(false);
        buttom33.setSelected(false);
    }

    //隐藏fragmtne
    private void hideAllFragment(FragmentTransaction transaction) {
        if (f31 != null) {
            transaction.hide(f31);
        }
        if (f32 != null) {
            transaction.hide(f32);
        }
        if (f33 != null) {
            transaction.hide(f33);
        }
    }

}

