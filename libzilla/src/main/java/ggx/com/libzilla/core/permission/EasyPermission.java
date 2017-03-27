package ggx.com.libzilla.core.permission;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jerry.guan on 3/27/2017.
 */

public class EasyPermission {

    public static boolean hasPermission(AppCompatActivity activity, @Size(min = 1) String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void apply(AppCompatActivity activity, int requestCode, @Size(min = 1) String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallback callback) {
        if (grantResults.length == permissions.length) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    if (callback != null) {
                        callback.onDenied(requestCode);
                    }
                    return;
                }
            }
            //权限都允许了,初始化相机
            if (callback != null) {
                callback.onGranted(requestCode);
            }
        } else {
            if (callback != null) {
                callback.onDenied(requestCode);
            }
        }
    }

    public interface PermissionCallback {
        void onGranted(int requestCode);

        void onDenied(int requestCode);
    }
}
