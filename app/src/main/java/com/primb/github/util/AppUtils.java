package com.primb.github.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.primb.github.AppApplication;
import com.primb.github.R;

import es.dmoral.toasty.Toasty;

/**
 * @author Chen
 * @date 2018/3/7
 * 功能描述：APP基本设置
 */

public class AppUtils {
    private static final String DOWNLOAD_SERVICE_PACKAGE_NAME = "com.android.providers.downloads";

    public static boolean isNightMode(){

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
