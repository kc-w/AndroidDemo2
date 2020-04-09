package com.example.androiddemo2.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.androiddemo2.Bean.History;
import com.example.androiddemo2.Bean.MySystem;

import java.util.ArrayList;
import java.util.List;

//增删改查
public class TestCase{

    private MyOpenHelper oh;
    private SQLiteDatabase db;

    private History history ;
    private MySystem mySystem;
    private ArrayList<History> historyList = new ArrayList();

    public TestCase(Context context){
        //创建数据库
        oh = new MyOpenHelper(context,"k720.db",null,1);
        //得到数据库读写对象
        db=  oh.getWritableDatabase();
        //创建表
        oh.onCreate(db);
    }
    //关闭数据库
    public void close(){
        db.close();
    }

    //添加记录
    public void insert(){
        db.execSQL("insert into record (number, id, project, result, time, mark, datas)values(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"1", "1", "项目1","通过","2020-4-1","无备注","11,44,55,32,54,25,56,456,23,645,23,456,23,453,23,435"});
        db.execSQL("insert into record (number, id, project, result, time, mark, datas)values(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{"2", "2", "项目2","通过","2000-1-2","无备注","81,94,25,32,54,25,56,46,23,45,23,46,23,43,23,35"});
    }
    //批量记录
    public void delete(List<History> historyList){
        for (History history:historyList){
            db.execSQL("delete from record where id_ = ?",new Object[]{history.getId_()});
        }
    }
    //修改
    public void update(){
        db.execSQL("update record set id_ = ? where id_ = ?", new Object[]{321312, "aaaaa"});
    }
    //查询所有记录
    public ArrayList<History> select(){
        Cursor cursor = db.rawQuery("select id_, number, id, project, result, time, mark from record ", null);
        while(cursor.moveToNext()){

            int id_ = cursor.getInt(0);
            String number = cursor.getString(1);
            String id = cursor.getString(2);
            String project = cursor.getString(3);
            String result = cursor.getString(4);
            String time = cursor.getString(5);
            String mark = cursor.getString(6);

            history = new History();
            history.setId_(id_);
            history.setNumber(number);
            history.setId(id);
            history.setProject(project);
            history.setResult(result);
            history.setTime(time);
            history.setMark(mark);
            historyList.add(history);

        }
        if(cursor != null){
            cursor.close();
        }
        return historyList;
    }


    //自定义sql查询
    public ArrayList<History> select(String sql){
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int id_ = cursor.getInt(0);
            String number = cursor.getString(1);
            String id = cursor.getString(2);
            String project = cursor.getString(3);
            String result = cursor.getString(4);
            String time = cursor.getString(5);
            String mark = cursor.getString(6);

            history = new History();
            history.setId_(id_);
            history.setNumber(number);
            history.setId(id);
            history.setProject(project);
            history.setResult(result);
            history.setTime(time);
            history.setMark(mark);
            historyList.add(history);

        }
        if(cursor != null){
            cursor.close();
        }
        return historyList;
    }

    //查询单个记录
    public History selectOne(int id_){

        Cursor cursor = db.rawQuery("select * from record where id_ = ?", new String[]{String.valueOf(id_)});
        while(cursor.moveToNext()){
            String number = cursor.getString(1);
            String id = cursor.getString(2);
            String project = cursor.getString(3);
            String result = cursor.getString(4);
            String time = cursor.getString(5);
            String mark = cursor.getString(6);
            String datas = cursor.getString(7);
            history = new History();
            history.setNumber(number);
            history.setId(id);
            history.setProject(project);
            history.setResult(result);
            history.setTime(time);
            history.setMark(mark);
            history.setDatas(datas);
        }

        return history;

    }







    //查询系统数据
    public MySystem selectSystem(){
        Cursor cursor = db.rawQuery("select * from system ", null);
        if(cursor == null){
            return null;
        }
        while(cursor.moveToNext()){
            int system1 = cursor.getInt(1);
            int system2 = cursor.getInt(2);
            int system3 = cursor.getInt(3);
            int system4 = cursor.getInt(4);
            mySystem = new MySystem();
            mySystem.setSystem1(system1);
            mySystem.setSystem2(system2);
            mySystem.setSystem3(system3);
            mySystem.setSystem4(system4);
        }
        cursor.close();
        return mySystem;
    }
    //插入系统数据
    public void insertSystem(){
        db.execSQL("insert into system (id_,system1,system2,system3,system4)values(?,?,?,?,?)",new Object[]{1,50,3600,50,50});
    }
    //更新系统数据
    public void updateSystem(int i1,int i2){
        if(i1==1){
            db.execSQL("update system set system1 = ? where id_ = 1", new Object[]{i2});
        }
        if(i1==2){
            db.execSQL("update system set system2 = ? where id_ = 1", new Object[]{i2});
        }
        if(i1==3){
            db.execSQL("update system set system3 = ? where id_ = 1", new Object[]{i2});
        }
        if(i1==4) {
            db.execSQL("update system set system4 = ? where id_ = 1", new Object[]{i2});
        }
    }


//    //API方法实现增删该查
//    public void insertApi(){
//        //把要插入的数据全部封装到ContentValues对象
//        ContentValues values = new ContentValues();
//        values.put("name", "xxx");
//        values.put("phone", "234324");
//        values.put("salary",13423);
//        db.insert("person", null, values);
//    }
//
//    public void deleteApi(){
//        //返回删除的行数
//        int i = db.delete("person", "name = ? and _id = ?", new String[]{"aaaaa" , "4"});
//    }
//
//    public void updateApi(){
//        ContentValues values = new ContentValues();
//        values.put("salary", 17000);
//        //返回修改的行数
//        int i = db.update("person", values, "name = ?", new String[]{"xxx"});
//        System.out.println("i"+i);
//    }
//
//    public void selectApi(){
//        Cursor cursor = db.query("person", null, null, null, null, null, null, null);
//        while(cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String phone = cursor.getString(cursor.getColumnIndex("phone"));
//            String salary = cursor.getString(cursor.getColumnIndex("salary"));
//            System.out.println("name = " + name + "phone = " + phone + "salary = " + salary);
//        }
//    }
//
//    public void transation(){
//        try{
//            //开启事务
//            db.beginTransaction();
//            ContentValues values = new ContentValues();
//            values.put("salary", 40000);
//            db.update("person", values, "name = ?", new String[]{"luodewu"});
//
//            //上下都使用一个values防止数据干扰
//            values.clear();
//            values.put("salary", 20000);
//            db.update("person", values, "name = ?", new String[]{"luodewuluodewu"});
//
//            //设置事物执行成功
//            db.setTransactionSuccessful();
//        }
//        finally{
//            //关闭事物，同时提交,如果已经设置事务执行成功，sql语句就生效了，反之语句回滚
//            db.endTransaction();
//        }
//    }

}