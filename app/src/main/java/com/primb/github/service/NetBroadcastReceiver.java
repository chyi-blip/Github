package com.primb.github.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.primb.github.common.AppEventBus;
import com.primb.github.common.Event;
import com.primb.github.util.NetHelper;

/**
 * @author Chen
 * @date 2018/3/8
 * 功能描述：监听网络状态
 */

public class NetBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            int preNetStatus = NetHelper.INSTANCE.getNetStatus();
            NetHelper.INSTANCE.checkNet();
            int curNetStatus = NetHelper.INSTANCE.getNetStatus();
            AppEventBus.INSTANCE.getEventBus().post(new Event.NetChangedEvent(preNetStatus, curNetStatus));
        }
    }
}
