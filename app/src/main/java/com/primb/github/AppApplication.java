package com.primb.github;

import android.app.Application;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.primb.github.service.NetBroadcastReceiver;
import com.primb.github.util.AppUtils;
import com.primb.github.util.NetHelper;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import es.dmoral.toasty.Toasty;

/**
 * @author Chen
 * @date 2018/3/7
 * 功能描述：
 */

public class AppApplication extends Application {
    private static AppApplication application;
    private final String TAG = AppApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 启动更新语言
         */
        AppUtils.updateAppLanguage(getApplicationContext());
        /**
         * 初始化日志框架
         */
        initLogger();
        /**
         * 初始化bugly框架
         */
        initBugly();
        /**
         * 初始化网络状态
         */
        initNetwork();
    }

    /**
     * 初始化日志框架
     */
    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(0)
                .tag("GitHub_Logger")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

            @Override
            public void log(int priority, String tag, String message) {
                super.log(priority, tag, message);
            }
        });
    }

    /**
     * 初始化bugly框架
     */
    private void initBugly() {
        Beta.initDelay = 6 * 1000;
        Beta.enableHotfix = false;
//        Beta.canShowUpgradeActs.add(LoginActivity.class);
//        Beta.canShowUpgradeActs.add(MainActivity.class);
//        Beta.canShowUpgradeActs.add(AboutActivity.class);
//        Beta.upgradeListener = UpgradeDialog.INSTANCE;

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppChannel(getAppChannel());
        strategy.setAppReportDelay(10 * 1000);
        Bugly.init(getApplicationContext(), AppConfig.BUGLY_APPID, BuildConfig.DEBUG, strategy);
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);
    }

    /**
     * 初始化网络状态
     */
    private void initNetwork() {
        NetBroadcastReceiver receiver = new NetBroadcastReceiver();
        IntentFilter filter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        } else {
            filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }
        registerReceiver(receiver, filter);
        NetHelper.INSTANCE.init(this);
    }

    private String getAppChannel() {
        String channel = "normal";
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            channel = applicationInfo.metaData.getString("BUGLY_APP_CHANNEL", "normal");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static AppApplication get() {
        return application;
    }
}
