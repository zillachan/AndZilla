package ggx.com.mvpdemo.mvplib;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public abstract class BasePersent<T extends BaseView> {

    private WeakReference<T> weakReference;

    public BasePersent(T baseView) {
        setView(baseView);
    }

    public T getView() {
        return weakReference!=null?weakReference.get():null;
    }

    public void setView(T view) {
        this.weakReference = new WeakReference<>(view);
    }

    public void recycle(){
        if(weakReference!=null){
            weakReference.clear();
            weakReference=null;
        }
    }
}
