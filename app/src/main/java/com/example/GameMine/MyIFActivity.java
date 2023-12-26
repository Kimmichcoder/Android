package com.example.GameMine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myexamproject.R;
import com.example.GameMine.MainBin.Student;
import com.example.GameMine.Utils.StudentDbHelper;

import java.util.LinkedList;

/**
 * 我的个人信息活动类
 */
public class MyIFActivity extends AppCompatActivity {

    // 显示学号的 TextView
    TextView tvStuName, tvStuMajor, tvStuPhone, tvStuQq, tvStuAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ifactivity);

        // 返回按钮
        Button btnBack = findViewById(R.id.btn_back);
        // 返回点击事件，销毁当前界面
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 从 Bundle 中获取用户账号/学号
        final TextView tvUserNumber = findViewById(R.id.tv_stu_number);

        tvUserNumber.setText("kk");


        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            String username = preferences.getString("username", "");
            tvUserNumber.setText(username);
            Log.d("debug1", username);
            // 其他逻辑...
        }


        // 显示用户信息的 TextView
        tvStuName = findViewById(R.id.tv_stu_name);
        tvStuMajor = findViewById(R.id.tv_stu_major);
        tvStuPhone = findViewById(R.id.tv_stu_phone);
        tvStuQq = findViewById(R.id.tv_stu_qq);
        tvStuAddress = findViewById(R.id.tv_stu_address);

        // 创建数据库操作对象
        StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(), StudentDbHelper.DB_NAME, null, 1);

        // 从数据库中读取用户信息
        LinkedList<Student> students = dbHelper.readStudents(tvUserNumber.getText().toString());

        if (students != null) {
            for (Student student : students) {
                // 设置用户信息到对应的 TextView 中
                tvStuName.setText(student.getStuName());
                tvStuMajor.setText(student.getStuMajor());
                tvStuPhone.setText(student.getStuPhone());
                tvStuQq.setText(student.getStuQq());
                tvStuAddress.setText(student.getStuAddress());
            }
        } else {
            // 如果用户信息为空，显示默认文本
            tvStuName.setText("暂未填写");
            tvStuMajor.setText("暂未填写");
            tvStuPhone.setText("暂未填写");
            tvStuQq.setText("暂未填写");
            tvStuAddress.setText("暂未填写");
        }

        // 修改用户信息按钮
        Button btnModifyInfo = findViewById(R.id.btn_modify_info);
        // 跳转到修改用户信息界面
        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateIFActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number2", tvUserNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
