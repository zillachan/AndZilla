package ggx.com.libzilla.util;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author jerry.Guan
 *         created by 2017/4/9
 */

public class MutilRecycleAdapter<T extends ItemModel> extends RecyclerView.Adapter<BaseViewHolder>{

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private SparseArray<Visitor> visitorSparseArray;
    private List<T> models;

    public MutilRecycleAdapter(List<T> models) {
        this.models=models;
        visitorSparseArray=new SparseArray<>();
    }

    public void addVisitor(int type,Visitor visitor){
        visitorSparseArray.put(type,visitor);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Visitor visitor=visitorSparseArray.get(viewType);
        if(visitor==null){
            //需要处理null的情况
        }
        View itemView=LayoutInflater.from(parent.getContext()).inflate(visitor.getViewLayout(),parent,false);
        BaseViewHolder<T> holder=visitor.createViewHolder(itemView);
        holder.setViewType(viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (onItemClickListener!=null){
            holder.setOnItemClickListener(onItemClickListener,position);
        }
        if(onItemLongClickListener!=null){
            holder.setOnItemLongClickListener(onItemLongClickListener,position);
        }
        holder.setLogic(models.get(position),position,holder.getViewType());
    }

    @Override
    public int getItemViewType(int position) {
        return models.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return models==null?0:models.size();
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
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }
}
