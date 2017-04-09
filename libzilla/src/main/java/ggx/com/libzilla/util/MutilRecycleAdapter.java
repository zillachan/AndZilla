package ggx.com.libzilla.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * @author jerry.Guan
 *         created by 2017/4/9
 */

public class MutilRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    SparseArray<Visitor> visitorSparseArray;
    List<T> datas;

    public MutilRecycleAdapter(Context context,List<T> datas) {
        this.mContext = context;
        this.datas=datas;
        visitorSparseArray=new SparseArray<>();
    }

    public void addVisitor(Visitor visitor){
        visitorSparseArray.put(visitor.getViewLayout(),visitor);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(mContext).inflate(viewType,parent,false);
        BaseViewHolder holder=new BaseViewHolder(itemView);
        holder.setViewType(viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        visitorSparseArray.get(holder.getViewType())
                .setLogic(holder,position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick();
    }

    public interface OnItemLongClickListener{
        void onItemLongClick();
    }
}
