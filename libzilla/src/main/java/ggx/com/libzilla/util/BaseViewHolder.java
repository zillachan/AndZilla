package ggx.com.libzilla.util;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author jerry.Guan
 *         created by 2017/4/9
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private int viewType;
    public BaseViewHolder(View itemView) {
        super(itemView);
        views=new SparseArray<>();
    }
    public <T extends View>T getView(int resID) {
        View view = views.get(resID);

        if (view == null) {
            view = super.itemView.findViewById(resID);
            views.put(resID,view);
        }

        return (T)view;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
