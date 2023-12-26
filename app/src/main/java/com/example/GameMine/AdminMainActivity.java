package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myexamproject.R;

/**
 * 主界面的活动
 */
public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_updatee;
    private Button bt_deletee;
    private Button bt_read;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmain);


        // 获取个人中心按钮
        ImageButton IbPersonalCenter = findViewById(R.id.bt_personal);
        // 跳转到个人中心界面
        IbPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, PersonalCenterActivity.class);
                // hideKeybaord(v);
                startActivity(intent);
            }
        });



        // 获取更新游戏按钮
        bt_updatee = findViewById(R.id.bt_updatee);
        bt_updatee.setOnClickListener(this);

        // 获取删除游戏按钮
        bt_deletee = findViewById(R.id.bt_deletee);
        bt_deletee.setOnClickListener(this);

        // 获取查询游戏按钮
        bt_read = findViewById(R.id.bt_read);
        bt_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 通过switch方法跳转到相应界面
        switch (v.getId()) {
            case R.id.bt_updatee:
                // 跳转到更新游戏界面
                Intent intent_query = new Intent();
                intent_query.setClass(AdminMainActivity.this, AdminUpdateGamesActivity.class);
                startActivity(intent_query);
                break;

            case R.id.bt_deletee:
                // 跳转到删除游戏界面
                Intent intent_update = new Intent();
                intent_update.setClass(AdminMainActivity.this, AdminnDeleteGamesActivity.class);
                startActivity(intent_update);
                break;

            case R.id.bt_read:
                // 跳转到查询游戏界面
                Intent intent_delete = new Intent();
                intent_delete.setClass(AdminMainActivity.this, QueryGamesActivity.class);
                startActivity(intent_delete);
                break;
        }
    }
}
