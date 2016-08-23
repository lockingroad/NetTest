package com.xhyy.lxr.nettest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xhyy.lxr.nettest.entity.StrNetInfo;
import com.xhyy.lxr.nettest.message.MessageEvent;
import com.xhyy.lxr.nettest.util.GetUtil;
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
 * Created by LXR on 2016/8/17.
 */
public class GetActivity extends Activity {
    @InjectView(R.id.get_btn1)
    Button getBtn1;
    @InjectView(R.id.get_btn2)
    Button getBtn2;
    String url="http://123.56.186.79/";
    private String TAG="GetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        ButterKnife.inject(this);
        EventBus.getDefault().register(GetActivity.this);


    }

    @OnClick({R.id.get_btn1, R.id.get_btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_btn1:
                requestInfo1();

                break;
            case R.id.get_btn2:
                requestInfo2();
                break;
        }
    }

    private void requestInfo2() {
        OkHttpUtils.get().url(url).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"失败了");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"get工具类得到"+response);
                    }
                });
    }

    private void requestInfo1() {
        final StrNetInfo info=new StrNetInfo();
        info.inter=url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str=new GetUtil().getString(info);
                EventBus.getDefault().post(new MessageEvent(str));
            }
        }).start();
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        Log.e(TAG,"普通get接受到的信息"+event.getMessage());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(GetActivity.this);
    }
}
