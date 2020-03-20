package com.example.androiddemo2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.androiddemo2.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Dialog_Chart extends Dialog{


    //图表控件
    private LineChart lineChart;
    private String Datas;


    //创建弹窗对象传入context和style样式
    public Dialog_Chart(Context context, int style, String Datas) {
        super(context, style);
        this.Datas = Datas;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 是否可以撤销
        this.setCancelable(false);


        //装入界面
        setContentView(R.layout.dialog_chart);
        lineChart = findViewById(R.id.mLineChar);

        String[] datas=Datas.split(",");

        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        int i =0;
        for (String date :datas){
            entries.add(new Entry(i,Integer.parseInt(date)));
            i=i+1;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");

        //3.chart设置数据
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        //刷新
        lineChart.invalidate();



        //点击弹框外的区域关闭
        setCanceledOnTouchOutside(true);
    }



    @Override
    public void onBackPressed() {
        dismiss();
    }



}
