package com.zhufk.pluginproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

/**
 * @ClassName PluginManager
 * @Description 插件管理类
 * @Author zhufk
 * @Date 2019/11/28 9:37
 * @Version 1.0
 */
public class PluginManager {
    public static final String TAG = PluginManager.class.getSimpleName();

    private static PluginManager instance;

    private Context context;

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    private PluginManager(Context context) {
        this.context = context;
    }

    private DexClassLoader dexClassLoader;
    private Resources resources;

    /**
     * 加载插件; 1、activity 2、layout
     */
    public void loadPlugin() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
            if (!file.exists()) {
                Log.d(TAG, "插件包不存在。。。");
                return;
            }

            /**
             *  插件里面的 class部分
             */
            //插件包路径
            String pluginPath = file.getAbsolutePath();
            //dexClassLoader需要一个缓存目录 /data/data/当前应用包名/pluginDir
            File pluginDir = context.getDir("pluginDir", Context.MODE_PRIVATE);

            dexClassLoader = new DexClassLoader(pluginPath, pluginDir.getAbsolutePath(), null, context.getClassLoader());

            /**
             *  插件里面的 resources
             */
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, pluginPath);
            Resources r = context.getResources();
            resources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public void parserApkAction() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
            if (!file.exists()) {
                Log.d(TAG, "插件包不存在。。。");
                return;
            }

            Class mPackageParserClass = Class.forName("android.content.pm.PackageParser");
            Object mPackageParser = mPackageParserClass.newInstance();

            //public Package parsePackage(File packageFile, int flags)
            Method mParsePackageMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
            Object mPackage = mParsePackageMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);

            //Package 文件分析
            Field receiversField = mPackage.getClass().getDeclaredField("receivers");
            Object receivers = receiversField.get(mPackage);
            //public final ArrayList<Activity> receivers
            ArrayList arrayList = (ArrayList) receivers;

            //public final static class Activity extends Component<ActivityIntentInfo>
            for (Object mActivity : arrayList) {
                //TODO 优化：class放到循环外处理 减少开销

                //IntentFilter == Activity.intents (Activity->Component.intents)
                Class mComponentClass = Class.forName("android.content.pm.PackageParser$Component");
                Field intentsField = mComponentClass.getDeclaredField("intents");
                //对象可以用Component的子类mActivity
                Object intentsObject = intentsField.get(mActivity);
                ArrayList<IntentFilter> intents = (ArrayList) intentsObject;

                Class mPackageUserStateClass = Class.forName("android.content.pm.mPackageUserState");
                Object packageUserState = mPackageUserStateClass.newInstance();

                //public static @UserIdInt int getCallingUserId()
                Class mUserHandleClass = Class.forName("android.os.UserHandle");
                //静态方法invoke不需要对象
                int userId = (int) mUserHandleClass.getMethod("getCallingUserId").invoke(null);

                //拿到广播类名 ActivityInfo-->ComponentInfo-->PackageItemInfo.name
                //ActivityInfo.name == android:name=".StaticReceiver"
                //public static final ActivityInfo generateActivityInfo(Activity a, int flags, PackageUserState state, int userId)
                Method generateActivityInfoMethod = mPackageParserClass.getMethod("generateActivityInfo", mActivity.getClass(),
                        int.class, mPackageUserStateClass, int.class);
                ActivityInfo activityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(null, mActivity, 0, packageUserState, userId);
                String receiver = activityInfo.name;

                Class staticReceiverClass = getDexClassLoader().loadClass(receiver);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) staticReceiverClass.newInstance();

                for (IntentFilter intentFilter : intents) {
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
