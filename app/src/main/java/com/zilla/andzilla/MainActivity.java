package com.zilla.andzilla;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ggx.andzilla.annotation.BindView;

import ggx.com.libzilla.core.log.AppLog;
import ggx.com.libzilla.core.log.CrashHandler;
import ggx.com.libzilla.core.permission.MPermission;
import ggx.com.libzilla.core.permission.PermissionFail;
import ggx.com.libzilla.core.permission.PermissionOK;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn)
    TextView tv;

    CrashHandler crash;
    MPermission permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        crash=CrashHandler.regist(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission=MPermission.with(this);
        String s=null;
        s.equals("dsa");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.print("测试普通日志"+new Object());
                AppLog.apply(MainActivity.this).print("测试写入文件日志");
                permission.apply(100, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);

//                startActivity(new Intent(MainActivity.this, LogActivity.class));
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
    @PermissionFail(id = 100)
    private void permissionFail(){
        AppLog.print("权限失败");
    }

    @PermissionOK(id = 100)
    private void permissionOK(){
        AppLog.print("权限成功");
    }
    @PermissionFail(id = 101)
    private void permissionFail1(){
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
