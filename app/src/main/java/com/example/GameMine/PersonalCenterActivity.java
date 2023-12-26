package com.example.GameMine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myexamproject.R;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener {

    // 显示学号的 TextView
    TextView TvStuNumber;
    // 退出登录按钮
    private Button btn_logout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        // 初始化退出登录按钮
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        // 取出登录时的登录名
        TvStuNumber = findViewById(R.id.tv_student_number);
        String StuNumber = this.getIntent().getStringExtra("username1");
        TvStuNumber.setText(StuNumber);

        // 返回主界面按钮
        Button btnBack = findViewById(R.id.bt_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 点击个人信息按钮
        Button btnUserInfo = findViewById(R.id.btn_user_info);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyIFActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number1", TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                // 点击退出登录按钮，跳转到退出登录界面
                Intent intent_insert = new Intent();
                intent_insert.setClass(PersonalCenterActivity.this, BackActivity.class);
                startActivity(intent_insert);
                break;
        }
    }
}
