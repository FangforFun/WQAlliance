package gkzxhn.wqalliance.mvp.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.ImageTools;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.ui.activity.ContactWayActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MyAddressActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MyOrderActivity;
import gkzxhn.wqalliance.mvp.ui.activity.SettingActivity;
import gkzxhn.wqalliance.mvp.ui.activity.SignActivity;
import gkzxhn.wqalliance.mvp.widget.CircleImageView;

/**
 * Created by 方 on 2017/3/3.
 */
public class MineFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MineFragment";
    private static final int CHOOSE_PHOTO = 1;

    @BindView(R.id.iv_avatar) CircleImageView iv_avatar; // 头像
    @BindView(R.id.tv_sign_status) TextView tv_sign_status; // 签约状态
    @BindView(R.id.tv_login_status) TextView tv_login_status;// 登录状态
    @BindView(R.id.ll_my_order) LinearLayout ll_my_order;// 我的订单
    @BindView(R.id.ll_my_address) LinearLayout ll_my_address; // 我的地址
    @BindView(R.id.ll_contact_info) LinearLayout ll_contact_info; // 联系方式
    @BindView(R.id.ll_sign) LinearLayout ll_sign;// 签约
    @BindView(R.id.ll_setting) LinearLayout ll_setting; // 设置

    private AlertDialog signDialog;
    private Bitmap photo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private AlertDialog chooseImageDialog;
    private ProgressDialog uploadDialog;

    @OnClick({R.id.ll_my_order, R.id.ll_my_address, R.id.ll_contact_info,
            R.id.ll_sign, R.id.ll_setting, R.id.iv_avatar})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_avatar:
                chooseImageDialog = UiUtils.showSingleChooseDialog(getActivity(), "请选择", new String[]{"图库", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.i(TAG, "checked position: " + which);
                        switch (which){
                            case 0:// 图库
                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
                                break;
                            case 1:// 相册

                                break;
                        }
                    }
                });
                break;
            case R.id.ll_my_order:
                UiUtils.startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.ll_my_address:
                UiUtils.startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
            case R.id.ll_contact_info:
                UiUtils.startActivity(new Intent(getActivity(), ContactWayActivity.class));
                break;
            case R.id.ll_sign:
                signDialog = DialogUtil.showSignDialog(getActivity(), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        UiUtils.makeText("线下");
                    }
                }, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        UiUtils.startActivity(new Intent(getActivity(), SignActivity.class));
                    }
                });
                break;
            case R.id.ll_setting:
                UiUtils.startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtil.dismissDialog(signDialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getActivity().getContentResolver();
        // 照片的原始资源地址
        Uri originalUri = data.getData();
        String path = originalUri.getPath();
        Log.i(TAG, "originalUri : " + path);
        // /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1488807352169.jpg
        try {// 使用ContentProvider通过URI获取原始图片
            photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
            DialogUtil.dismissDialog(chooseImageDialog);
            if (photo != null) {
                // 先上传  上传成功再更新数据  更新成功再显示在界面上
                uploadImage(path);
            }else {
                UiUtils.makeText(getString(R.string.read_file_error));
            }
        } catch (FileNotFoundException e) {
            UiUtils.makeText(getString(R.string.file_not_exist));
        } catch (IOException e) {
            UiUtils.makeText(getString(R.string.read_file_error));
        }
    }

    /**
     * 上传图片
     * @param path
     */
    private void uploadImage(String path) {
        uploadDialog = UiUtils.showProgressDialog(getActivity(), "");
        ApiWrap.uploadImage(getFilePath(path), getFileName(path), new SimpleObserver<UploadImageResult>(){
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
                    updateUserInfo(imageUrl);
                }else {
                    UiUtils.dismissProgressDialog(uploadDialog);
                    UiUtils.makeText(getString(R.string.code_500));
                }
            }
        });
    }

    /**
     * 获取图片所属文件夹路径
     * @param path
     * @return
     */
    private String getFilePath(String path) {
        if (path.contains("/")){
            return path.substring(0, path.lastIndexOf("/"));
        }
        return "";
    }

    /**
     * 更新用户信息
     * @param imageUrl
     */
    private void updateUserInfo(String imageUrl) {
        Map<String, Object> map = new HashMap<>();
        int userId = (int) SPUtil.get(getActivity(), "userId", 1);
        map.put("userId", userId);
        map.put("faceImgUrl", imageUrl);
        LogUtils.i(TAG, userId + " " + imageUrl);
        ApiWrap.updateUserInfo(map, new SimpleObserver<Result>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog(uploadDialog);
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.e(TAG, "update user info exception: " + e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                LogUtils.i(TAG, "update user info result: " + result.toString());
                UiUtils.makeText(result.getMsg());
                UiUtils.dismissProgressDialog(uploadDialog);
                if (result.getCode() == 0){
                    // 设置图片到界面头像上
                    if (photo != null)
                        iv_avatar.setImageBitmap(ImageTools.zoomBitmap(photo, photo.getWidth() / 5, photo.getHeight() / 5));
                    else
                        LogUtils.i(TAG, "bitmap is null");
                }
            }
        });
    }

    /**
     * 获取图片文件名
     * @param path
     * @return
     */
    private String getFileName(String path){
        if (path.contains("/")){
            int index = path.lastIndexOf("/");
            return path.substring(index + 1, path.length());
        }
        return "";
    }
}
