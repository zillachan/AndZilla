package ggx.com.mvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ggx.com.mvpdemo.mvplib.BasePersent;
import ggx.com.mvpdemo.mvplib.BaseView;

public class BaseActivity<T extends BasePersent<? extends BaseView>> extends AppCompatActivity implements BaseView {
    public static String TAG;

    private T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getCanonicalName();
    }

    @Override
    protected void onDestroy() {
        if(presenter!=null){
            presenter.recycle();
        }
        super.onDestroy();
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(String message) {
        Log.i(TAG,"dialog show "+message);
    }

    @Override
    public void hideLoading() {
        Log.i(TAG,"dialog hiden");
    }
}
