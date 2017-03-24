package ggx.com.mvpdemo;

import ggx.com.mvpdemo.mvplib.BasePersent;
import ggx.com.mvpdemo.mvplib.BaseView;
import ggx.com.mvpdemo.mvplib.BaseView2;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public class DemoPersent extends BasePersent<DemoPersent.DemoView> {

    Service service;

    public DemoPersent(DemoView baseView) {
        super(baseView);
    }

    public void doOnClick(String classId){
        getView().showLoading("正在加载");
        service.getMainDate(classId, new CallBack() {
            @Override
            public void success(String str) {
                DemoView view=getView();
                if(view!=null){
                    view.hideLoading();
                    view.updateBtn2(str);
                }
            }
        });

    }
    public void  doSomthing(){
        String str="haha22222";
        getView().updateBtn1(str);
    }

    public interface DemoView extends BaseView {

         void updateBtn1(String str);

         void updateBtn2(String str);
    }
}
