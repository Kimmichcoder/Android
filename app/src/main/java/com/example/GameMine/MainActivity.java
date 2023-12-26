package com.example.GameMine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.myexamproject.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * 主界面的活动
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_updatee;
    private Button bt_deletee;
    private Button bt_read;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayerView playerView = findViewById(R.id.playerView);


/*


        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        player.setMediaItem(MediaItem.fromUri("android.resource://" + getPackageName() + "/" + R.raw.qbjxql));
        player.prepare();
        player.play();
*/
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        Uri rawResourceUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.qbjxql);
        MediaItem mediaItem = MediaItem.fromUri(rawResourceUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    // 当播放完成时，将播放位置设置到开始并重新播放
                    player.seekTo(0);
                    player.play();
                }
            }
        });

        playerView.setPlayer(player);


        // 获取个人中心按钮
        ImageButton IbPersonalCenter = findViewById(R.id.bt_personal);
        // 跳转到个人中心界面
        IbPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
               // hideKeybaord(v);
                startActivity(intent);
            }
        });

        // 获取添加游戏按钮
        ImageButton IbAdd = findViewById(R.id.bt_add);

        IbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGamesActivity.class);
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
                intent_query.setClass(MainActivity.this, UpdateGamesActivity.class);
                startActivity(intent_query);
                break;

            case R.id.bt_deletee:
                // 跳转到删除游戏界面
                Intent intent_update = new Intent();
                intent_update.setClass(MainActivity.this, DeleteGamesActivity.class);
                startActivity(intent_update);
                break;

            case R.id.bt_read:
                // 跳转到查询游戏界面
                Intent intent_delete = new Intent();
                intent_delete.setClass(MainActivity.this, QueryGamesActivity.class);
                startActivity(intent_delete);
                break;
        }
    }
}
