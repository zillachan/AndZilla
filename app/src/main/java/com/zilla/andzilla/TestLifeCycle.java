package com.zilla.andzilla;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import ggx.com.libzilla.core.log.AppLog;

/**
 * Created by jerry.guan on 5/12/2017.
 */

public class TestLifeCycle implements Application.ActivityLifecycleCallbacks{
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppLog.print("哈哈哈哈  oncreat测试成功");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppLog.print("哈哈哈哈  oncreat测试成功");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppLog.print("哈哈哈哈  Resumed测试成功");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AppLog.print("哈哈哈哈  Paused测试成功");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        AppLog.print("哈哈哈哈  Stopped测试成功");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        AppLog.print("哈哈哈哈 SaveInstanceState测试成功");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppLog.print("哈哈哈哈  onDestory测试成功");
    }
}
