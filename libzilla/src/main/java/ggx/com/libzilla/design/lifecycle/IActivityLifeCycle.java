package ggx.com.libzilla.design.lifecycle;

import android.os.Bundle;

/**
 * Created by jerry.guan on 3/27/2017.
 */

public interface IActivityLifeCycle {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onRestart();

    void onDestroy();
}
