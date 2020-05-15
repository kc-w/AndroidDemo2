package com.example.androiddemo2.SerialPort;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android_serialport_api.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


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
    //校验数据线程
    private CheckThread mCheckThread = null;
    //超时检测线程
    private SendThread mSendThread = null;
    private Data_class mData_class = null;
    //串口开启标识
    private boolean isStart = false;


    /**
     * 打开串口，接收数据
     * 通过串口，接收发送来的数据
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
        //开启数据处理线程
        getSerialPort();
    }

    //开启数据线程
    private void getSerialPort() {

        if (mData_class == null) {
            mData_class = new Data_class();
        }
        if (mReceiveThread == null) {
            mReceiveThread = new ReceiveThread(mData_class);
        }
        if (mCheckThread == null) {
            mCheckThread = new CheckThread(mData_class);
        }
        if (mSendThread==null){
            mSendThread=new SendThread(mData_class);
        }
        mReceiveThread.start();
        mCheckThread.start();
        mSendThread.start();
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


    //通过串口，发送数据到下位机
    public void sendSerialPort(String data) {
        try {
            //将发送的十六进制字符串转换为数组
            byte[] sendData = DataUtils.HexToByteArr(data);
            if (sendData.length==2){
                System.out.println(sendData[0]+" "+sendData[1]);
            }

            //发送给下位机
            outputStream.write(sendData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendSerialPort(byte[] data) {
        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //存放缓冲区读取到的数据
    byte[] readData = new byte[1024];




    public class Data_class {
        int size=0;
        //存放缓冲区的数据
        ArrayList<Byte> SendData = new ArrayList();
        //需要发送的数据
        ArrayList<Byte> data=new ArrayList();

        public  ArrayList<Byte> getSendData() {
            return SendData;
        }
        public  ArrayList<Byte> getData() {
            return data;
        }

        public  void setSize(int size) {
            this.size=size;
        }
        public  void addData (byte b) {
            data.add(b);
        }
        public  void clear() {
            data.clear();
        }
        public  void add(int size,byte[] readDatum) {
            for (int i=0;i<size;i++){
                SendData.add(readDatum[i]);
            }
            Log.d(TAG, "添加数据执行 "+SendData.toString());
        }
        public  void subtract(int i) {

            Log.d(TAG, "移除数据执行 " + SendData.toString());
            for (int j = 0; j <= i; j++) {
                SendData.remove(0);
            }
        }



    }

    //接收数据的线程类
    private class ReceiveThread extends Thread {

        Data_class data_class;
        public ReceiveThread(Data_class data_class){
            this.data_class=data_class;
        }

        @Override
        public void run() {


            Log.d(TAG, "接收数据线程执行");
            //如果串口开启成功，则执行这个线程
            while (true){
                while (isStart) {
                    if (inputStream == null ) {
                        return;
                    }
                    try {
                        //如果缓冲区无数据则会卡在此处
                        int size = inputStream.read(readData);
                        data_class.add(size,readData);
                        isStart=false;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    boolean checkFlag=false;
    //超时检测线程
    public class SendThread extends Thread {
        Data_class data_class;
        public SendThread(Data_class data_class){
            this.data_class=data_class;
        }

        public void run(){

            while (true){

                while (checkFlag){

                    for (int i=0;i<100;i++){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (data_class.getData().size()==data_class.size){
                            if (data_class.getData().size()>10){
                                message(1,"shuju",data_class.getData());
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            data_class.clear();
                            checkFlag=false;
                            break;
                        }
                        if (i==100){
                            if (data_class.getData().size()!=0 && data_class.getData().size()!=data_class.size){
                                message(2,"shuju",data_class.getData());
                                data_class.clear();
                                checkFlag=false;
                            }
                        }

                    }

                }

            }


        }

    }

    //数据校验线程
    private class CheckThread extends Thread {

        boolean flag = true;

        Data_class data_class;
        public CheckThread(Data_class data_class){
            this.data_class=data_class;
        }


        @Override
        public void run() {

            Log.d(TAG, "发送数据线程执行");
            while (true){

                while (!isStart){
                    ArrayList<Byte> sendDate=data_class.getSendData();
                    int size=sendDate.size();

                    if(size>0){
                        for (int i=0;i<size;i++){
                            if (sendDate.get(i) ==2 && flag){
                                data_class.addData(sendDate.get(i));
                                data_class.setSize(DataUtils.HLtoInt(sendDate.get(i+2),sendDate.get(i+1)));
                                //首字符通过检测
                                flag=false;
                                //数据超时检测开启
                                checkFlag=true;
                                continue;
                            }
                            //确定首字节后进行累加操作
                            if (!flag){
                                data_class.addData(sendDate.get(i));
                                //累加长度达到标准正确长度
                                if (data_class.size==data_class.getData().size()){

                                    if(sendDate.get(i)==3){
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        data_class.subtract(i);
                                        flag=true;
                                        isStart=true;
                                        continue;
                                    }

                                }

                                //有多个数据包且循环到最后一个数据时清除接收到的数据重新从缓冲区读取数据
                                if (i+1==size){
                                    data_class.subtract(i);
                                    isStart=true;
                                }

                            }

                        }
                    }
                }
            }
        }
    }


    Bundle bundle = new Bundle();
    private static Handler mHandler;
    public static void setHandler(Handler handler) {
        mHandler = handler;
    }

    //发送数据
    public void message(int flag,String mesage,ArrayList tempData){
        bundle.putCharSequenceArrayList(mesage,tempData);
        Message message = Message.obtain();
        message.setData(bundle);
        message.what =flag ;
        mHandler.sendMessage(message);
    }

}
