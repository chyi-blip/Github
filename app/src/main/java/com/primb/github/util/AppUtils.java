package com.primb.github.util;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.primb.github.AppApplication;
import com.primb.github.R;

import java.util.Locale;

import es.dmoral.toasty.Toasty;

/**
 * @author Chen
 * @date 2018/3/7
 * 功能描述：APP基本设置
 */

public class AppUtils {
    private static final String DOWNLOAD_SERVICE_PACKAGE_NAME = "com.android.providers.downloads";

    /**
     * 检查是否能够下载
     *
     * @param context s
     */
    public static boolean checkDownloadServiceEnabled(Context context) {
        return checkApplicationEnabledSetting(context, DOWNLOAD_SERVICE_PACKAGE_NAME);
    }

    /**
     * 检查是否能够下载
     *
     * @param context s
     */
    public static boolean checkApplicationEnabledSetting(Context context, String packageName) {
        int state = context.getPackageManager().getApplicationEnabledSetting(packageName);
        return state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ||
                state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    /**
     * 打开系统下载设置
     *
     * @param context s
     */
    public static void showDownloadServiceSetting(@NonNull Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + DOWNLOAD_SERVICE_PACKAGE_NAME));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 切换系统语言
     */
    public static void updateAppLanguage(@NonNull Context context) {
        String lang = PrefUtils.getLanguage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, lang);
        }
        updateResourcesLegacy(context, lang);
    }

    /**
     * 更新语言资源(25版本之后)
     *
     * @param language 语言分类
     */
    private static void updateResources(@NonNull Context context, @NonNull String language) {
        Locale locale = getLocale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        context.createConfigurationContext(configuration);
    }

    /**
     * 更新语言资源(25版本之前)
     *
     * @param language 语言分类
     */
    private static void updateResourcesLegacy(@NonNull Context context, @NonNull String language) {
        Locale locale = getLocale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    /**
     * 获取当前语言种类
     *
     * @param language 语言分类
     */
    @NonNull
    public static Locale getLocale(@NonNull String language) {
        Locale locale;
        if (language.equalsIgnoreCase("zh-rCN")) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (language.equalsIgnoreCase("zh-rTW")) {
            return Locale.TRADITIONAL_CHINESE;
        }
        String[] array = language.split("-");
        if (array.length > 1) {
            locale = new Locale(array[0], array[1]);
        } else {
            locale = new Locale(language);
        }
        return locale;
    }

    /**
     * 当前是否是夜间模式
     */
    public static boolean isNightMode() {
        String theme = PrefUtils.getTheme();
        return PrefUtils.DARK.equals(theme);
    }

    /**
     * 复制到剪切板
     *
     * @param context 上下文对象
     * @param content 粘贴文字内容
     */
    public static void copyToClipboard(@NonNull Context context, @NonNull String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(context.getString(R.string.app_name), content);
        clipboardManager.setPrimaryClip(clipData);
        Toasty.success(context, context.getString(R.string.success_copied)).show();
    }

    /**
     * 是否是横屏
     *
     * @param resources 资源对象
     * @return true 是  反之不是
     */
    public static boolean isLandscape(@NonNull Resources resources) {
        return resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 显示软键盘
     *
     * @param view 获取焦点的view
     * @param context 上下文对象
     */
    public static void showKeyboard(@NonNull View view, @NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 显示软键盘
     *
     * @param view 获取焦点的view
     */
    public static void showKeyboard(@NonNull View view) {
        showKeyboard(view, view.getContext());
    }

    /**
     * 隐藏软键盘
     *
     * @param view 获取焦点的view
     */
    public static void hideKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 第一次安装的时间
     */
    @Nullable
    public static long getFirstInstallTime() {
        long time = 0;
        try {
            PackageInfo packageInfo = AppApplication.get().getPackageManager()
                    .getPackageInfo(AppApplication.get().getPackageName(), 0);
            time = packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return time;
    }
}
