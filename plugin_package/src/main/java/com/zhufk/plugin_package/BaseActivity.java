package com.zhufk.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.zhufk.stander.ActivityInterface;


/**
 * @ClassName BaseActivity
 * @Description 插件activity基类
 * @Author zhufk
 * @Date 2019/11/28 9:33
 * @Version 1.0
 */
public class BaseActivity extends Activity implements ActivityInterface {

    public Activity appActivity;

    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    //重写findViewById
    public void setContentView(int layoutResID) {
        appActivity.setContentView(layoutResID);
    }

    //重写findViewById
    public View findViewById(int id){
        return appActivity.findViewById(id);
    }

    //重写startActivity
    public void startActivity(Intent intent){
        Intent newIntent = new Intent();
        newIntent.putExtra("className",intent.getComponent().getClassName());
        appActivity.startActivity(newIntent);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intent = new Intent();
        intent.putExtra("className",intent.getComponent().getClassName());
        return appActivity.startService(intent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return appActivity.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        appActivity.sendBroadcast(intent);
    }
}
