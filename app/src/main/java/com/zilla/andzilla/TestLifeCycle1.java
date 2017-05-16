package com.zilla.andzilla;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import ggx.com.libzilla.core.log.AppLog;

/**
 * Created by jerry.guan on 5/12/2017.
 */

public class TestLifeCycle1 implements Application.ActivityLifecycleCallbacks{
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppLog.print("TestLifeCycle1  oncreat测试成功");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppLog.print("TestLifeCycle1  Started测试成功");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppLog.print("TestLifeCycle1  Resumed测试成功");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AppLog.print("TestLifeCycle1  Paused测试成功");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        AppLog.print("TestLifeCycle1  Stopped测试成功");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        AppLog.print("TestLifeCycle1 SaveInstanceState测试成功");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppLog.print("TestLifeCycle1  onDestory测试成功");
    }
}
