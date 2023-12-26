package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.GameMine.MainBin.Game;
import com.example.GameMine.Utils.MySQLiteOpenHelper;
import com.example.myexamproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AdminUpdateGamesActivity extends AppCompatActivity implements View.OnClickListener {
    // 组件定义
    //   private EditText etGameid;
    AutoCompleteTextView autoCompleteTextViewyouxi;
    String[] suggestions = {"钢铁雄心4", "王者荣耀", "英雄联盟","元梦之星","原神","DOTA2","varolant"};
    private EditText etGametime;
    private EditText etGamenote;


    private  String gameid;


    private String etUserId;
    private Button btnSearch;
    private Button btnEdit;

    private  String name;

    private MySQLiteOpenHelper dbHelper;
    private AutoCompleteTextView autoCompleteTextView,  autoCompleteTextViewuser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_activity_update_games);

        dbHelper = new MySQLiteOpenHelper(this);

        autoCompleteTextViewuser=findViewById(R.id.autoCompleteTextViewuser);

        Log.d("herehe","pp");



        etUserId="";
        String[] useridList=getuserlist();

        Log.d("herehe1","pp");

        if(useridList==null)
        {
            Log.d("kong","pppppp");
        }
        else
        {
            Log.d("kong", String.valueOf(useridList.length));
        }


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, useridList);
        autoCompleteTextViewuser.setAdapter(adapter1);

        // 在此处添加点击事件监听，使下拉框在点击文本框时弹出
        autoCompleteTextViewuser.setOnClickListener(view -> {
            autoCompleteTextViewuser.showDropDown();
        });

        // 添加下拉框选项点击监听器
        autoCompleteTextViewuser.setOnItemClickListener((adapterView, view, position, id) -> {
            // 获取选中的内容
            String selectedText = (String) adapterView.getItemAtPosition(position);

            // 设置文本框内容
            autoCompleteTextViewuser.setText(selectedText);

            // 手动触发 TextChanged 事件
            autoCompleteTextViewuser.post(() -> autoCompleteTextViewuser.setSelection(autoCompleteTextViewuser.getText().length()));

            etUserId = selectedText.trim();

            autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
            // 获取游戏 ID 列表
            String[] gameIdList = getGameIdList(etUserId);


            Log.d("qinbiaofff", String.valueOf(gameIdList.length));



            // 确保 gameIdList 不为 null
            if (gameIdList == null) {
                gameIdList = new String[]{"llll","pppp"}; // 初始化为空数组
            }

            // 创建适配器，并设置给 AutoCompleteTextView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gameIdList);
            autoCompleteTextView.setAdapter(adapter);

            // 在此处添加点击事件监听，使下拉框在点击文本框时弹出
            autoCompleteTextView.setOnClickListener(view1 -> {
                autoCompleteTextView.showDropDown();
            });

            // 添加下拉框选项点击监听器
            autoCompleteTextView.setOnItemClickListener((adapterView1, view1, position1, id1) -> {
                // 获取选中的内容
                gameid = (String) adapterView1.getItemAtPosition(position1);
            });

        });



        // 初始化界面
        initView();
    }

    // 查询所有的 gameid
    private String[]getGameIdList(String userid) {
        // 在这里调用你的查询函数，获取游戏 ID 列表
        // 使用 MySQLiteOpenHelper 的实例调用你的查询方法
        String[] gameIdList = dbHelper.getGamesList(userid);

        // 构建游戏 ID 列表

        return gameIdList;
    }

    private  String[]getuserlist()
    {
        String[] userIdList = dbHelper.getAllUserIds();


        return userIdList;
    }
    public void showDatePickerDialog(View view) {
        // 获取当前日期
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String ans = "ppp";
        // 创建日期选择器对话框
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 用户选择日期后的操作
                        // 这里可以更新 EditText 中显示的日期
                        etGametime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        // 显示日期选择器对话框
        datePickerDialog.show();

        // return ans;

    }
    private void initView() {
        // 初始化组件

        //  etGameid = findViewById(R.id.et_gameid);
        btnSearch = findViewById(R.id.btn_search);

         autoCompleteTextViewyouxi = findViewById(R.id.autoCompleteTextViewyouxi);

        // 定义一组数据，用于提供给 AutoCompleteTextView 的建议


        // 创建适配器，并设置给 AutoCompleteTextView
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextViewyouxi.setAdapter(adapter2);

        // 在此处添加点击事件监听，使下拉框在点击文本框时弹出
        autoCompleteTextViewyouxi.setOnClickListener(view2 -> {
            autoCompleteTextViewyouxi.showDropDown();
        });


        // 添加下拉框选项点击监听器
        autoCompleteTextViewyouxi.setOnItemClickListener((adapterView2, view2, position2, id2) -> {
            // 获取选中的内容
            name = (String) adapterView2.getItemAtPosition(position2);
        });


        etGametime = findViewById(R.id.et_gametime);
        etGamenote = findViewById(R.id.et_gamenote);
        btnEdit = findViewById(R.id.btn_edit);

        // 设置按钮点击事件
        btnSearch.setOnClickListener((View.OnClickListener) this);
        btnEdit.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        // 通过switch方法处理按钮点击事件
        switch (v.getId()) {
            case R.id.btn_search:
                searchOrder();
                break;
            case R.id.btn_edit:
                updateOrder();
                break;
        }
    }

    private void searchOrder() {

        Log.d("anniu","lll");
        if(gameid==null)
        {
            Toast.makeText(this, "游戏id不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 创建数据库访问对象
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();

        Log.d("anniu",gameid);
        // 根据游戏id查询游戏信息
        Game game = GameMySql.getGames(gameid);

        // 将查询到的游戏信息显示在对应的EditText中
      //  etGamename.setText(game.GameName);

        List<String> originalSuggestions = new ArrayList<>(Arrays.asList(suggestions));

// 设置默认值
        autoCompleteTextViewyouxi.setText(game.GameName);

// 如果需要，你还可以将默认值添加到原始选项列表
     //   originalSuggestions.add(game.GameName);

// 创建适配器，并设置给 AutoCompleteTextView
        ArrayAdapter<String> updatedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, originalSuggestions);
        autoCompleteTextViewyouxi.setAdapter(updatedAdapter);



        etGametime.setText(game.GameTime);
        etGamenote.setText(game.GameNote);

        // 关闭数据库
        GameMySql.close();
    }

    private void updateOrder() {
        // 创建游戏对象，将修改后的信息存入
        Game game = new Game();
        game.UserId=etUserId;
        game.GameId = gameid;
        if(gameid==null)
        {
            Toast.makeText(this, "游戏id不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        game.GameName = name;
        game.GameTime = etGametime.getText().toString().trim();
        game.GameNote = etGamenote.getText().toString().trim();

        // 创建数据库访问对象
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();

        // 更新游戏信息
        long result = GameMySql.updateGames(game);

        // 根据更新结果显示相应的提示信息
        if (result > 0) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }

        // 关闭数据库
        GameMySql.close();

        startActivity(new Intent(AdminUpdateGamesActivity.this, AdminMainActivity.class));
    }
}
