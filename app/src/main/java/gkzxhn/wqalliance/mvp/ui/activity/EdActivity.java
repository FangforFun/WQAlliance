package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.jess.arms.utils.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.AppComponent;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.FileUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.ui.adapter.EvidenceListAdapter;

/**
 * Created by 方 on 2017/3/9.
 */

public class EdActivity extends BaseContentActivity {

    private RecyclerView mRl_evidence;
    private EvidenceListAdapter mEvidenceListAdapter;
    private int mType;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mType = getIntent().getIntExtra("type", 0);
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
        mTvSubtitle.setVisibility(View.GONE);
    }

    private ProgressDialog loginDialog;

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_ed, null, false);
        mRl_evidence = (RecyclerView) contentView.findViewById(R.id.rl_evidence_list);
        if (NetworkUtils.isConnected()) {
            loginDialog = UiUtils.showProgressDialog(this, getString(R.string.loading));
            Log.i(TAG, "initContentView: mType---------" + mType);
//
            ApiWrap.getEvidences(mType, new SimpleObserver<EvidenceList>() {
                @Override
                public void onError(Throwable e) {
                    UiUtils.dismissProgressDialog(loginDialog);
                    UiUtils.makeText(getString(R.string.timeout_retry));
                    LogUtils.e(TAG, "show_evidence_list exception: " + e.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EdActivity.this.finish();
                        }
                    }, 500);
                }
//
                @Override
                public void onNext(EvidenceList evidenceList) {
                    LogUtils.i(TAG, "show_evidence_list: " + evidenceList.toString());
//                    UiUtils.makeText(evidenceList.msg);
                    mRl_evidence.setLayoutManager(new LinearLayoutManager(EdActivity.this));
                    mEvidenceListAdapter = new EvidenceListAdapter(evidenceList.data, EdActivity.this);
                    mRl_evidence.setAdapter(mEvidenceListAdapter);
                    UiUtils.dismissProgressDialog(loginDialog);
                }
            });
        }else {
            UiUtils.makeText(getResources().getString(R.string.net_broken));
            Log.i(TAG, "initContentView: 网络异常");
        }

        return contentView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                Log.i(TAG, "onRequestPermissionsResult: camera--------");
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //执行后续的操作
                    Toast.makeText(this, "相机已经授权成功了", Toast.LENGTH_SHORT).show();
                    // TODO: 2016/11/4
                    mEvidenceListAdapter.takePhoto(requestCode);
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEvidenceListAdapter = null;
    }

    private AlertDialog chooseDialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: resultCode    " + resultCode);
        chooseDialog = mEvidenceListAdapter.getChooseDialog();

        if (resultCode == -1) {

        int position = requestCode % 1000;
        int type = requestCode / 1000;
        if (type == 0) {
            //图库
            ContentResolver resolver = getContentResolver();
            Uri originalUri = data.getData();
            String path = FileUtil.getPath(this, originalUri);
            Log.i(TAG, "originalUri : " + path);
            // /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1488807352169.jpg
            // raw//storage/emulated/0/DCIM/Camera/IMG_20170309_080815.jpg
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                DialogUtil.dismissDialog(chooseDialog);
                if (photo != null) {
                    // 先上传  上传成功再更新数据  更新成功再显示在界面上
                    mEvidenceListAdapter.uploadImage(photo, path, position);
                }else {
                    UiUtils.makeText(getString(R.string.read_file_error));
                }
            } catch (FileNotFoundException e) {
                UiUtils.makeText(getString(R.string.file_not_exist));
            } catch (IOException e) {
                UiUtils.makeText(getString(R.string.read_file_error));
            }
        }else {
            //相机
            DialogUtil.dismissDialog(chooseDialog);
            String path = Environment
                    .getExternalStorageDirectory() + File.separator + "image.jpg";
            mEvidenceListAdapter.uploadImage(BitmapFactory.decodeFile(path), path, position);
        }
        }

    }
}
