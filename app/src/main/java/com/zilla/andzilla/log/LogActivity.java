package com.zilla.andzilla.log;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ggx.andzilla.annotation.BindView;
import com.zilla.andzilla.R;

import ggx.com.libzilla.core.log.AppLog;
import ggx.com.libzilla.design.lifecycle.IActivityLifeCycle;


public class LogActivity extends AppCompatActivity{

    @BindView(R.id.btn)
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.apply(LogActivity.this).print("测试二");
            }
        });
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
