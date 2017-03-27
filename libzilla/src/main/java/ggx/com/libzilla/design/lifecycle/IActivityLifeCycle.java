package ggx.com.libzilla.design.lifecycle;

/**
 * Created by jerry.guan on 3/27/2017.
 */

public interface IActivityLifeCycle {

    void onCreated();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onRestart();

    void onDestroy();
}
