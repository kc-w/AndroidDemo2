package com.example.androiddemo2.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//SQLiteDatabase的帮助类，用于管理数据库的创建和版本的更新
public class MyOpenHelper extends SQLiteOpenHelper {

    //context对象,数据库名,cursor对象,数据库版本
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists "+"record"+
                        "(" +
                        "id_ integer primary key autoincrement, " +
                        "number char(20), "+
                        "id char(20), " +
                        "project char(20)," +
                        "result integer(10)," +
                        "time date(20)," +
                        "mark char(20)," +
                        "datas varchar(500)" +
                        ")"
        );

        db.execSQL(
                "create table if not exists "+"system"+
                        "(" +
                        "id_ integer, " +
                        "system1 integer, " +
                        "system2 integer, "+
                        "system3 integer, " +
                        "system4 integer" +
                        ")"
        );
    }

    //每一次数据库版本号发生变动时触发此方法
    //比如如果想往数据库中再插入一些表、字段或者其他信息时通过修改数据库版本号来触发此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+"record");
    }
}



