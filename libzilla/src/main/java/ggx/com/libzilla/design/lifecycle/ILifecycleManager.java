package ggx.com.libzilla.design.lifecycle;

import android.app.Application;

/**
 * Created by jerry.guan on 5/12/2017.
 */

public interface ILifecycleManager {

    void removeLifecycle(String key);
    Application.ActivityLifecycleCallbacks getLifecycle(String key);

}
