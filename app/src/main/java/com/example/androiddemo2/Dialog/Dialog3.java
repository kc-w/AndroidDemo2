package com.example.androiddemo2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.androiddemo2.R;

import java.util.Calendar;

//日期设置提示框
public class Dialog3 extends Dialog {

    //日期设置控件
    private DatePicker datePicker;
    //时间设置控件
    private TimePicker timePicker;

    //日期设置
    public static String time1;
    //时间设置
    public static String time2;



    //左,右按钮
    protected TextView LeftBtn;
    protected TextView RightBtn;

    //创建弹窗对象传入context和style样式
    public Dialog3(Context context, int style) {
        super(context, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 是否可以撤销
        this.setCancelable(false);
        //点击弹框外的区域关闭
        setCanceledOnTouchOutside(true);
        //装入界面
        setContentView(R.layout.dialog3);
        LeftBtn = (TextView) findViewById(R.id.dialog3_button1);
        RightBtn = (TextView) findViewById(R.id.dialog3_button2);
        datePicker = findViewById(R.id.set_Data);
        timePicker = findViewById(R.id.set_Time);


        datePicker.init(2020,3,20,new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,monthOfYear,dayOfMonth);
                time1 = year + String.valueOf(monthOfYear+1) + dayOfMonth +"";

            }
        });
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time2 = hourOfDay+minute+"00";

            }
        });

    }

    //设置文字和点击事件
    public void setRightButton(String rightStr, View.OnClickListener clickListener) {
        RightBtn.setOnClickListener(clickListener);
        RightBtn.setText(rightStr);
    }

    public void setLeftButton(String leftStr, View.OnClickListener clickListener) {
        LeftBtn.setOnClickListener(clickListener);
        LeftBtn.setText(leftStr);
    }



    @Override
    public void onBackPressed() {
        dismiss();
    }

}
