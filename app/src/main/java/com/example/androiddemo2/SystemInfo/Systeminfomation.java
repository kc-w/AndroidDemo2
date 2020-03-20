package com.example.androiddemo2.SystemInfo;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import com.example.androiddemo2.Bean.MySystem;
import com.example.androiddemo2.MainActivity;
import com.example.androiddemo2.Receiver.ScreenOffAdminReceiver;
import com.example.androiddemo2.Sqlite.TestCase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.POWER_SERVICE;

public class Systeminfomation {



    //获取手机内部剩余存储空间
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    //获取手机内部总的存储空间
    public static long getTotalInternalMemorySize() {
         File path = Environment.getDataDirectory();
         StatFs stat = new StatFs(path.getPath());
         long blockSize = stat.getBlockSizeLong();
         long totalBlocks = stat.getBlockCountLong();
         return totalBlocks * blockSize;
    }

    //获得时间
    public static  String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }

    //得到细分的时间
    public static  String getTime1(String value){

        String str;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (value == "date"){
            str = year+month+day+"";
        }else {
            str = hour+minute+second+"";
        }

        return str;
    }





}
