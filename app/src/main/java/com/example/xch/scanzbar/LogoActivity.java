package com.example.xch.scanzbar;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class LogoActivity extends BaseActivity {
    private final int time = 5000;
    private boolean lag = true;
    private VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
        getSupportActionBar().hide();
        //隐藏底部home，back键的导航栏
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        initView();
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
                    finish();

                    Intent intent = new Intent(LogoActivity.this , LoadActivity.class);
                    startActivity(intent);
                }
            }
        } , time);*/

    }

    private void initView() {
        videoview = (VideoView) findViewById(R.id.videoView);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.logo));
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                                                    @Override
                                                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                                                            //视频开始播放就去掉原来的白色遮罩（初始化background颜色），改成透明的
                                                            videoview.setBackgroundColor(Color.TRANSPARENT);
                                                        return true;
                                                    }
                                                });
                                            }
                                        });

        //播放
        videoview.start();

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                SystemClock.sleep(1000);
                videoview.setBackgroundColor(Color.WHITE);
                //videoview.setVisibility(View.INVISIBLE);
                finish();

                Intent intent = new Intent(LogoActivity.this , LoadActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
