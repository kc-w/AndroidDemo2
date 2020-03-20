package com.example.androiddemo2.Treads;

import android.app.admin.DevicePolicyManager;

//休眠管理线程
public class Sleep_Treads extends Thread{

    private int time;
    private DevicePolicyManager policyManager;

    public void setTime(int time, DevicePolicyManager policyManager) {
        this.time=time;
        this.policyManager=policyManager;
    }

    //run()方法是子线程的核心方法,子线程从该方法开始执行,方法执行完后子线程终止
    public void run() {
        try {
            Thread.sleep(time);
            policyManager.lockNow();
        } catch (InterruptedException e) {
            System.out.println("触摸屏幕,退出本次锁屏倒计时");
            return;
        }
    }

}
