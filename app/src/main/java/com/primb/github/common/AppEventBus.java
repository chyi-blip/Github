package com.primb.github.common;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Chen
 * @date 2018/3/8
 * 功能描述：事件总线
 */

public enum AppEventBus {
    INSTANCE;

    private EventBus mEventBus;

    AppEventBus() {
        init();
    }

    private void init() {
        mEventBus = EventBus.builder().installDefaultEventBus();
    }

    public EventBus getEventBus() {
        return mEventBus;
    }
}
