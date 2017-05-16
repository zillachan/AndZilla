package ggx.com.libzilla.design.lifecycle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * Created by jerry.guan on 5/12/2017.
 */

public class ActivityLifeCycle implements ActivityLifecycleCallbacks {

    ILifecycleManager manager;


    public ActivityLifeCycle() {
        try {
            Class<?> clazz=Class.forName("com.ggx.lib.lifecycle.LifecycleManager");
            manager= (ILifecycleManager) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private ActivityLifecycleCallbacks getLifeCycle(Activity activity){
        String fullName=activity.getClass().getName();
        //通过activity的名字获取对应的生命周期注册对象
        if(manager!=null){
            return manager.getLifecycle(fullName);
        }
        return null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityCreated(activity,savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityStarted(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityResumed(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityPaused(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityStopped(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivitySaveInstanceState(activity,outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityLifecycleCallbacks callbacks=getLifeCycle(activity);
        if(callbacks!=null){
            callbacks.onActivityDestroyed(activity);
        }
    }
}
