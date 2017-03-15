package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.jess.arms.utils.UiUtils;

import java.util.List;

import common.AppComponent;
import common.SuperApplication;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.OrderEvidence;
import gkzxhn.wqalliance.mvp.model.entities.Result;

/**
 * Created by 方 on 2017/3/6.
 */

public class ProtectionActivity extends BaseContentActivity implements View.OnClickListener {
    private EditText mTheme;//主题
    private RelativeLayout mUploadEd;//上传证据
    private EditText mDesc;//案件详情
    private TextView mCommit;//提交按钮
    private ProgressDialog mProgressDialog;
    private AlertDialog mDialog;

    private int code = -1; //网络错误

    private View mContentView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText("我要维权");
        mTvSubtitle.setVisibility(View.GONE);

        int userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, 0);
        getSignStatusFromNet(userId);
    }

    /**
     * 从网络获取签约状态
     * @param userId
     */
    private void getSignStatusFromNet(int userId) {
        ApiWrap.getUser(userId, new SimpleObserver<Result>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(Result result) {
                super.onNext(result);
                LogUtils.i(TAG, result.toString());
                int signedStatus = result.getData().getSignedStatus();
                SPUtil.put(ProtectionActivity.this, SharedPreferenceConstants.SIGNEDSTATUS, signedStatus);
            }
        });

    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(this).inflate(R.layout.fragment_protection, null, false);
        mTheme = (EditText) mContentView.findViewById(R.id.et_theme);
        mUploadEd = (RelativeLayout) mContentView.findViewById(R.id.rl_upload_ed);
        mDesc = (EditText) mContentView.findViewById(R.id.desc);
        mCommit = (TextView) mContentView.findViewById(R.id.tv_commit);

        mUploadEd.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        return mContentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_upload_ed:
                startActivity(new Intent(this, UploadEdActivity.class));
                break;
            case R.id.tv_commit:
                commitCase();
                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_checkout:
                UiUtils.makeText("查看详情");
                this.startActivity(new Intent(this, MyOrderActivity.class));
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
        }
    }

    /**
     * 提交案件
     */
    public void commitCase() {
        if (isSigned()) {

            int userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, 0);
            String title = mTheme.getText().toString().trim();
            String description = mDesc.getText().toString().trim();
            List<OrderEvidence> orderEvidences = SuperApplication.getOrderEvidences();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || orderEvidences.size() == 0) {
                UiUtils.makeText("请完善信息");
                return;
            }
            mProgressDialog = UiUtils.showProgressDialog(this);

            String orderEvidenceJs = new Gson().toJson(orderEvidences);
            Log.i(TAG, "onClick: orderEvidenceJs       " + orderEvidenceJs);
            Log.i(TAG, "onClick: userId       " + userId);
            Log.i(TAG, "onClick: title       " + title);
            Log.i(TAG, "onClick: description       " + description);
//            CommonService.addOrder(userId, title, description, orderEvidenceJs);
            ApiWrap.addOrder(userId, title, description, orderEvidenceJs, new SimpleObserver<Result>(){
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    code = -1;
                    UiUtils.makeText("网络错误");
                    if (mProgressDialog != null) {
                        UiUtils.dismissProgressDialog(mProgressDialog);
                    }
                }

                @Override
                public void onNext(Result result) {
                    super.onNext(result);
                    code = result.getCode();
                    UiUtils.makeText("提交成功");
                    if (mProgressDialog != null) {
                        UiUtils.dismissProgressDialog(mProgressDialog);
                    }
                    showSuccessPopupWindow();
                    clearContent();
                }
            });
        }else {
            mDialog = DialogUtil.showUnSignedDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog(mDialog);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog(mDialog);
                    startActivity(new Intent(ProtectionActivity.this, SignActivity.class));
                }
            });
        }
        return;
    }

    /**
     * 是否已签约
     * @return
     */
    private boolean isSigned() {
        if (((int)SPUtil.get(this, SharedPreferenceConstants.SIGNEDSTATUS, 0))==3) {
            return true;
        }
        return false;
    }

    private PopupWindow mPopupWindow;
    private TextView mTv_checkout;


    /**
     * 弹出提交操作成功的popupwindow
     */
    public void showSuccessPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_success, null, false);

        mTv_checkout = (TextView) view.findViewById(R.id.tv_checkout);

        mTv_checkout.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        mPopupWindow.setTouchable(true);

//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Log.i("mengdd", "onTouch : ");
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popupwindow_background));

        //设置背景透明度
        backgroundAlpha(0.3f);

        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        // 设置好参数之后再show
        mPopupWindow.showAtLocation(mContentView, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dip2px(20));
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 提交成功后,清除提交内容
     */
    public void clearContent() {
        mTheme.setText("");
        mDesc.setText("");
        SuperApplication.getOrderEvidences().clear();
    }
}
