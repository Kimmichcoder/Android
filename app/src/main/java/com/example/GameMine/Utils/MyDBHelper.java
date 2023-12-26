package com.example.GameMine.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    //常量定义
    public static final String name = "db_game1.db";
    public static final int DB_VERSION = 1;
    //创建表
    public static final String CREATE_USERDATA1 = "create table tb_Game(gameid char(10)primary key,gamename varchar(20),gametime varchar(20),gamenote varchar(20),userid varchar(20))";

    public MyDBHelper(Context context) {
        super(context, name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA1);
        db.execSQL("insert into tb_Game(gameid,gamename,gametime,gamenote,userid)Values('0xZWaoQa','王者荣耀','2023-2-8','山东省/青岛市','Qb2606414')");
        db.execSQL("insert into tb_Game(gameid,gamename,gametime,gamenote,userid)Values('Mp98qW4H','英雄联盟','2023-9-5','山东省/青岛市','Qb2606414')");
        db.execSQL("insert into tb_Game(gameid,gamename,gametime,gamenote,userid)Values('Wa9pOIT2','钢铁雄心4','2023-8-4','山东省/青岛市','Qb2606414')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}