package com.zilla.andzilla;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jerry.guan on 3/28/2017.
 */

public class AndZillaApplication extends Application{

    public static RefWatcher getRefWatcher(Context context) {
        AndZillaApplication application = (AndZillaApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher= LeakCanary.install(this);
    }
}
