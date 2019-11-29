package com.zhufk.pluginproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zhufk.stander.ServiceInterface;

/**
 * @ClassName ProxyService
 * @Description Service占位类
 * @Author zhufk
 * @Date 2019/11/29 9:00
 * @Version 1.0
 */
public class ProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");
        try {
            Class pluginServiceClass = PluginManager.getInstance(this).getDexClassLoader().loadClass(className);
            Object pluginService = pluginServiceClass.newInstance();
            ServiceInterface serviceInterface = (ServiceInterface) pluginService;
            serviceInterface.insertAppContext(this);
            serviceInterface.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
