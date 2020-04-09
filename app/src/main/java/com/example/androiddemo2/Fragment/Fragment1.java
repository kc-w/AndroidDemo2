package com.example.androiddemo2.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.androiddemo2.Adapter.MyAdapter;
import com.example.androiddemo2.Bean.History;
import com.example.androiddemo2.Dialog.Dialog2;
import com.example.androiddemo2.Dialog.Dialog_Chart;
import com.example.androiddemo2.R;
import com.example.androiddemo2.Sqlite.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


//继承抽象类,实现Fragment,首页片段
public class Fragment1 extends Fragment implements View.OnClickListener{


    private View FragmentView;


    //日期选择
    private TextView start_data;
    private TextView end_data;
    // 用来装日期的
    private Calendar calendar;
    //时间选择弹窗
    private DatePickerDialog dialog;


    //记录操作按钮
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;

    //查询控件
    private EditText edit1;
    private EditText edit11;
    private EditText edit2;
    private EditText edit3;
    private EditText edit4;
    private EditText edit41;
    private Button submit_select;


    //列表视图
    private ListView lvData;
    //适配器
    private MyAdapter adapter;
    //所有数据
    private List<History> mData = new ArrayList<History>();
    //将选中数据放入里面
    private List<History> mCheckedData = new ArrayList<History>();
    //用来存放CheckBox的选中状态，true为选中,false为没有选中
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();
    //用来控制点击全选，全选和全不选相互切换
    private boolean isSelectedAll = true;
    //单选控制
    private boolean isSelecte = false;
    //控制下方的菜单显示和隐藏
    private LinearLayout mLlEditBar;





    @Override//创建Fragment时调用,只会调用一次
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override//每次创建,绘制该Fragment时调用,将显示的view返回
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment1, container, false);
        //返回完成的视图
        return FragmentView;
    }


    @Override//当Fragment所在的Activity启动完成后调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //日期
        start_data=getActivity().findViewById(R.id.edit4);
        end_data=getActivity().findViewById(R.id.edit41);

        //查询按钮
        submit_select = getActivity().findViewById(R.id.submit_select);

        //存放listview的地方
        lvData=getActivity().findViewById(R.id.ListView1);

        //按钮绑定
        b1=getActivity().findViewById(R.id.fragment1_b1);
        b2=getActivity().findViewById(R.id.fragment1_b2);
        b3=getActivity().findViewById(R.id.fragment1_b3);
        b4=getActivity().findViewById(R.id.fragment1_b4);
        b5=getActivity().findViewById(R.id.fragment1_b5);
        mLlEditBar = getActivity().findViewById(R.id.mLlEditBar);

        initView();
        initData();



        adapter = new MyAdapter(getActivity(), mData, stateCheckedMap);
        lvData.setAdapter(adapter);
        setOnListViewItemClickListener();
        setOnListViewItemLongClickListener();

    }

    int id_;
    TestCase testCase;
    History history;
    //点击选择按钮
    private void setOnListViewItemClickListener() {

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //在多选状态下可以进行单选,否则不能进行选中操作,只能查看图片
                if(isSelecte){
                    updateCheckBoxStatus(view, position);
                }else {
                    history = mData.get(position);
                    testCase = new TestCase(getContext());
                    id_ = history.getId_();
                    history = testCase.selectOne(id_);

                    //创建对象将数据传入
                    final Dialog_Chart dialogChart = new Dialog_Chart(getActivity(),R.style.Dialog1,history.getDatas());
                    //弹出图像
                    dialogChart.show();
                }
            }

        });
    }
    //长按弹出菜单
    private void setOnListViewItemLongClickListener() {
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //将标识设置为可以进行单选
                isSelecte = true;
                //显示下方布局
                mLlEditBar.setVisibility(View.VISIBLE);
                //CheckBox的那个方框显示
                adapter.setShowCheckBox(true);
                updateCheckBoxStatus(view, position);
                return true;
            }

        });
    }
    //复选框选中
    private void updateCheckBoxStatus(View view, int position) {
        MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) view.getTag();
        //反转CheckBox的选中状态
        holder.checkBox.toggle();
        //长按ListView时选中按的那一项
        lvData.setItemChecked(position, holder.checkBox.isChecked());
        //存放CheckBox的选中状态
        stateCheckedMap.put(position, holder.checkBox.isChecked());
        if (holder.checkBox.isChecked()) {
            //CheckBox选中时，把这一项的数据加到选中数据列表
            mCheckedData.add(mData.get(position));
        } else {
            //CheckBox未选中时，把这一项的数据从选中数据列表移除
            mCheckedData.remove(mData.get(position));
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        //绑定监听
        start_data.setOnClickListener(this);
        end_data.setOnClickListener(this);
        submit_select.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        //绑定listview
        lvData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    //第一次进入应用将所有数据加载到得list中存储
    private void initData() {
        TestCase testCase = new TestCase(getActivity());
        mData=testCase.select();
        testCase.close();
        setStateCheckedMap(false);
    }

    //设置各个数据的check状态
    private void setStateCheckedMap(boolean isSelectedAll) {
        for (int i = 0; i < mData.size(); i++) {
            stateCheckedMap.put(i, isSelectedAll);
            lvData.setItemChecked(i, isSelectedAll);
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //监听日期点击
            case R.id.edit4:
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth) {
                                start_data.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;

            case R.id.edit41:
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth) {
                                end_data.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            //查询
            case R.id.submit_select:
                selectData();
                break;
            //取消
            case R.id.fragment1_b1:
                cancel();
                break;
            //全选
            case R.id.fragment1_b2:
                selectAll();
                break;
            //删除
            case R.id.fragment1_b3:
                delete();

                break;
            //上传
            case R.id.fragment1_b4:
                Uload();
                break;
            //打印
            case R.id.fragment1_b5:
                break;

        }

    }

    //根据条件查询
    private void selectData(){
        //编号
        edit1 = getActivity().findViewById(R.id.edit1);
        edit11 = getActivity().findViewById(R.id.edit11);
        //ID
        edit2 = getActivity().findViewById(R.id.edit2);
        //检项
        edit3 = getActivity().findViewById(R.id.edit3);
        //测试时间
        edit4 = getActivity().findViewById(R.id.edit4);
        edit41 = getActivity().findViewById(R.id.edit41);

        String start_number = edit1.getText().toString();
        String end_number = edit11.getText().toString();
        String id = edit2.getText().toString();
        String jianxiang = edit3.getText().toString();
        String start_time = edit4.getText().toString().replace("/","-");
        String end_time = edit41.getText().toString().replace("/","-");

        String sql="select * from record where (1 = 1)";

        if(start_number.equals("") && end_number.equals("") && id.equals("") && jianxiang.equals("") && start_time.equals("") && end_time.equals("")){
            sql="select * from record";
        }

        if(!start_number.equals("") && !end_number.equals("")){
            sql = sql + "and (number between "+start_number+" and "+end_number+")";
        }else if(start_number.equals("") && !end_number.equals("")){
            sql = sql + "and (number <= "+end_number+")";
        }else if (!start_number.equals("") && end_number.equals("")){
            sql = sql + "and (number >= "+start_number+")";
        }else {
        }

        if(!id.equals("")){
            sql = sql + " and ( id = "+id+")";
        }

        if (!jianxiang.equals("")){
            sql = sql + " and ( project = '"+jianxiang+"')";
        }

        if(!start_time.equals("") && !end_time.equals("")){
            sql = sql + " and (time between '"+start_time+"' and '"+end_time+"')";
        }else if(start_time.equals("") && !end_time.equals("")){
            sql = sql + " and (time <= '"+end_number+"')";
        }else if (!start_time.equals("") && end_time.equals("")){
            sql = sql + " and (time >= '"+start_time+"')";
        }else {
        }

        System.out.println(sql);


        testCase = new TestCase(getActivity());
        mData = testCase.select(sql);
        setStateCheckedMap(false);
        System.out.println(mData.size());


        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);


    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 1){
                //存放listview的地方
                lvData=getActivity().findViewById(R.id.ListView1);
                initView();
                adapter = new MyAdapter(getActivity(), mData, stateCheckedMap);
                lvData.setAdapter(adapter);

            }
        }
    };



    //取消全选
    private void cancel() {
        //设置单选标识
        isSelecte = false;
        //将CheckBox的所有选中状态变成未选中
        setStateCheckedMap(false);
        //隐藏下方布局
        mLlEditBar.setVisibility(View.GONE);
        //让CheckBox那个方框隐藏
        adapter.setShowCheckBox(false);
        //更新ListView
        adapter.notifyDataSetChanged();
    }

    //全选数据
    private void selectAll() {
        //清空之前选中数据
        mCheckedData.clear();
        if (isSelectedAll) {
            //将CheckBox的所有选中状态变成选中
            setStateCheckedMap(true);
            isSelectedAll = false;
            //把所有的数据添加到选中列表中
            mCheckedData.addAll(mData);
        } else {
            //将CheckBox的所有选中状态变成未选中
            setStateCheckedMap(false);
            isSelectedAll = true;
        }
        adapter.notifyDataSetChanged();
    }

    //删除选数据
    private void delete() {
        if (mCheckedData.size() == 0) {
            Toast.makeText(getActivity(), "您还没有选中任何数据！", Toast.LENGTH_SHORT).show();
            return;
        }

        final Dialog2 dialog = new Dialog2(getActivity(),R.style.Dialog1);
        dialog.show();
        dialog.setHintText("是否删除？");
        dialog.setLeftButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除选中数据
                mData.removeAll(mCheckedData);
                //将CheckBox的所有选中状态变成未选中
                setStateCheckedMap(false);
                TestCase testCase = new TestCase(getActivity());
                testCase.delete(mCheckedData);
                testCase.close();

                //清空选中数据
                mCheckedData.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
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

    public void Uload(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path="http://192.168.0.101:8080/AndroidTest/mustLogin?logname="+"name"+"&password="+"psw";
                try {
                    try{
                        URL url = new URL(path); //新建url并实例化
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");//获取服务器数据
                        connection.setReadTimeout(8000);//设置读取超时的毫秒数
                        connection.setConnectTimeout(8000);//设置连接超时的毫秒数
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
                        Log.d("MainActivity","run: "+result);
                        if (result.equals("login successfully!")){
                            Log.d("MainActivity","run2: "+result);
                            Looper.prepare();
                            Log.d("MainActivity","run3: "+result);
                            Toast.makeText(getActivity(),"You logined successfully!",Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity","run4: "+result);
                            Looper.loop();
                        }
                    }catch (MalformedURLException e){}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }





}
