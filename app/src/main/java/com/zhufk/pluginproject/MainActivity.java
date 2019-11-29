package com.zhufk.pluginproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //加载插件
    public void loadPlugin(View view) {
        PluginManager.getInstance(this).loadPlugin();
    }

    //启动插件
    public void startPlugin(View view) {

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = packageInfo.activities[0];

        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", activityInfo.name);
        startActivity(intent);
    }

    public void startStaticReceiver(View view) {
        PluginManager.getInstance(this).parserApkAction();
    }

    public void sendReceiver(View view) {
        Intent intent = new Intent();
        intent.setAction("plugin.start_receiver");
        sendBroadcast(intent);
    }
}
