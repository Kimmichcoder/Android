package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myexamproject.R;
import com.example.GameMine.Utils.MySQLiteOpenHelper;

import java.util.List;
import java.util.Map;

public class QueryGamesActivity extends AppCompatActivity {
    // 定义组件
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_games);
        // 设置标题
        setTitle("查看记录");
        // 初始化界面
        initView();
    }

    private void initView() {
        // 创建数据库操作对象
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();

        // 获取所有游戏记录数据
        List<Map<String, Object>> mOrderData = GameMySql.getAllGames();

        // 获取ListView组件
        listView = (ListView) findViewById(R.id.lst_orders);

        // 定义ListView中每个Item中显示的数据
        String[] from = {"gameid", "gamename", "gametime", "gamenote"};
        int[] to = {R.id.tv_lst_gameid, R.id.tv_lst_gamename, R.id.tv_lst_gametime, R.id.tv_lst_gamenote};

        // 创建SimpleAdapter，将数据与布局绑定
        SimpleAdapter listItemAdapter = new SimpleAdapter(QueryGamesActivity.this, mOrderData, R.layout.item_list, from, to);

        // 将Adapter设置给ListView
        listView.setAdapter(listItemAdapter);

        // 关闭数据库
        GameMySql.close();
    }
}
