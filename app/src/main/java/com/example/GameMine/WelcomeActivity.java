package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.myexamproject.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置布局文件
        setContentView(R.layout.activity_welcome);

        // 创建处理器以延迟切换到下一个活动
        Handler handler = new Handler();

        // 使用处理器延迟发布操作
        // 当经过指定的延迟时间（3000毫秒或3秒）后，将执行run()方法
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 创建意图以切换到LoginActivity
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);

                // 启动LoginActivity
                startActivity(intent);

                // 关闭当前的WelcomeActivity
                WelcomeActivity.this.finish();
            }
        }, 3000);  // 延迟3秒
    }
}
