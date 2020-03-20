package com.example.androiddemo2.SerialPort;

import android.util.Log;
import android_serialport_api.SerialPort;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


//通过串口用于接收或发送数据
public class SerialPortUtil {

    //串口操作对象
    private SerialPort serialPort = null;
    //串口输入流
    private InputStream inputStream = null;
    //串口输出流
    private OutputStream outputStream = null;
    //接收数据线程
    private ReceiveThread mReceiveThread = null;
    //串口开启标识
    private boolean isStart = false;

    /**
     * 打开串口，接收数据
     * 通过串口，接收单片机发送来的数据
     */
    public void openSerialPort(String path,int bit,int flag) {
        try {
            serialPort = new SerialPort(new File(path), bit, flag);
            //调用对象SerialPort方法，获取串口中"读和写"的数据流
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            isStart = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        //接收数据
        getSerialPort();
    }

    //开启线程接收数据
    private void getSerialPort() {
        if (mReceiveThread == null) {
            mReceiveThread = new ReceiveThread();
        }
        mReceiveThread.start();
    }

    /**
     * 关闭串口
     * 关闭串口中的输入输出流
     */
    public void closeSerialPort() {
        Log.i("SerialPortUtil", "关闭串口");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * 通过串口，发送数据到单片机
     *
     * @param data 要发送的数据
     */
    public void sendSerialPort(String data) {
        try {
            //将发送的十六进制字符串转换为数组
            byte[] sendData = data.getBytes();
            //发送给下位机
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //接收数据的线程类
    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            //如果串口开启成功，则一直执行这个线程
            while (isStart) {
                if (inputStream == null) {
                    return;
                }
                byte[] readData = new byte[1024];
                try {
                    int size = inputStream.read(readData);
                    if (size > 0) {
                        String readString = DataUtils.ByteArrToHex(readData, 0, size);
                        //十六进制转十进制
//                        String readInt = String.valueOf(DataUtils.HexToInt(readString));

                        System.out.println("1111111111111");
                        //将接收到的进制数据进行显示
                        EventBus.getDefault().post("readInt");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
