package gkzxhn.wqalliance.mvp.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.AppComponent;
import gkzxhn.utils.BitmapUtils;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.FileUtil;
import gkzxhn.utils.baiduservice.LocationService;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.Constants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Code;
import gkzxhn.wqalliance.mvp.model.entities.ToHomeEvent;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.ui.activity.listener.MyLocationListener;

/**
 * Created by 方 on 2017/4/28.
 */

public class FightFakeActivity extends BaseContentActivity implements View.OnClickListener {

    private EditText mEt_goods_name;    //商品名称
    private RelativeLayout rl_upload_ed;    //上传图片
    private EditText mEt_addr;          //填写地址
    private RelativeLayout mRl_location;//手动定位
    private TextView mTv_commit;        //提交打假
    private String mFileName;
    private MyLocationListener mListener;
    private ImageView mIv_upload_ed;  //上传图片的icon
    private ImageView mIv_photo;        //已上传图片预览
    private String mImgUrl;
    private String mGoods_name;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mGoods_name = getIntent().getStringExtra("goods_name");
        mTvTitle.setText(UiUtils.getString(R.string.fight_fake));
        mTvSubtitle.setVisibility(View.GONE);
    }

    /**
     * 申请定位权限
     */
    private void addLocationPermission() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请定位LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        }
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请定位LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }
        else {
            startLocation();
        }
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_fight_fake, null, false);
        mEt_goods_name = (EditText)view.findViewById(R.id.et_goods_name);
        rl_upload_ed = (RelativeLayout)view.findViewById(R.id.rl_upload_ed);
        mEt_addr = (EditText)view.findViewById(R.id.et_addr);
        mRl_location = (RelativeLayout)view.findViewById(R.id.rl_location);
        mTv_commit = (TextView)view.findViewById(R.id.tv_commit);
        mIv_upload_ed = (ImageView)view.findViewById(R.id.iv_upload_ed);
        mIv_photo = (ImageView)view.findViewById(R.id.iv_photo);
        addLocationPermission();

        if (!TextUtils.isEmpty(mGoods_name)) {
            mEt_goods_name.setText(mGoods_name);
        }
        rl_upload_ed.setOnClickListener(this);
        mRl_location.setOnClickListener(this);
        mTv_commit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_upload_ed:
                addStoragePermission();
                break;
            case R.id.rl_location:
                //手动定位
                addLocationPermission();
                break;
            case R.id.tv_commit:
                //提交打假
                String goodsName = mEt_goods_name.getText().toString().trim();
                if (TextUtils.isEmpty(goodsName)) {
                    UiUtils.makeText("商品名不能为空");
                    return;
                }
                if (mImgUrl == null) {
                    UiUtils.makeText("请上传图片");
                    return;
                }
                String address = mEt_addr.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    UiUtils.makeText("请填写地址信息");
                    return;
                }
                ApiWrap.fightFake(goodsName, mImgUrl, address, new SimpleObserver<Code>(){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        UiUtils.makeText(UiUtils.getString(R.string.net_broken));
                    }

                    @Override
                    public void onNext(Code code) {
                        super.onNext(code);
                        if (code.code == 0) {
                            UiUtils.makeText("提交成功");
                            EventBus.getDefault().post(new ToHomeEvent());
                            finish();
                        }
                    }
                });
                break;
        }
    }

    private AlertDialog chooseDialog;
    private void choosePhoto() {
        chooseDialog = UiUtils.showSingleChooseDialog(this, getString(R.string.please_choose), new String[]{"图库", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(TAG, "checked position: " + which);
                switch (which){
                    case 0:// 图库
                        openAlbum();
                        break;
                    case 1:// 相机
                        //TODO ...
//                        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
//                            @Override
//                            public void onRequestPermissionSuccess() {
//                                takePhoto(position);//请求权限成功后做一些操作
//                            }
//                        }, mRxPermissions, mErrorHandler);

                        //检查权限
                        if (ContextCompat.checkSelfPermission(FightFakeActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(FightFakeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        }else {
                            takePhoto();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 1);
    }

    /**
     * 从相机拍照获得照片
     */
    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tmpFile = new File(Constants.SD_FILE_CACHE_PATH);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        mFileName = String.valueOf(System.currentTimeMillis()) + "fight_fake.jpg";
        File file = new File(Constants.SD_FILE_CACHE_PATH, mFileName);

        Uri imageUri = Uri.fromFile(file);

        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: resultCode    " + resultCode);

        if (resultCode == -1) {
            if (requestCode == 1) {
                //图库
                ContentResolver resolver = getContentResolver();
                Uri originalUri = data.getData();
                String path = FileUtil.getPath(this, originalUri);
                Log.i(TAG, "originalUri : " + path);
                // /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1488807352169.jpg
                // raw//storage/emulated/0/DCIM/Camera/IMG_20170309_080815.jpg
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                    photo = BitmapUtils.getSmallBitmap(path);

                    try {
                        //图片压缩存储
                        String fileName = String.valueOf(System.currentTimeMillis() + "evidence.jpg");
                        File avatarFile = BitmapUtils.saveFile(photo, Constants.SD_FILE_CACHE_PATH, fileName);
                        path = Constants.SD_FILE_CACHE_PATH + File.separator + fileName;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DialogUtil.dismissDialog(chooseDialog);
                    if (photo != null) {
                        // 先上传  上传成功再更新数据  更新成功再显示在界面上
                        uploadImage(photo, path);
                    } else {
                        UiUtils.makeText(getString(R.string.read_file_error));
                    }
                } catch (FileNotFoundException e) {
                    UiUtils.makeText(getString(R.string.file_not_exist));
                } catch (IOException e) {
                    UiUtils.makeText(getString(R.string.read_file_error));
                }
            } else {
                //相机
                DialogUtil.dismissDialog(chooseDialog);
                if (mFileName != null) {
                    String path = Constants.SD_FILE_CACHE_PATH + File.separator + mFileName;
                    Log.i(TAG, "onActivityResult: path = " + path);
                    Bitmap photo = BitmapUtils.getSmallBitmap(path);
                    try {
                        //图片压缩存储
//                        String fileName = String.valueOf(System.currentTimeMillis() + "evidence.jpg");
                        File avatarFile = BitmapUtils.saveFile(photo, Constants.SD_FILE_CACHE_PATH, mFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uploadImage(photo, path);
                }
            }
        }
    }

    private ProgressDialog uploadDialog;
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
                UiUtils.dismissProgressDialog(uploadDialog);
                if (uploadImageResult.getCode() == 0){
                    if (photo != null) {
                        mImgUrl = uploadImageResult.getData().getImgUrl();
                        Log.i(TAG, "onNext: imgUrl: " + mImgUrl);
                        mIv_upload_ed.setVisibility(View.GONE);
                        mIv_photo.setVisibility(View.VISIBLE);
                        mIv_photo.setImageBitmap(photo);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                Log.i(TAG, "onRequestPermissionsResult: camera--------");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //执行后续的操作
                    Toast.makeText(this, "相机已经授权成功了", Toast.LENGTH_SHORT).show();
                    // TODO: 2016/11/4
                    takePhoto();
                }
                break;
            case 2:
                Log.i(TAG, "onRequestPermissionsResult: storage--------");
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //执行后续的操作
                    // TODO: 2016/11/4
                    choosePhoto();
                }
                break;
            case 3:
                Log.i(TAG, "onRequestPermissionsResult: location--------");
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    //执行后续的操作
                    // TODO: 2016/11/4
                    startLocation();
                }
                break;
        }
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        mEt_addr.setText("正在定位...");
        mListener = new MyLocationListener(this);
        LocationService.getInstance().start();
        LocationService.getInstance().registerListener(mListener);
    }

    /**
     * 检查存储权限
     */
    private void addStoragePermission() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }else {
            choosePhoto();
        }
    }

    /**
     * 更新地址显示
     * @param addrStr
     */
    public void updateAddr(final String addrStr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEt_addr.setText(addrStr);
            }
        });
    }
}
