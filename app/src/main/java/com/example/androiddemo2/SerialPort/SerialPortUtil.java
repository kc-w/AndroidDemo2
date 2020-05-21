package com.example.androiddemo2.SerialPort;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android_serialport_api.SerialPort;


//通过串口用于接收或发送数据
public class SerialPortUtil {

    //串口操作对象
    private SerialPort serialPort = null;
    //串口输入流
    private InputStream inputStream = null;
    //串口输出流
    private OutputStream outputStream = null;

    //构建串口对象
    public void openSerialPort(String path,int bit,int flag) {
        try {
            serialPort = new SerialPort(new File(path), bit, flag);
            //调用对象SerialPort方法，获取串口中"读和写"的数据流
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //关闭串口
    public void closeSerialPort() {
        Log.i("SerialPortUtil", "关闭串口");
        try {

            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //发送数据到下位机
    public void sendSerialPort(String data) throws IOException, InterruptedException {
        //将发送的十六进制字符串转换为数组
        byte[] sendData = DataUtils.HexToByteArr(data);

        //发送给下位机
        outputStream.write(sendData);
        outputStream.flush();

        receive();
    }


    public void sendSerialPort(byte[] data) {
        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //包存放数组
    byte[] readData = new byte[1024];
    //byte数组
    ArrayList<Byte> SendData = new ArrayList();

    //接收数据
    public void receive() throws IOException {

        int size=0;

        Boolean flag=true;

        //如果读取到的大小一直为0则循环，直到读取到数据
        while (flag){
            size = inputStream.read(readData);

            if (size>0){
                Log.e("TAG", "数据包长度："+size );
                for (int i=0;i<size;i++){
                    //判断发送数据的数组长度是否为0
                    if (SendData.size()==0){
                        //如果遍历到包的值为2的话就加入数组的第1位
                        if (readData[i]==2){
                            SendData.add(readData[i]);
                        }
                    }else {
                        //数组中有数据进行累加
                        SendData.add(readData[i]);
                        //当数组中存储数据的长度大于4时，进行包长度正确性验证
                        if (SendData.size()>4){
                            //判断存储数据长度
                            if (SendData.size()==DataUtils.HLtoInt(SendData.get(2),SendData.get(1))){
                                //判断最后一个数据是否为3
                                if (SendData.get(SendData.size()-1)==3){
                                    flag=false;
                                    SendData.clear();
                                }else {
                                    flag=false;
                                    SendData.clear();
                                }
                            }
                        }

                    }

                }

            }

        }



        Log.e("TAG", SendData.toString());

    }



}
