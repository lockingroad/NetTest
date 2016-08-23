package com.xhyy.lxr.nettest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_get)
    Button mainGet;
    @InjectView(R.id.main_post)
    Button mainPost;
    @InjectView(R.id.main_up_load_single)
    Button mainDownLoad;
    @InjectView(R.id.main_up_load_multi)
    Button mainUpLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.main_get, R.id.main_post, R.id.main_up_load_single,R.id.main_up_load_multi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_get:
                Intent intent=new Intent(MainActivity.this,GetActivity.class);
                startActivity(intent);
                break;
            case R.id.main_post:
                Intent intent1=new Intent(MainActivity.this,PostActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_up_load_single:
                Intent intent2=new Intent(MainActivity.this,SingleUpActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_up_load_multi:
                break;
        }
    }


}
