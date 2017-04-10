package com.zilla.andzilla.log;

import android.view.View;
import android.widget.TextView;

import com.zilla.andzilla.R;

import ggx.com.libzilla.util.BaseViewHolder;
import ggx.com.libzilla.util.Visitor;

/**
 * @author jerry.Guan
 *         created by 2017/4/10
 */

public class Normal implements Visitor{
    @Override
    public BaseViewHolder createViewHolder(View itemView) {
        return new BaseViewHolder<Person>(itemView) {
            @Override
            public void setLogic(BaseViewHolder holder, Person model, int position, int itemType) {
                TextView tv= (TextView) holder.getView(R.id.t1);
                tv.setText(model.name);
            }
        };
    }

    @Override
    public int getViewLayout() {
        return R.layout.normal_item;
    }
}
