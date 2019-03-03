package com.okusr.music.musicbox3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author lixiang
 * @date :2017/10/23
 * @Description:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyReclerViewAdapter.ISelect {
    private RecyclerView rc_list;
    private MyReclerViewAdapter myReclerViewAdapter;
    private TextView start_tv, stop_tv;
    private MediaPlayer mePlayer;
    private final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc_list = (RecyclerView) findViewById(R.id.rc_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_list.setLayoutManager(layoutManager);
        initView();
        quanxian();
    }

    private void quanxian() {
        //请求权限
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            MusicLoader.instance(getContentResolver());
            myReclerViewAdapter = new MyReclerViewAdapter(this, MusicLoader.musicList, this);
            rc_list.setAdapter(myReclerViewAdapter);
            //初始化mediaplayer对象,这里播放的是raw文件中的mp3资源
            mePlayer = MediaPlayer.create(MainActivity.this, Uri.parse(MusicLoader.musicList.get(0).getUrl()));

            Log.d("MainActivity", "-------musicList---" + MusicLoader.musicList.get(0).getTitle());
            Log.d("MainActivity", "-------musicList---" + MusicLoader.musicList.get(0).getArtist());
            Log.d("MainActivity", "-------musicList---" + MusicLoader.musicList.get(0).getAlbum());
            Log.d("MainActivity", "-------musicList---" + MusicLoader.musicList.get(0).getDuration());
            Log.d("MainActivity", "-------musicList---" + MusicLoader.musicList.get(0).getUrl());
            //设置循环播放:
            mePlayer.setLooping(true);
        }
    }

    private void initView() {
        start_tv = (TextView) findViewById(R.id.start_tv);
        stop_tv = (TextView) findViewById(R.id.stop_tv);
        start_tv.setOnClickListener(this);
        stop_tv.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    quanxian();
                } else {
                    Toast.makeText(this, "请打开存储权限", Toast.LENGTH_SHORT).show();
                    //用户拒绝授权
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_tv:
                mePlayer.start();
                Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
                //播放
                break;
            case R.id.stop_tv:
                Toast.makeText(this, "停止", Toast.LENGTH_SHORT).show();
                //停止
                mePlayer.pause();
                break;
            default:
        }
    }

    @Override
    public void selectItem(String s, int possion) {
        Log.d("MainActivity", "-------播放----" + s);
        mePlayer.stop();
        mePlayer.reset();

        mePlayer = MediaPlayer.create(MainActivity.this, Uri.parse(s));
        Toast.makeText(this, "正在播放" + MusicLoader.musicList.get(possion).getAlbum(), Toast.LENGTH_SHORT).show();
        //设置循环播放:
        mePlayer.setLooping(true);
        mePlayer.start();
    }
}
