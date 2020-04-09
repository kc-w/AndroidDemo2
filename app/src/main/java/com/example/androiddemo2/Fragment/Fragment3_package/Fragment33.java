package com.example.androiddemo2.Fragment.Fragment3_package;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.example.androiddemo2.R;
import com.example.androiddemo2.SystemInfo.Systeminfomation;

public class Fragment33 extends Fragment implements View.OnClickListener {

    private View FragmentView;
    private TextView text335;

    private TextView downLoadApk;
    private String Url = "http://www.zkthivd.com/downloads/app.apk";

    private ProgressBar progressBar331;

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

        text335 = getActivity().findViewById(R.id.text335);
        progressBar331 = getActivity().findViewById(R.id.progressBar331);
        downLoadApk = getActivity().findViewById(R.id.downLoadApk);
        downLoadApk.setOnClickListener(this);

        //总存储
        String TotalStorage = String.valueOf((int) (Systeminfomation.getTotalInternalMemorySize()/1024/1024/1024));
        //可用存储
        String AvailableStorage = String.valueOf((int) (Systeminfomation.getAvailableInternalMemorySize()/1024/1024/1024));

        int num1 = Integer.valueOf(TotalStorage);
        int num2 = Integer.valueOf(AvailableStorage);


        progressBar331.setMax(num1);
        progressBar331.setProgress(num2);

        text335.setText(AvailableStorage+"GB可用,共"+TotalStorage+"GB");


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.downLoadApk:

                AllenVersionChecker
                        .getInstance()
                        .downloadOnly(
                                UIData.create()
                                        .setTitle("更新")
                                        .setContent("新版本下载!")
                                        .setDownloadUrl(Url)
                        )
                        .executeMission(getActivity());
                break;


        }

    }






}
