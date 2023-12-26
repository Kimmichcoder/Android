package com.example.GameMine.Utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.example.GameMine.MainBin.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MySQLiteOpenHelper {

    private Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    //构造函数
    public MySQLiteOpenHelper(Context context) {


        this.context = context;
        Log.d("Debug1", "Creating MySQLiteOpenHelper");

        open();
    }



    //打开数据库方法
    public void open() throws SQLiteException {
        dbHelper = new MyDBHelper(context);
       // Log.d("fDebug1", "Creating MySQLiteOpenHelper");
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    //关闭数据库方法
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }


    //添加发布信息内容
    public long addGames(Game game) {
        // 创建ContentValues对象
        ContentValues values = new ContentValues();
        // 向该对象中插入值
        values.put("gameid", game.GameId);
        values.put("gamename", game.GameName);
        values.put("gametime", game.GameTime);
        values.put("gamenote", game.GameNote);
        values.put("userid",game.UserId);

        // 通过insert()方法插入数据库中
        return db.insert("tb_Game", null, values);
    }

    //删除发布信息
    public int deletGames(Game game) {
        return db.delete("tb_Game", "gameid=?", new String[]{String.valueOf(game.GameId)});
    }

    //修改发布信息
    public int updateGames(Game game) {
        ContentValues value = new ContentValues();
        value.put("gamename", game.GameName);
        value.put("gametime", game.GameTime);
        value.put("gamenote", game.GameNote);
        return db.update("tb_Game", value, "gameid=?", new String[]{String.valueOf(game.GameId)});
    }

    //根据游戏id查找以发布内容
    @SuppressLint("Range")
    public Game getGames(String gameid) {
        Cursor cursor = db.query("tb_Game", null, "gameid=?", new String[]{gameid}, null, null, null);
        Game game = new Game();
        game.GameId=null;
        while (cursor.moveToNext()) {
            game.GameId = cursor.getString(cursor.getColumnIndex("gameid"));
            game.GameName = cursor.getString(cursor.getColumnIndex("gamename"));
            game.GameTime = cursor.getString(cursor.getColumnIndex("gametime"));
            game.GameNote = cursor.getString(cursor.getColumnIndex("gamenote"));

        }
        return game;
    }

    @SuppressLint("Range")
    public String[] getGamesList(String userid) {


      Log.d("fuckyou1",userid);
        Cursor cursor = db.query("tb_Game", new String[]{"gameid"}, "userid=?", new String[]{userid}, null, null, null);
        List<String> gameIdList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String gameId = cursor.getString(cursor.getColumnIndex("gameid"));


            gameIdList.add(gameId);
        }

        cursor.close();

        // 将 List 转换为 String 数组
        String[] gameIdArray = gameIdList.toArray(new String[0]);
        return gameIdArray;
    }

    //查找所有发布信息
    @SuppressLint("Range")
    public ArrayList<Map<String, Object>> getAllGames() {
        ArrayList<Map<String, Object>> listGames = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.query("tb_Game", null, null, null, null, null, null);

        int resultCounts = cursor.getCount();
        if (resultCounts == 0) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("gameid", cursor.getString(cursor.getColumnIndex("gameid")));
                map.put("gamename", cursor.getString(cursor.getColumnIndex("gamename")));
                map.put("gametime", cursor.getString(cursor.getColumnIndex("gametime")));
                map.put("gamenote", cursor.getString(cursor.getColumnIndex("gamenote")));

                listGames.add(map);
            }
            return listGames;
        }
    }
    @SuppressLint("Range")
    public String[] getAllUserIds() {
        HashSet<String> userIdSet = new HashSet<>(); // 使用 HashSet 来存储不重复的 userid
        Cursor cursor = db.query("tb_Game", new String[]{"userid"}, null, null, null, null, null);

        Log.d("jjjjaa","here");
        int resultCounts = cursor.getCount();
        if (resultCounts == 0) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                String userId = cursor.getString(cursor.getColumnIndex("userid"));

                userIdSet.add(userId);
            }

            // 将 HashSet 转换为数组
            return userIdSet.toArray(new String[0]);
        }
    }

}