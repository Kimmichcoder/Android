package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.GameMine.Utils.MD5Utils;
import com.example.myexamproject.R;


public class RegisterActivity extends AppCompatActivity {
    //声明所有按钮
    private EditText et_username,et_pwd,et_pwd_sure;
    private Button register;
    private String userName,passWord,passWord_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeAdminAccount();
        init();
    }

    private void initializeAdminAccount() {
        SharedPreferences preferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        boolean adminAccountExists = preferences.contains("Admin");

        if (!adminAccountExists) {
            // 如果 Admin 账号不存在，创建并保存到 SharedPreferences
            saveAdminAccount();
        }
    }

    private void saveAdminAccount() {
        String adminUsername = "Admin";
        String adminPassword = MD5Utils.md5("Admin");

        SharedPreferences preferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(adminUsername, adminPassword);
        editor.apply();
    }
    public void init(){
        //给按钮匹配id
        et_username = (EditText)findViewById(R.id.username);
        et_pwd = (EditText)findViewById(R.id.pwd);
        et_pwd_sure = (EditText)findViewById(R.id.pwd2);
        register = (Button)findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                //提示输入信息
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passWord)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passWord_sure)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passWord.equals(passWord_sure)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                    //判断用户名是否已被注册
                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!isValidUsername(userName))
                {
                    Toast.makeText(RegisterActivity.this, "请按照账号规定格式注册！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!isValidPassword(passWord))
                {
                    Toast.makeText(RegisterActivity.this, "请按照密码规定格式注册！", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把保存账号密码
                    saveRegisterInfo(userName, passWord);
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    //跳转到登录界面中
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
            }
        });
    }
    //获得已输入信息
    private void getEditString(){
        userName = et_username.getText().toString().trim();
        passWord = et_pwd.getText().toString().trim();
        passWord_sure = et_pwd_sure.getText().toString().trim();
    }
    //判断输入的用户名是否已经存在
    private boolean isExistUserName(String userName){
        boolean has_userName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        //判断密码是否为空，不空则注册成功
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    //将用户名和密码保存到sp中
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName, md5Psw);
        editor.commit();
    }
    //跳转回登录界面
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }


    private boolean isValidUsername(String username) {
        // 首字母必须为英文字母，不小于八位，只包含英文字母和数字
        return username.length() >= 8 && Character.isLetter(username.charAt(0)) && username.matches("[a-zA-Z0-9]+");
    }

    // 检查密码格式是否合法
    private boolean isValidPassword(String password) {
        // 不小于八位，必须含有大写字母，小写字母，和数字
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*");
    }

}