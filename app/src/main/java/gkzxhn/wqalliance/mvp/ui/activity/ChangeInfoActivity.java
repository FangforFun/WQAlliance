package gkzxhn.wqalliance.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.AppComponent;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.FileUtil;
import gkzxhn.utils.ImageTools;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.InfoChangedEvent;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.widget.CircleImageView;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:修改资料activity
 */
public class ChangeInfoActivity extends BaseContentActivity implements View.OnClickListener {

    private CircleImageView iv_avatar;
    private EditText et_company_name;

    private static final int CHOOSE_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private Bitmap photo;
    private String avatar_path;

    private boolean canUpload = false;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.info_change));
        mTvSubtitle.setText(getString(R.string.save));
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_change_info, null, false);
        iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        et_company_name = (EditText) view.findViewById(R.id.et_company_name);
        et_company_name.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        String faceImgUrl = (String) SPUtil.get(this, SharedPreferenceConstants.FACEIMGURL, "");
        String userName = (String) SPUtil.get(this, SharedPreferenceConstants.USERNAME, "");
        if (!TextUtils.isEmpty(userName))
            et_company_name.setText(userName);
        if (!TextUtils.isEmpty(faceImgUrl)){
            LogUtils.i(TAG, faceImgUrl);
            Glide.with(this).load(faceImgUrl).error(R.drawable.avatar_def).into(iv_avatar);
        }
    }

    @Override
    protected void doSubtitle() {
        if (photo != null && !TextUtils.isEmpty(avatar_path) && canUpload) {
            uploadImage(photo, avatar_path);
        }else {
            UiUtils.makeText("请完善信息再保存");
        }
    }

    @Override
    protected void onDestroy() {
        UiUtils.dismissProgressDialog(uploadDialog);
        DialogUtil.dismissDialog(chooseImageDialog);
        super.onDestroy();
    }

    private AlertDialog chooseImageDialog;
    private ProgressDialog uploadDialog;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_avatar:
                chooseImageDialog = UiUtils.showSingleChooseDialog(this, getString(R.string.please_choose), new String[]{"图库", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.i(TAG, "checked position: " + which);
                        switch (which){
                            case 0:// 图库
                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
                                break;
                            case 1:// 相机
                                //检查权限
                                if (ContextCompat.checkSelfPermission(ChangeInfoActivity.this, Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    //申请WRITE_EXTERNAL_STORAGE权限
                                    ActivityCompat.requestPermissions(ChangeInfoActivity.this, new String[]{Manifest.permission.CAMERA},
                                            1);
                                }else {
                                    openCamera();
                                }
                                openCamera();
                                break;
                        }
                    }
                });
                break;
        }
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
                    openCamera();
                }
                break;
        }
    }


    /**
     * 打开相机
     */
    private void openCamera() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        Uri imageUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), String.valueOf(System.currentTimeMillis()) + ".jpg"));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CHOOSE_PHOTO){
                ContentResolver resolver = getContentResolver();
                Uri originalUri = data.getData();
                String path = FileUtil.getPath(this, originalUri);
                Log.i(TAG, "originalUri : " + path);
                // /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1488807352169.jpg
                try {
                    photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    DialogUtil.dismissDialog(chooseImageDialog);
                    if (photo != null) {
                        iv_avatar.setImageBitmap(ImageTools.zoomBitmap(photo, photo.getWidth() / 5, photo.getHeight() / 5));
                        // 先上传  上传成功再更新数据  更新成功再显示在界面上
                        avatar_path = path;
                        canUpload = true;
                    }else {
                        UiUtils.makeText(getString(R.string.read_file_error));
                        canUpload = false;
                    }
                } catch (FileNotFoundException e) {
                    UiUtils.makeText(getString(R.string.file_not_exist));
                    canUpload = false;
                } catch (IOException e) {
                    UiUtils.makeText(getString(R.string.read_file_error));
                    canUpload = false;
                }
            }else if (requestCode == TAKE_PHOTO){
                // 上传
                DialogUtil.dismissDialog(chooseImageDialog);
                avatar_path = Environment
                        .getExternalStorageDirectory() + File.separator + "image.jpg";
                photo = BitmapFactory.decodeFile(avatar_path);
                iv_avatar.setImageBitmap(ImageTools.zoomBitmap(photo, photo.getWidth() / 5, photo.getHeight() / 5));
                canUpload = photo != null;
            }
        }
    }


    /**
     * 上传图片
     * @param photo
     * @param path
     */
    private void uploadImage(final Bitmap photo, String path) {
        uploadDialog = UiUtils.showProgressDialog(this, "");
        ApiWrap.uploadImage(FileUtil.getFilePath(path), FileUtils.getFileName(path), new SimpleObserver<UploadImageResult>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog(uploadDialog);
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.e(TAG, "upload exception: " + e.getMessage());
            }

            @Override public void onNext(UploadImageResult uploadImageResult) {
                LogUtils.i(TAG, "upload result : " + uploadImageResult.toString());
                if (uploadImageResult.getCode() == 0){
                    // 上传成功获取url更新资料
                    String imageUrl = uploadImageResult.getData().getImgUrl();
                    if (TextUtils.isEmpty(imageUrl)){
                        UiUtils.dismissProgressDialog(uploadDialog);
                        UiUtils.makeText(getString(R.string.code_500));
                        return;
                    }
                    updateUserInfo(photo, imageUrl);
                }else {
                    UiUtils.dismissProgressDialog(uploadDialog);
                    UiUtils.makeText(getString(R.string.code_500));
                }
            }
        });
    }

    /**
     * 更新用户信息
     * @param photo
     * @param imageUrl
     */
    private void updateUserInfo(final Bitmap photo, String imageUrl) {
        Map<String, Object> map = new HashMap<>();
        int userId = (int) SPUtil.get(this, "userId", 1);
        map.put("userId", userId);
        map.put("faceImgUrl", imageUrl);
        String companyName = et_company_name.getText().toString().trim();
        if (!TextUtils.isEmpty(companyName)){
            map.put("userName", companyName);
        }
        LogUtils.i(TAG, userId + " " + imageUrl);
        ApiWrap.updateUserInfo(map, new SimpleObserver<Result>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog(uploadDialog);
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.e(TAG, "update user info exception: " + e.getMessage());
            }

            @Override public void onNext(Result result) {
                LogUtils.i(TAG, "update user info result: " + result.toString());
                UiUtils.makeText(result.getMsg());
                UiUtils.dismissProgressDialog(uploadDialog);
                if (result.getCode() == 0){
                    // 保存
                    SPUtil.put(ChangeInfoActivity.this, SharedPreferenceConstants.FACEIMGURL, result.getData().getFaceImgUrl());
                    SPUtil.put(ChangeInfoActivity.this, SharedPreferenceConstants.USERNAME, result.getData().getUserName());
                    // 通知mineFragment修改
                    EventBus.getDefault().post(new InfoChangedEvent(result.getData().getFaceImgUrl(), result.getData().getUserName()));
                }
            }
        });
    }
}
