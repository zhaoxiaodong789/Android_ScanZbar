package com.example.xch.scanzbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {

    private Button BtnLogin, BtnLoginBack;
    private EditText EdtAdmin, EdtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade().setDuration(2000));
            getWindow().setExitTransition(new Fade().setDuration(2000));
        }

        EdtAdmin = findViewById(R.id.edt_admin);
        EdtPassword = findViewById(R.id.edt_password);

        BtnLogin = findViewById(R.id.btn_login);
        BtnLoginBack = findViewById(R.id.btn_login_back);

        BtnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SelectActivity.class));
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdtAdmin.getText().toString().equals("admin")&&EdtPassword.getText().toString().equals("123456")){
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                }
                else {
                    Check.checkvoid(LoginActivity.this, "帐号或密码错误！");
                }
            }
        });

    }
}
