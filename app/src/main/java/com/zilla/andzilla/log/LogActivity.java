package com.zilla.andzilla.log;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ggx.andzilla.annotation.Lifecycle;
import com.zilla.andzilla.R;
import com.zilla.andzilla.TestLifeCycle1;

import ggx.com.libzilla.core.log.AppLog;

@Lifecycle(TestLifeCycle1.class)
public class LogActivity extends AppCompatActivity {

    RelativeLayout rl_send;

    EditText et_input;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new LogFragment()).commit();
        et_input= (EditText) findViewById(R.id.et_input);
        rl_send= (RelativeLayout) findViewById(R.id.rl_send);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppLog.getfileLog().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
