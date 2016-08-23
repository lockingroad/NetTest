package com.xhyy.lxr.nettest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xhyy.lxr.nettest.entity.StrNetInfo;
import com.xhyy.lxr.nettest.message.MessageEvent;
import com.xhyy.lxr.nettest.util.PostUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by LXR on 2016/8/18.
 */
public class PostActivity extends Activity {
    @InjectView(R.id.post_get1)
    Button postGet1;
    @InjectView(R.id.post_get2)
    Button postGet2;
    private String TAG = "PostActivity";
    private String url="http://123.56.186.79/V1_5/Activity/payactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this);
        EventBus.getDefault().register(PostActivity.this);
    }

    @OnClick({R.id.post_get1, R.id.post_get2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_get1:
                requestInfo1();
                break;
            case R.id.post_get2:
                requestInfo2();
                break;
        }
    }

    private void requestInfo2() {
        OkHttpUtils.post().url(url)
                .addParams("page","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"工具post得到的信息"+response);
                    }
                });
    }

    private void requestInfo1() {
        final StrNetInfo info=new StrNetInfo();
        info.inter=url;
        info.parame="page=1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str=new PostUtil().getString(info);
                EventBus.getDefault().post(new MessageEvent(str),"post_tao");
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(PostActivity.this);
    }
    @Subscriber(tag = "post_tao",mode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        Log.e(TAG,"普通post得到的信息"+event.getMessage());
    }
}
