package ggx.com.libzilla.design.lifecycle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * Created by jerry.guan on 5/12/2017.
 */

public class ActivityLifeCycle implements ActivityLifecycleCallbacks {

    ILifecycleManager manager;

    private ActivityLifecycleCallbacks getLifeCycle(Activity activity){
        String fullName=activity.getClass().getName();
        //通过activity的名字获取对应的生命周期注册对象
        return null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
