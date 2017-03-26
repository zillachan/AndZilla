package com.zilla.andzilla;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zilla.andzilla.log.LogActivity;

import ggx.com.libzilla.log.AppLog;
import ggx.com.libzilla.log.CrashHandler;

public class MainActivity extends AppCompatActivity {

    CrashHandler crash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        crash=CrashHandler.regist(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.print("测试普通日志"+new Object());
                AppLog.apply(MainActivity.this).print("测试写入文件日志");
                startActivity(new Intent(MainActivity.this, LogActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppLog.getfileLog().onRequestPermissionsResult(requestCode,permissions,grantResults);
        crash.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLog.i("界面销毁i");
        AppLog.print(1);
    }
}
