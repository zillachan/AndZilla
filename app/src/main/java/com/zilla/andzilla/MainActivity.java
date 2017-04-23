package com.zilla.andzilla;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ggx.andzilla.annotation.AuthorityFail;
import com.ggx.andzilla.annotation.AuthorityOK;
import com.ggx.andzilla.annotation.BindView;
import com.zilla.andzilla.log.LogActivity;

import ggx.com.ioc_api.GBinder;
import ggx.com.libzilla.core.log.AppLog;
import ggx.com.libzilla.core.log.CrashHandler;
import ggx.com.libzilla.core.permission.MPermission;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.btn)
    Button tv;
    @BindView(R.id.btn1)
    Button tv1;
    @BindView(R.id.btn2)
    Button tv2;

    CrashHandler crash;
    MPermission permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        crash=CrashHandler.regist(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission=MPermission.with(this);
        GBinder.bind(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.print("测试普通日志"+new Object());
                AppLog.apply(MainActivity.this).print("测试写入文件日志");
                //permission.apply(100, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);
                startActivity(new Intent(MainActivity.this, LogActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppLog.getfileLog().onRequestPermissionsResult(requestCode,permissions,grantResults);
        crash.onRequestPermissionsResult(requestCode,permissions,grantResults);
        permission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }
    @AuthorityOK(100)
    public void permissionFail(){
        AppLog.print("权限失败");
    }

    @AuthorityOK( 100)
    public void permissionOK(){
        AppLog.print("权限成功");
    }
    @AuthorityFail(101)
    public void permissionFail1(){
        AppLog.print("权限失败了dsadsadas");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        permission.recycle();
        super.onDestroy();
    }
}
