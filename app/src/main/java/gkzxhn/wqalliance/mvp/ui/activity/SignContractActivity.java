package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;

/**
 * Created by 方 on 2017/3/9.
 */

public class SignContractActivity extends BaseContentActivity implements View.OnClickListener {
    /**
     * 是否已阅读
     */
    boolean isChecked = false;

    private TextView mTv_sign_contract;
    private ImageView mIv_read_checkbox;

    private String companyName;
    private String propertyImgUrl;  //知识产权
    private String trademarkImgUrl;  //商标
    private int userId;

    private ProgressDialog loginDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        companyName = getIntent().getStringExtra(SignActivity.COMPANYNAME);
        propertyImgUrl = getIntent().getStringExtra(SignActivity.PROPERTYIMGURL);
        trademarkImgUrl = getIntent().getStringExtra(SignActivity.TRADEMARKIMGURL);
        mTvTitle.setText("签订合同");
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_sign_contract, null, false);
        mIv_read_checkbox = (ImageView) contentView.findViewById(R.id.iv_read_checkbox);
        mTv_sign_contract = (TextView) contentView.findViewById(R.id.tv_sign_contract);

        mIv_read_checkbox.setOnClickListener(this);
        mTv_sign_contract.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_read_checkbox:
                if (isChecked) {
                    mIv_read_checkbox.setImageResource(R.drawable.unread);
                    isChecked = false;
                } else {
                    mIv_read_checkbox.setImageResource(R.drawable.read);
                    isChecked = true;
                }
                break;
            case R.id.tv_sign_contract:
                Log.i(TAG, "onClick: userId" + userId);
                Log.i(TAG, "onClick: companyName" + companyName);
                Log.i(TAG, "onClick: propertyImgUrl" + propertyImgUrl);
                Log.i(TAG, "onClick: trademarkImgUrl" + trademarkImgUrl);

                submitUserSign();
                break;
            case R.id.tv_subtitle:
                finish();
                break;
        }
    }

    /**
     * 确认签约
     */
    private void submitUserSign() {
        if (!isChecked) {
            UiUtils.makeText("请阅读合同内容并勾选");
            return;
        }

        //TODO ... 提交签约
        UiUtils.makeText("签约");
        if (TextUtils.isEmpty(companyName) || TextUtils.isEmpty(propertyImgUrl) || TextUtils.isEmpty(trademarkImgUrl)) {
            UiUtils.makeText("资料未提交完全");
            return;
        }
        userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, 0);

        if (NetworkUtils.isConnected()) {
            loginDialog = UiUtils.showProgressDialog(this, getString(R.string.uploading));
            ApiWrap.submitUserSign(userId, companyName, trademarkImgUrl, propertyImgUrl, new SimpleObserver<Result>() {
                @Override
                public void onNext(Result result) {
                    UiUtils.dismissProgressDialog(loginDialog);
                    super.onNext(result);
                    if (result.getCode() == 0) {
                        UiUtils.makeText("签约成功");
                        mTv_sign_contract.setText("签约成功");
                        mTv_sign_contract.setClickable(false);
                    }else {
                        UiUtils.makeText("签约失败, 错误码: " + result.getCode());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    UiUtils.dismissProgressDialog(loginDialog);
                    UiUtils.makeText("服务未响应");
                    Log.i(TAG, "onError: submitUserSign" + e.getMessage());
                    super.onError(e);
                }
            });
        } else {
            UiUtils.makeText("网络连接不可用");
        }
        return;
    }
}
