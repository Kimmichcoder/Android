package com.example.GameMine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myexamproject.R;
import com.example.GameMine.MainBin.Student;
import com.example.GameMine.Utils.StudentDbHelper;

import java.util.LinkedList;

/**
 * 修改个人信息Activity类
 */
public class UpdateIFActivity extends AppCompatActivity {

    EditText etStuName, etMajor, etPhone, etQq, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ifactivity);

        // 返回按钮点击事件
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 利用bundle传递学号
        final TextView tvStuNumber = findViewById(R.id.tv_stu_number);
        tvStuNumber.setText(getIntent().getStringExtra("stu_number2"));

        // 初始化EditText组件
        etStuName = findViewById(R.id.et_stu_name);
        etMajor = findViewById(R.id.et_stu_major);
        etPhone = findViewById(R.id.et_stu_phone);
        etQq = findViewById(R.id.et_stu_qq);
        etAddress = findViewById(R.id.et_stu_address);

        // 创建数据库访问对象
        StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(), StudentDbHelper.DB_NAME, null, 1);
        LinkedList<Student> students = dbHelper.readStudents(tvStuNumber.getText().toString());

        // 如果查找到的学生信息不为空，则显示在对应的EditText中
        if (students != null) {
            for (Student student : students) {
                etStuName.setText(student.getStuName());
                etMajor.setText(student.getStuMajor());
                etPhone.setText(student.getStuPhone());
                etQq.setText(student.getStuQq());
                etAddress.setText(student.getStuAddress());
            }
        }

        // 保存按钮点击事件
        Button btnSaveInfo = findViewById(R.id.btn_save_info);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先判断输入不为空
                if (checkInput()) {
                    // 创建数据库访问对象
                    StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(), StudentDbHelper.DB_NAME, null, 1);

                    // 创建学生对象，将修改后的信息存入
                    Student student = new Student();
                    student.setStuNumber(tvStuNumber.getText().toString());
                    student.setStuName(etStuName.getText().toString());
                    student.setStuMajor(etMajor.getText().toString());
                    student.setStuPhone(etPhone.getText().toString());
                    student.setStuQq(etQq.getText().toString());
                    student.setStuAddress(etAddress.getText().toString());

                    // 保存学生信息
                    dbHelper.saveStudent(student);
                    Toast.makeText(getApplicationContext(), "用户信息保存成功!", Toast.LENGTH_SHORT).show();

                    // 销毁当前界面
                    finish();
                }
            }
        });
    }



    // 检查输入是否为空
    public boolean checkInput() {
        String stuName = etStuName.getText().toString();
        String stuMajor = etMajor.getText().toString();
        String stuPhone = etPhone.getText().toString();
        String stuQq = etQq.getText().toString();
        String stuAddress = etAddress.getText().toString();

        if (stuName.trim().equals("")) {
            Toast.makeText(this, "姓名不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stuMajor.trim().equals("")) {
            Toast.makeText(this, "专业不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stuPhone.trim().equals("")) {
            Toast.makeText(this, "联系方式不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stuQq.trim().equals("")) {
            Toast.makeText(this, "QQ号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stuAddress.trim().equals("")) {
            Toast.makeText(this, "地址不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
