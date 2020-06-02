package com.example.xch.scanzbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminActivity extends BaseActivity implements View.OnClickListener{

    private Button BtnAdd, BtnBackToSelectActivity, BtnBarcodeScan, BtnDelete;

    private EditText barcodeAdmin;

    private EditText nameAdmin;

    private EditText priceAdmin;

    private EditText countAdmin;


    private static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码
    //private static final String WEB_URL = "http://172.29.36.46:8081/barcode/";
    //private static final String WEB_URL = "http://192.168.253.1:8081/barcode/";
    private static final String WEB_URL = "http://111.229.77.140//barcode/";

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        barcodeAdmin = findViewById(R.id.barcode_admin);
        nameAdmin = findViewById(R.id.name_admin);
        priceAdmin = findViewById(R.id.price_admin);
        countAdmin = findViewById(R.id.count_admin);

        BtnBackToSelectActivity = findViewById(R.id.btn_backToSelectActivity);
        BtnBackToSelectActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, SelectActivity.class));
            }
        });

        BtnBarcodeScan = findViewById(R.id.btn_barcodeScan);
        BtnBarcodeScan.setOnClickListener(this);

        BtnAdd = findViewById(R.id.btn_add);
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("系统提示")
                        .setMessage("确定修改吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(barcodeAdmin.getText().toString().equals("")||nameAdmin.getText().toString().equals("")||priceAdmin.getText().toString().equals("")||countAdmin.getText().toString().equals("")){
                                            Check.checkvoid(AdminActivity.this,"内容不完整，请重新输入！");

                                        }
                                        else {
                                            String urlAdd = WEB_URL + "add.php?barcode=" + barcodeAdmin.getText().toString()
                                                    + "&name=" + nameAdmin.getText().toString()
                                                    + "&price=" + priceAdmin.getText().toString()
                                                    + "&count=" + countAdmin.getText().toString();
                                            final String URL = urlAdd;
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
                                                        msg.what = 0x2;
                                                        msg.obj = response.toString();
                                                        handler.sendMessage(msg);


                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }).start();
                                        }
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
        });


        BtnDelete = findViewById(R.id.btn_delete);
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("系统提示")
                        .setMessage("确定删除吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(barcodeAdmin.getText().toString().equals("")||nameAdmin.getText().toString().equals("")||priceAdmin.getText().toString().equals("")||countAdmin.getText().toString().equals("")){
                                            Check.checkvoid(AdminActivity.this,"内容不完整，请重新输入！");

                                        }
                                        else {
                                            String urlAdd = WEB_URL + "delete.php?barcode=" + barcodeAdmin.getText().toString()
                                                    + "&name=" + nameAdmin.getText().toString()
                                                    + "&price=" + priceAdmin.getText().toString()
                                                    + "&count=" + countAdmin.getText().toString();
                                            final String URL = urlAdd;
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
                                                        msg.what = 0x3;
                                                        msg.obj = response.toString();
                                                        handler.sendMessage(msg);


                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }).start();
                                        }
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
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_barcodeScan:
                //动态权限申请
                if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
        Intent intent = new Intent(AdminActivity.this, CaptureActivity.class);
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
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1:{
                    // 在这里可以进行UI操作

                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());

                        if(jsonObject.getString("success").equals("1")) {
                            barcodeAdmin.setText(jsonObject.getString("barcode"));
                            nameAdmin.setText(jsonObject.getString("name"));
                            priceAdmin.setText(jsonObject.getString("price"));
                            countAdmin.setText(jsonObject.getString("count"));
                        }
                        else {
                            barcodeAdmin.setText(jsonObject.getString("barcode_src"));
                            nameAdmin.setText("");
                            priceAdmin.setText("");
                            countAdmin.setText("");
                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }



                    break;
                }
                case 0x2:{

                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());

                        if(jsonObject.getString("success").equals("1")) {
                            new AlertDialog.Builder(AdminActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("修改成功！")
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
                        else {
                            new AlertDialog.Builder(AdminActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("修改失败！")
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

                case 0x3:{

                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());

                        if(jsonObject.getString("success").equals("1")) {
                            new AlertDialog.Builder(AdminActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("删除成功！")
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
                        else if(jsonObject.getString("success").equals("0")){
                            new AlertDialog.Builder(AdminActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("删除失败！")
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
                        else {
                            new AlertDialog.Builder(AdminActivity.this)
                                    .setTitle("系统提示")
                                    .setMessage("不存在该商品，请重新输入！")
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
                                    msg.what = 0x1;
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
}
