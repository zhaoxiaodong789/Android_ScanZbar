package com.example.xch.scanzbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xch.scanzbar.zbar.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_scan, btn_back;
    private TextView barcode;

    private TextView name;

    private TextView price;

    private TextView count;


    private static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码
    //private static final String WEB_URL = "http://172.29.36.46:8081/barcode/";
    //private static final String WEB_URL = "http://192.168.253.1:8081/barcode/";
    private static final String WEB_URL = "http://111.229.77.140//barcode/";
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
        getSupportActionBar().hide();


        Log.d(TAG, "Hello World!");

        btn_scan = findViewById(R.id.btn_scan);
        btn_back = findViewById(R.id.btn_back);

        barcode = findViewById(R.id.barcode_admin);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        count = findViewById(R.id.count);


        btn_scan.setOnClickListener(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectActivity = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(toSelectActivity);
            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:{
                    // 在这里可以进行UI操作

                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());

                        if(jsonObject.getString("success").equals("1")) {
                            barcode.setText(jsonObject.getString("barcode"));
                            name.setText(jsonObject.getString("name"));
                            price.setText(jsonObject.getString("price")+"元");
                            count.setText(jsonObject.getString("count"));
                        }
                        else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("该商品不存在，请重新扫描！")
                                    .setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                        }



                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }



                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                //动态权限申请
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN:// 二维码
                // 扫描二维码回传
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        //获取扫描结果
                        Bundle bundle = data.getExtras();
                        String result = bundle.getString(CaptureActivity.EXTRA_STRING);

                        String url = WEB_URL + "saveUsername.php?barcode=" + result;

                        final String URL = url;

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpURLConnection connection;

                                try {
                                    connection = (HttpURLConnection) new URL(URL).openConnection();
                                    connection.setRequestMethod("GET");
                                    InputStream in = connection.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                                    StringBuilder response = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        response.append(line);
                                    }


                                    Message msg = new Message();
                                    msg.what = 0x123;
                                    msg.obj = response.toString();
                                    handler.sendMessage(msg);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toSelectActivity = new Intent(MainActivity.this, SelectActivity.class);
        startActivity(toSelectActivity);
    }
}
