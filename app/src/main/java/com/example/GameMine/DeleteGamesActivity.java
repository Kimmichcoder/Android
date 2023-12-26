package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.GameMine.MainBin.Game;
import com.example.GameMine.Utils.MySQLiteOpenHelper;
import com.example.myexamproject.R;

/**
 * 删除游戏信息的活动
 */
public class DeleteGamesActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etGameid;    // 输入游戏id的文本框
    private Button btnSearch;     // 搜索按钮
    private EditText etGamename;  // 游戏名称文本框
    private EditText etGametime;  // 游戏时间文本框
    private EditText etGamenote;  // 备注文本框
    private Button btnDelete;     // 删除按钮
    private  EditText etUserId;
    private MySQLiteOpenHelper dbHelper;
    private AutoCompleteTextView autoCompleteTextView;

    private  String gameid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_games);


        etUserId=findViewById(R.id.et_userid);

        etUserId.setText("kkkk");

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            String username = preferences.getString("username", "");

            etUserId.setText(username);
            Log.d("debug1", username);

            // 其他逻辑...
        }

        Log.d("here1","kkkk");
        dbHelper = new MySQLiteOpenHelper(this);


        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        Toast.makeText(this,etUserId.getText().toString().trim() , Toast.LENGTH_SHORT).show();

        // 获取游戏 ID 列表
        String[] gameIdList = getGameIdList(etUserId.getText().toString().trim());


        // 确保 gameIdList 不为 null
        if (gameIdList == null) {
            gameIdList = new String[]{"llll","pppp"}; // 初始化为空数组
        }

        // 创建适配器，并设置给 AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gameIdList);
        autoCompleteTextView.setAdapter(adapter);

        // 在此处添加点击事件监听，使下拉框在点击文本框时弹出
        autoCompleteTextView.setOnClickListener(view -> {
            autoCompleteTextView.showDropDown();
        });

        // 添加下拉框选项点击监听器
        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, id) -> {
            // 获取选中的内容
            gameid = (String) adapterView.getItemAtPosition(position);
        });

        initView();



    }

    // 初始化界面
    private void initView() {

        btnSearch = (Button) findViewById(R.id.btn_search);
        etGamename = (EditText) findViewById(R.id.et_gamename);
        etGametime = (EditText) findViewById(R.id.et_gametime);
        etGamenote = (EditText) findViewById(R.id.et_gamenote);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }
    // 查询所有的 gameid
    private String[]getGameIdList(String userid) {
        // 在这里调用你的查询函数，获取游戏 ID 列表
        // 使用 MySQLiteOpenHelper 的实例调用你的查询方法
        String[] gameIdList = dbHelper.getGamesList(userid);

        // 构建游戏 ID 列表

        return gameIdList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                // 点击搜索按钮，查找游戏信息
                searchOrder();
                break;
            case R.id.btn_delete:
                // 点击删除按钮，删除游戏信息
                deleteOrder();
                break;
        }
    }

    // 查找游戏信息
    private void searchOrder() {
        String gameId = gameid;
        if(gameid==null)
        {
            Toast.makeText(this, "游戏id不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();
        Game game = GameMySql.getGames(gameId);
        etGamename.setText(game.GameName);
        etGametime.setText(game.GameTime);
        etGamenote.setText(game.GameNote);
        GameMySql.close();
    }

    // 删除游戏信息
    private void deleteOrder() {
        Game game = new Game();
        game.GameId = gameid;
        if(gameid==null)
        {
            Toast.makeText(this, "游戏id不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();
        int result = GameMySql.deletGames(game);
        if (result > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
        GameMySql.close();

        startActivity(new Intent(DeleteGamesActivity.this, MainActivity.class));
    }
}
