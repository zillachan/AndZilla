package ggx.com.libzilla.design.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public abstract class BasePresenter<T extends BaseView> {

    private WeakReference<T> weakReference;

    public BasePresenter(T baseView) {
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
