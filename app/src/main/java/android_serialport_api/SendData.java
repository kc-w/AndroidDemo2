package android_serialport_api;

import android.util.Log;

import com.example.androiddemo2.SerialPort.DataUtils;
import com.example.androiddemo2.SerialPort.SerialPortUtil;

import java.io.IOException;

public class SendData {

    //串口操作对象
    private static SerialPortUtil serialPortUtil;


    public static void open(){



        //获取可用串口
        try {
            SerialPortFinder prot = new SerialPortFinder();
            prot.getAllDevicesPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serialPortUtil = new SerialPortUtil();
        //开启串口传入串口名,波特率
        serialPortUtil.openSerialPort("/dev/ttyS1",9600,0);


        //发送系统参数
        serialPortUtil.sendSerialPort("0219001A");
        //keyword
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(100000,4));
        //EnIDcount
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(0,2));
        //Start_pos
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(1390,2));
        //WinWidth
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(80,2));
        //TestStep
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(-700,2));
        //MoveSpeed
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(90,2));
        //TestSpeed
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(160,2));
        //MaxStep
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(12000,2));
        //Bak
        serialPortUtil.sendSerialPort(DataUtils.InttoLH(0,2));

        serialPortUtil.sendSerialPort("03");


    }


    //快速检测
    public static void fast_check(){
        Log.d("SendData", "发出快速检测命令 ");
        serialPortUtil.sendSerialPort("0205001603");

    }



}
