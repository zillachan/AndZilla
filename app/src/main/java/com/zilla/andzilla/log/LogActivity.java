package com.zilla.andzilla.log;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ggx.andzilla.annotation.BindView;
import com.zilla.andzilla.R;

import java.util.ArrayList;
import java.util.List;

import ggx.com.libzilla.core.log.AppLog;
import ggx.com.libzilla.design.lifecycle.IActivityLifeCycle;
import ggx.com.libzilla.util.MutilRecycleAdapter;


public class LogActivity extends AppCompatActivity{

    @BindView(R.id.btn)
    Button btn;

    RecyclerView list;

    List<Person> items;
    MutilRecycleAdapter<Person> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.apply(LogActivity.this).print("测试二");
            }
        });
        list= (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        items=new ArrayList<>();
        for (int i=0;i<10;i++){
            items.add(new Person("item"+i));
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
        list.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppLog.getfileLog().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
