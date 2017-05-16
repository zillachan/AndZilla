package com.zilla.andzilla.log;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zilla.andzilla.R;

import java.util.ArrayList;
import java.util.List;

import ggx.com.libzilla.core.log.AppLog;
import ggx.com.libzilla.util.MutilRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogFragment extends Fragment {


    RecyclerView list;
    List<Person> items;
    MutilRecycleAdapter<Person> adapter;

    public LogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list= (RecyclerView) view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        items=new ArrayList<>();
        for (int i=0;i<50;i++){
            if(i==2){
                items.add(new Person(0,"item"+i));
            }else {
                items.add(new Person(1,"item"+i));
            }
        }
        adapter=new MutilRecycleAdapter<>(items);
        adapter.setOnItemClickListener(new MutilRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AppLog.print(position+"项被点击了");
            }
        });
        adapter.setOnItemLongClickListener(new MutilRecycleAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                AppLog.print(position+"项被长按了");
            }
        });
        adapter.addVisitor(0,new Normal());
        adapter.addVisitor(1,new Special());
        list.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
