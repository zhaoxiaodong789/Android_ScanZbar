package com.example.xch.scanzbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Check {
    private static boolean flag = true;
    public static boolean check(Context context, String message){

        new AlertDialog.Builder(context)
                .setTitle("系统提示")
                .setMessage(message)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                flag = true;
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                })
                .show();
        return flag;
    }
    public static void checkvoid(Context context, String message){

        new AlertDialog.Builder(context)
                .setTitle("系统提示")
                .setMessage(message)
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
