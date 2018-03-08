package com.primb.github.common;

/**
 * @author Chen
 * @date 2018/3/8
 * 功能描述：具体事件
 */

public class Event {
    /**
     * 网络状态改变事件
     */
    public static class NetChangedEvent{
        public int preNetStatus;
        public int curNetStatus;
        public NetChangedEvent(int preNetStatus, int curNetStatus){
            this.preNetStatus = preNetStatus;
            this.curNetStatus = curNetStatus;
        }
    }
}

