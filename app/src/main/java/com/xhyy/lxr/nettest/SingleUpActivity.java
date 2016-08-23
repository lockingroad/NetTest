package com.xhyy.lxr.nettest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xhyy.lxr.nettest.inter.OnUploadProcessListener;
import com.xhyy.lxr.nettest.util.ImageCompress;
import com.xhyy.lxr.nettest.util.UploadUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

/**
 * Created by LXR on 2016/8/18.
 * 单图上传
 */
public class SingleUpActivity extends Activity implements OnUploadProcessListener {
    private String TAG="SingleUpActivity";
    private static final int REQUEST_IMAGE = 3;
    @InjectView(R.id.single_choose)
    Button singleChoose;
    @InjectView(R.id.single_path)
    TextView singlePath;
    @InjectView(R.id.single_up)
    Button singleUp;
    @InjectView(R.id.single_up1)
    Button singleUp1;
    private String path;
    private Map<String, String> map;
    private String inter = "http://123.56.186.79/V1_6/Student/uploadphoto";
    private File newFile;
    private UploadUtil uploadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_up);
        ButterKnife.inject(this);
        initList();


    }

    private void initList() {
        map = new HashMap<String, String>();
        map.put("m_id", "981");
        map.put("token", "123456");
        uploadUtil=UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(SingleUpActivity.this);

    }

    @OnClick({R.id.single_choose, R.id.single_up,R.id.single_up1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single_choose:
                choosePhoto();
                break;
            case R.id.single_up:
                upPhoto1();
                break;
            case R.id.single_up1:
                upPhoto2();
                break;
        }
    }

    private void upPhoto2() {
        Log.e(TAG,"开始postUtil上传了");
        OkHttpUtils.post()
                .addFile("file1",newFile.getName(),newFile)
                .url(inter)
//                .params(map)
                .addParams("m_id","981")
                .addParams("token","123456")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"失败了");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"得到的信息"+response);
                    }
                });
    }

    private void upPhoto1() {
        uploadUtil.uploadFile(newFile,"file1",inter,map);
    }

    private void choosePhoto() {
        Intent intent = new Intent(SingleUpActivity.this, MultiImageSelectorActivity.class);
// whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
//        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"选择了图片 "+resultCode);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                String path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).get(0);
                Log.e(TAG,"没有进来 "+path);
                singlePath.setText(path);
                newFile = ImageCompress.serverScal(Uri.parse(path));

            }
        }
    }


    @Override
    public void onUploadDone(int responseCode, String message) {
        Log.e(TAG,"得到的信息"+message);
    }

    @Override
    public void onUploadProcess(int uploadSize) {

    }

    @Override
    public void initUpload(int fileSize) {

    }
}
