package gkzxhn.wqalliance.mvp.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import common.SuperApplication;
import gkzxhn.utils.FileUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.Constants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.model.entities.OrderEvidence;
import gkzxhn.wqalliance.mvp.model.entities.UploadImageResult;
import gkzxhn.wqalliance.mvp.ui.activity.EdActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.jess.arms.utils.UiUtils.getString;

/**
 * Created by 方 on 2017/3/9.
 */

public class EvidenceListAdapter extends RecyclerView.Adapter{

    private static final String TAG = "EvidenceListAdapter";
    private static final int TYPE_CHOOSEPHOTO = 0;//图库
    private static final int TYPE_TAKEPHOTO = 1;//相机
    private final List<EvidenceList.DataBean> mData; //请求到服务器的data数据
    private final Activity mActivity;

    private HashMap<Integer, Bitmap> mBitmaps = new HashMap<>(); //上传图片集合
    private RxPermissions mRxPermissions;
    private RxErrorHandler mErrorHandler;

    public EvidenceListAdapter(List<EvidenceList.DataBean> data, Activity activity) {
        mActivity = activity;
        mData = data;
        for (int i = 0; i < mData.size(); i++) {
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvidenceItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_evidence_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        EvidenceItemViewHolder viewHolder = (EvidenceItemViewHolder) holder;
        viewHolder.mTv_evidence_name.setText(mData.get(position).evidenceName);
        Log.i(TAG, "onBindViewHolder: EvidenceList.mData-----" + mData);
        int id = mData.get(position).id;

        List<OrderEvidence> orderEvidences = SuperApplication.getOrderEvidences();

        //从已传证据里面加载图片
        for (OrderEvidence orderEvidence : orderEvidences) {
            if (id == orderEvidence.evidenceId) {
                Log.i(TAG, "onBindViewHolder: onVindViewHodler_imgUrl---- " + position + " :  " + orderEvidence.imgUrl);
                Glide.with(mActivity)
                        .load(orderEvidence.imgUrl)
                        .error(R.drawable.avatar_def)
                        .override(640, 480)
                        .into(viewHolder.mIv_evidence);
                viewHolder.mIv_evidence.setVisibility(View.VISIBLE);
                viewHolder.upload_ed.setVisibility(View.GONE);
            }
        }

//        if (mBitmaps.containsKey(position)&&mBitmaps.get(position) != null) {
//            viewHolder.mIv_evidence.setImageBitmap(mBitmaps.get(position));
//            viewHolder.mIv_evidence.setVisibility(View.VISIBLE);
//            viewHolder.upload_ed.setVisibility(View.GONE);
//        }
        
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UiUtils.makeText(position+"");
                //TODO ...上传照片
                addStoragePermission(position);
            }
        });
    }

    private AlertDialog chooseDialog;

    public AlertDialog getChooseDialog() {
        return chooseDialog;
    }

    /**
     * 选择照片
     * @param position
     */
    private void choosePhoto(final int position) {
        chooseDialog = UiUtils.showSingleChooseDialog(mActivity, getString(R.string.please_choose), new String[]{"图库", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(TAG, "checked position: " + which);
                ((EdActivity)mActivity).setClickPosition(position);
                switch (which){
                    case 0:// 图库
                        openAlbum(position);
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
                        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA},
                                    1);
                        }else {
                            takePhoto(position);
                        }
                        break;
                }
            }
        });
    }

    /**
     * 打开相册
     * @param position
     */
    public void openAlbum(int position) {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        mActivity.startActivityForResult(openAlbumIntent, TYPE_CHOOSEPHOTO * 1000 + position);
    }

    /**
     * 检查存储权限
     */
    private void addStoragePermission(int position) {
        //检查权限
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        else if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }else {
            choosePhoto(position);
        }
    }

    /**
     * 从相机拍照获得照片
     * @param position
     */
    public void takePhoto(int position) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tmpFile = new File(Constants.SD_FILE_CACHE_PATH);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        String fileName = String.valueOf(System.currentTimeMillis()) + "evidence.jpg";
        ((EdActivity)mActivity).setFileName(fileName);
        File file = new File(Constants.SD_FILE_CACHE_PATH, fileName);

        Uri imageUri = Uri.fromFile(file);

        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mActivity.startActivityForResult(openCameraIntent, TYPE_TAKEPHOTO * 1000 + position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private ProgressDialog uploadDialog;

    /**
     * 更新图片UI并上传
     * @param photo
     * @param path
     * @param position
     */
    public void uploadImage(final Bitmap photo, String path, final int position) {
        uploadDialog = UiUtils.showProgressDialog(mActivity, "");
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
                        List<OrderEvidence> orderEvidences = SuperApplication.getOrderEvidences();
                        int id = mData.get(position).id;
                        String imgUrl = uploadImageResult.getData().getImgUrl();
                        for (OrderEvidence orderEvidence : orderEvidences) {
                            if (orderEvidence.evidenceId == id) {
                                orderEvidence.imgUrl = imgUrl;
                                notifyDataSetChanged();
                                return;
                            }
                        }
                        OrderEvidence orderEvidence = new OrderEvidence();
                        orderEvidence.evidenceId = id;
                        orderEvidence.imgUrl = imgUrl;
                        orderEvidences.add(orderEvidence);

//                        mBitmaps.put(position, ImageTools.zoomBitmap(photo, photo.getWidth()/2, photo.getHeight()/2));
                        notifyDataSetChanged();
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

    private class EvidenceItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView upload_ed;
        private final TextView mTv_evidence_name;
        private final ImageView mIv_evidence;

        public EvidenceItemViewHolder(View itemView) {
            super(itemView);
            upload_ed = (ImageView) itemView.findViewById(R.id.iv_upload_ed);
            mIv_evidence = (ImageView) itemView.findViewById(R.id.iv_evidence);
            mTv_evidence_name = (TextView) itemView.findViewById(R.id.tv_evidence_name);
        }
    }
}
