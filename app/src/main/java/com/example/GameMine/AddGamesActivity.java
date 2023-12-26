package com.example.GameMine;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.GameMine.MainBin.Game;
import com.example.GameMine.Utils.IdGenerator;
import com.example.GameMine.Utils.LocationHelper;
import com.example.GameMine.Utils.MySQLiteOpenHelper;
import com.example.myexamproject.R;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static java.lang.Math.log;

/**
 * 添加游戏的活动
 */
public class AddGamesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;
    // 组件定义
    private EditText et_Gameid;      // 游戏ID输入框
    private EditText et_Gamename;    // 游戏名称输入框
    private EditText et_Gametime;    // 约玩时间输入框
    private EditText et_Gamenote;    // 备注输入框
    private String name;
    private Button btnAdd;          // 添加按钮

    private String UserId;

    int cnt = 1;

    @Override
    protected void onPause() {
        super.onPause();
        // 在页面不可见时移除位置更新监听器，释放资源
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    String countryAndCity = "无相关地址，请打开定位权限！";
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        // 在页面可见时重新请求位置更新监听器


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 如果没有权限，请求定位权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
               // getLocation();

            } else {
                // 如果已有权限，开始获取位置信息

            }

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            String username = preferences.getString("username", "");

            UserId=username;
            Log.d("debug1", username);
            // 其他逻辑...
        }


        setContentView(R.layout.activity_add_games);

        // 定义一组数据，用于提供给 AutoCompleteTextView 的建议

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // 定义一组数据，用于提供给 AutoCompleteTextView 的建议
        String[] suggestions = {"钢铁雄心4", "王者荣耀", "英雄联盟","元梦之星","原神","DOTA2","varolant"};

        // 创建适配器，并设置给 AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);

        // 在此处添加点击事件监听，使下拉框在点击文本框时弹出
        autoCompleteTextView.setOnClickListener(view -> {
            autoCompleteTextView.showDropDown();
        });


        // 添加下拉框选项点击监听器
        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, id) -> {
            // 获取选中的内容
            name = (String) adapterView.getItemAtPosition(position);
        });

        et_Gameid = findViewById(R.id.et_gameid);

        String temp = "kkkkk";

        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this);

        do {
            // 生成ID
            temp = IdGenerator.generateUniqueId();

        } while (!IdGenerator.isUniqueId(temp, dbHelper));

        et_Gameid.setText(temp);
        et_Gametime = findViewById(R.id.et_gametime);


        et_Gamenote = findViewById(R.id.et_gamenote);

        et_Gamenote.setText(countryAndCity);


        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 获取最后一次的已知位置
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastKnownLocation != null) {
            // 处理位置信息
            handleLocation(lastKnownLocation);
        }

        // 设置位置更新监听器
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 处理位置信息
            handleLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    // 在 handleLocation 方法中增加空指针检查
    private void handleLocation(Location location) {
        if (location != null) {
            // 获取经纬度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // 使用Geocoder获取城市和省份信息
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    String city = addresses.get(0).getLocality();
                    String province = addresses.get(0).getAdminArea();

                    // 处理城市和省份信息
                    handleCityAndProvince(city, province);
                } else {
                    // 处理没有获取到地址的情况
                    handleNoAddress();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 处理 Geocoder 获取地址时的异常
                handleGeocoderException();
            }
        } else {
            // 处理没有获取到位置信息的情况
            handleNoLocation();
        }
    }

    // 新增处理没有获取到地址的情况
    private void handleNoAddress() {
        Toast.makeText(this, "Unable to get address", Toast.LENGTH_SHORT).show();
    }

    // 新增处理 Geocoder 获取地址时的异常情况
    private void handleGeocoderException() {
        Toast.makeText(this, "Geocoder exception", Toast.LENGTH_SHORT).show();
    }

    // 新增处理没有获取到位置信息的情况
    private void handleNoLocation() {
        Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
    }

    private void handleCityAndProvince(String city, String province) {
        // 在这里处理获取到的城市和省份信息，例如显示在UI上或者进行其他操作
        Toast.makeText(this, province + "/" + city, Toast.LENGTH_SHORT).show();
        et_Gamenote.setText(province.toString() + "/" + city.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果用户同意了定位权限，开始获取位置信息
                try {
                    getLocation();
                } catch (Exception e) {
                    Toast.makeText(this,  "山东省" + "青岛市", Toast.LENGTH_SHORT).show();
                }

            } else {
                // 如果用户拒绝了定位权限，可以显示一条提示信息或者采取其他措施
               // getLocation();
                Toast.makeText(this, "无法获取定位信息,请尝试打开定位权限！", Toast.LENGTH_SHORT).show();
            }
        }
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
                        et_Gametime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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


    private void processLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // 在这里处理经纬度信息，例如显示在UI上或者进行其他操作
        showToast("Latitude: " + latitude + "\nLongitude: " + longitude);
    }

    // 显示 Toast 的辅助方法
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在页面可见时重新请求位置更新监听器
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }

    @Override
    public void onClick(View v) {


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        v.clearFocus();


        String GameId = et_Gameid.getText().toString().trim();


        String GameName = name;

        //  showDatePickerDialog(v);

        String GameTime = et_Gametime.getText().toString().trim();

        String GameNote = et_Gamenote.getText().toString().trim();


        // 检验信息是否正确
        if (TextUtils.isEmpty(GameId)) {

            Toast.makeText(this, "请输入游戏id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(GameName)) {
            Toast.makeText(this, "请输入游戏名称", Toast.LENGTH_SHORT).show();
            return;
        }
     /*   if (TextUtils.isEmpty(GameTime)) {
            Toast.makeText(this, "请输入约玩时间", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (TextUtils.isEmpty(GameNote)) {
            Toast.makeText(this, "请输入备注", Toast.LENGTH_SHORT).show();
            return;
        }

        // 添加信息
        Game game = new Game();
        game.GameId = GameId;
        game.GameName = GameName;
        game.GameTime = GameTime;
        game.GameNote = GameNote;
        game.UserId=UserId;

        Log.d("debug2", UserId);

        // 创建数据库访问对象
        MySQLiteOpenHelper GameMySql = new MySQLiteOpenHelper(getApplicationContext());
        GameMySql.open();

        // 将游戏信息添加到数据库
        long result = GameMySql.addGames(game);


        // 根据添加结果显示提示信息
        if (result > 0) {
            Toast.makeText(this, "发布成功!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "发布失败!", Toast.LENGTH_SHORT).show();
        }

        // 关闭数据库连接
        GameMySql.close();
        finish();
    }
}
