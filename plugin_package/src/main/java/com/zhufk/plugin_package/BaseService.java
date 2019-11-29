package com.zhufk.plugin_package;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zhufk.stander.ServiceInterface;


/**
 * @ClassName BaseService
 * @Description TODO
 * @Author zhufk
 * @Date 2019/11/29 8:31
 * @Version 1.0
 */
public class BaseService extends Service implements ServiceInterface {
    public Service appService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void insertAppContext(Service appService) {
        this.appService = appService;
    }

    @Override
    public void onCreate() {
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {
    }
}
