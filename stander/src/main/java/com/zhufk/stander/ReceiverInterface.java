package com.zhufk.stander;

import android.content.Context;
import android.content.Intent;

/**
 * @ClassName PluginReceiver
 * @Description TODO
 * @Author zhufk
 * @Date 2019/11/29 9:38
 * @Version 1.0
 */
public interface ReceiverInterface {
     void onReceive(Context context, Intent intent);
}
