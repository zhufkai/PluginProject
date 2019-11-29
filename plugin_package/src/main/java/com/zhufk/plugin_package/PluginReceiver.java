package com.zhufk.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zhufk.stander.ReceiverInterface;

/**
 * @ClassName PluginReceiver
 * @Description TODO
 * @Author zhufk
 * @Date 2019/11/29 9:21
 * @Version 1.0
 */
public class PluginReceiver extends BroadcastReceiver implements ReceiverInterface {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "插件广播接收者。。。", Toast.LENGTH_SHORT).show();
    }
}
