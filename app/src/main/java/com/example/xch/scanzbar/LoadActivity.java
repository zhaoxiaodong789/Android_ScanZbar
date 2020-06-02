package com.example.xch.scanzbar;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LoadActivity extends BaseActivity {
    private final int time = 3000;
    private boolean lag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
        getSupportActionBar().hide();
        //隐藏底部home，back键的导航栏
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility =View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
                    finish();

                    Intent intent = new Intent(LoadActivity.this , SelectActivity.class);
                    startActivity(intent);
                }
            }
        } , time);

        Button button = (Button) findViewById(R.id.exitLoad);
        //给按钮添加监听事件，当点击时，直接进入主页面
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LoadActivity.this , SelectActivity.class);
                startActivity(intent);
                lag = false;
            }
        });


    }
}
