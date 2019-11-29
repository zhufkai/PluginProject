package com.zhufk.pluginproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import com.zhufk.stander.ActivityInterface;

import java.lang.reflect.Constructor;

/**
 * 占位Activity
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getDexClassLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");

        try {
            Class<?> pluginActivityClass = getClassLoader().loadClass(className);
            Constructor<?> constructor = pluginActivityClass.getConstructor(new Class[]{});
            Object pluginActivity = constructor.newInstance(new Object() {
            });
            ActivityInterface activityInterface = (ActivityInterface) pluginActivity;
            activityInterface.insertAppContext(this);
            Bundle bundle = new Bundle();
            bundle.putString("appName", "宿主传递");
            activityInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent pluginIntent = new Intent(this, ProxyActivity.class);
        pluginIntent.putExtra("className", className);
        //调用系统环境的startActivity
        super.startActivity(intent);
    }

    @Override
    public ComponentName startService(Intent service) {
        String className = service.getStringExtra("className");
        Intent pluginIntent = new Intent(this, ProxyService.class);
        pluginIntent.putExtra("className", className);
        return super.startService(pluginIntent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        String className = receiver.getClass().getName();
        return super.registerReceiver(new ProxyReceiver(className), filter);
    }
}
