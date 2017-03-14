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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.AppComponent;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.FileUtil;
import gkzxhn.utils.ImageTools;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;


/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:签约页面activity
 */
public class SignActivity extends BaseContentActivity implements View.OnClickListener {

    private static final int CHOOSE_PHOTO_1 = 1;
    private static final int CHOOSE_PHOTO_2 = 2;
    private static final int TAKE_PHOTO_1 = 3;
    private static final int TAKE_PHOTO_2 = 4;
    private ImageView iv_upload_trademark;
    private ImageView iv_knowledge_right;
    private EditText et_company_name;
    private LinearLayout ll_sign_contract;

    private String companyName;  //企业名称
    private String trademarkImgUrl; //商标
    private String propertyImgUrl;  //知识产权

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_sign, null, false);
        iv_upload_trademark = (ImageView) view.findViewById(R.id.iv_upload_trademark);
        iv_knowledge_right = (ImageView) view.findViewById(R.id.iv_knowledge_right);
        et_company_name = (EditText) view.findViewById(R.id.et_company_name);
        ll_sign_contract = (LinearLayout) view.findViewById(R.id.ll_sign_contract);
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.sign));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void initData() {
        super.initData();
        iv_upload_trademark.setOnClickListener(this);
        iv_knowledge_right.setOnClickListener(this);
        ll_sign_contract.setOnClickListener(this);
    }

    public static final String TRADEMARKIMGURL = "trademarkImgUrl";
    public static final String PROPERTYIMGURL = "propertyImgUrl";
    public static final String COMPANYNAME = "companyName";

    private int type = 0;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_upload_trademark:
                type = 1;
                choosePhoto(type);
                break;
            case R.id.iv_knowledge_right:
                type = 2;
                choosePhoto(type);
                break;
            case R.id.ll_sign_contract:
                companyName = et_company_name.getText().toString().trim();
                UiUtils.makeText("签订合同");
                Intent intent = new Intent(this, SignContractActivity.class);
                intent.putExtra(TRADEMARKIMGURL, trademarkImgUrl);
                intent.putExtra(PROPERTYIMGURL, propertyImgUrl);
                intent.putExtra(COMPANYNAME, companyName);
                UiUtils.startActivity(this, intent);
                break;
        }
    }

    private AlertDialog chooseDialog;
    private ProgressDialog uploadDialog;

    /**
     * 选择照片来源
     */
    private void choosePhoto(final int type) {
        chooseDialog = UiUtils.showSingleChooseDialog(this, getString(R.string.please_choose), new String[]{"图库", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(TAG, "checked position: " + which);
                switch (which){
                    case 0:// 图库
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, type == 1 ? CHOOSE_PHOTO_1 : CHOOSE_PHOTO_2);
                        break;
                    case 1:// 相机
                        //检查权限
                        if (ContextCompat.checkSelfPermission(SignActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(SignActivity.this, new String[]{Manifest.permission.CAMERA},
                                    1);
                        }else {
                            openCamera(type);
                        }

                        break;
                }
            }
        });
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
                    openCamera(type);
                }
                break;
        }
    }

    /**
     * 调用相机
     * @param type
     */
    private void openCamera(int type) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, type == 1 ? TAKE_PHOTO_1 : TAKE_PHOTO_2);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CHOOSE_PHOTO_1 || requestCode == CHOOSE_PHOTO_2){
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
                        uploadImage(photo, path, requestCode);
                    }else {
                        UiUtils.makeText(getString(R.string.read_file_error));
                    }
                } catch (FileNotFoundException e) {
                    UiUtils.makeText(getString(R.string.file_not_exist));
                } catch (IOException e) {
                    UiUtils.makeText(getString(R.string.read_file_error));
                }
            }else if (requestCode == TAKE_PHOTO_1 || requestCode == TAKE_PHOTO_2){
                // 上传
                DialogUtil.dismissDialog(chooseDialog);
                String path = Environment
                        .getExternalStorageDirectory() + File.separator + "image.jpg";
                uploadImage(BitmapFactory.decodeFile(path), path, requestCode);
            }
        }
    }

    /**
     * 上传图片
     * @param photo
     * @param path
     * @param requestCode
     */
    private void uploadImage(final Bitmap photo, final String path, final int requestCode) {
        uploadDialog = UiUtils.showProgressDialog(this, "");
        ApiWrap.uploadImage(FileUtil.getFilePath(path), FileUtils.getFileName(path), new SimpleObserver<UploadImageResult>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog(uploadDialog);
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.e(TAG, "upload exception: " + e.getMessage());
            }

            @Override public void onNext(UploadImageResult uploadImageResult) {
                LogUtils.i(TAG, "upload result : " + uploadImageResult.toString());
                UiUtils.dismissProgressDialog(uploadDialog);
                if (uploadImageResult.getCode() == 0){
                    if (photo != null) {
                        if (requestCode == CHOOSE_PHOTO_1 || requestCode == TAKE_PHOTO_1) {
                            trademarkImgUrl = uploadImageResult.getData().getImgUrl();
                            iv_upload_trademark.setImageBitmap(ImageTools.zoomBitmap(photo, photo.getWidth() / 5, photo.getHeight() / 5));
                        }else {
                            propertyImgUrl = uploadImageResult.getData().getImgUrl();
                            iv_knowledge_right.setImageBitmap(ImageTools.zoomBitmap(photo, photo.getWidth() / 5, photo.getHeight() / 5));
                        }
                        LogUtils.i(TAG, trademarkImgUrl + "----------------" + propertyImgUrl);
                    } else {
                        LogUtils.i(TAG, "bitmap is null");
                    }
                }else {
                    UiUtils.dismissProgressDialog(uploadDialog);
                    UiUtils.makeText(getString(R.string.code_500));
                }
            }
        });
    }
}
