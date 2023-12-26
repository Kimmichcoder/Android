package com.example.GameMine.Utils;

import android.util.Log;

import com.example.GameMine.MainBin.Game;

import java.util.Random;

public class IdGenerator {

    // 生成八位随机字符串
    public static String generateUniqueId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String uniqueId = "";
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            uniqueId+=(characters.charAt(random.nextInt(characters.length())));
        }

        Log.d("hhhhhhhhhhhh", String.valueOf(uniqueId));

        return uniqueId;
    }

    // 检查生成的随机字符串是否与数据库中现有ID重复
    public static boolean isUniqueId(String generatedId, MySQLiteOpenHelper dbHelper) {
        Game existingGame = dbHelper.getGames(generatedId);

        // 如果查询结果为空，则说明生成的ID是独一无二的
        return existingGame.GameId == null;
    }
}
