package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myexamproject.R;

/**
 * 退出界面的活动
 */
public class BackActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_backfalse;    // 返回按钮（取消退出）
    private Button bt_backtrue;     // 返回按钮（确认退出）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        bt_backfalse = findViewById(R.id.bt_backfalse);
        bt_backfalse.setOnClickListener(this);

        bt_backtrue = findViewById(R.id.bt_backtrue);
        bt_backtrue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_backfalse:
                // 点击取消退出按钮，返回到个人中心界面
                Intent intent_insert = new Intent();
                intent_insert.setClass(BackActivity.this, PersonalCenterActivity.class);
                startActivity(intent_insert);
                break;

            case R.id.bt_backtrue:
                // 点击确认退出按钮，返回到登录界面
                Intent intent_query = new Intent();
                intent_query.setClass(BackActivity.this, LoginActivity.class);
                startActivity(intent_query);
                break;
        }
    }
}
