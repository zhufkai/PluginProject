package com.zhufk.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @ClassName StaticReceiver
 * @Description TODO
 * @Author zhufk
 * @Date 2019/11/29 9:50
 * @Version 1.0
 */
public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "静态注册的广播接收者", Toast.LENGTH_SHORT).show();
    }
}
