package com.zhufk.pluginproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhufk.stander.ReceiverInterface;

/**
 * @ClassName ProxyReceiver
 * @Description TODO
 * @Author zhufk
 * @Date 2019/11/29 9:25
 * @Version 1.0
 */
public class ProxyReceiver extends BroadcastReceiver {
    private String pluginServiceClassName;

    public ProxyReceiver(String pluginServiceClassName) {
        this.pluginServiceClassName = pluginServiceClassName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Class pluginReceiverClass = PluginManager.getInstance(context).getDexClassLoader().loadClass(pluginServiceClassName);
            Object pluginReceiver = pluginReceiverClass.newInstance();
            ReceiverInterface receiverInterface = (ReceiverInterface) pluginReceiver;
            receiverInterface.onReceive(context, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
