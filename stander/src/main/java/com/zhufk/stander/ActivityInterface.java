package com.zhufk.stander;

import android.app.Activity;
import android.os.Bundle;

/**
 * @ClassName ActivityInterface
 * @Description activity标准
 * @Author zhufk
 * @Date 2019/11/28 9:26
 * @Version 1.0
 */
public interface ActivityInterface {

    /**
     *  把宿主环境给插件
     * @param appActivity
     */
    void insertAppContext(Activity appActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume() ;

    void onPause() ;

    void onStop() ;

    void onDestroy() ;
}
