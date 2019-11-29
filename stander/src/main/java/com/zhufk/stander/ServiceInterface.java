package com.zhufk.stander;

import android.app.Service;
import android.content.Intent;

/**
 * @ClassName ServiceInterface
 * @Description 服务标准
 * @Author zhufk
 * @Date 2019/11/29 8:30
 * @Version 1.0
 */
public interface ServiceInterface {
    /**
     *  把宿主环境给插件
     * @param appService
     */
    void insertAppContext(Service appService);

    void onCreate();

    int onStartCommand(Intent intent, int flags, int startId);

    void onDestroy();
}
