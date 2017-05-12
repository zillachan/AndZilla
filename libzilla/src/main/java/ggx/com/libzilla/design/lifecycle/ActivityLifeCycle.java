package ggx.com.libzilla.design.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jerry.Guan
 *         created by 2017/5/11
 */

public class ActivityLifeCycle implements Application.ActivityLifecycleCallbacks{

    Map<String,Object> lifeCycle=new LinkedHashMap<>();


    private String getActivityName(Activity activity){
        String fullName=activity.getClass().getName();
        return null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //更具当前的activity去分发需要执行的生命周期回调
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
