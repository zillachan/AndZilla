package ggx.com.mvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends BaseActivity<DemoPersent> implements DemoPersent.DemoView {

    RadioButton rb1;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPresenter(new DemoPersent(this));
        rg= (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb1:{
                        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content,new Fragment1());
                        ft.commit();
                    }
                        break;
                    case R.id.rb2:{
                        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content,new Fragment2());
                        ft.commit();
                    }
                        break;
                }
            }
        });
        rb1= (RadioButton) findViewById(R.id.rb1);
        rb1.setChecked(true);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().doOnClick("dsadas");
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().doSomthing();
            }
        });

    }


    @Override
    public void updateBtn1(String str) {
        ((Button)findViewById(R.id.btn)).setText(str);
    }

    @Override
    public void updateBtn2(String str) {
        ((Button)findViewById(R.id.btn1)).setText(str);
    }
}
