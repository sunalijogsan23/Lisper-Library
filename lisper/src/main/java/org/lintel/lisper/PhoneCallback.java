package org.lintel.lisper;

/**
 * Created by Mark Xu on 17/3/13.
 * 状态回调
 */

public abstract class PhoneCallback {

    public void incomingCall(LisperCall lisperCall) {}

    /**
     * 呼叫初始化
     */
    public void outgoingInit() {}

    /**
     * 电话接通
     */
    public void callConnected() {}

    /**
     * 电话挂断
     */
    public void callEnd() {}

    /**
     * 释放通话
     */
    public void callReleased() {}

    /**
     * 连接失败
     */
    public void error() {}
}
