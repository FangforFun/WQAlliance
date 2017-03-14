package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.util.HashMap;
import java.util.Map;

import common.AppComponent;
import gkzxhn.utils.SPUtil;
import gkzxhn.utils.Utils;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:我的地址页面activity
 */
public class MyAddressActivity extends BaseContentActivity {

    private EditText et_my_addr;
    private TextView mTv_my_addr;

    private boolean hadAddr = false; //默认没有地址

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_my_address, null, false);
        et_my_addr = (EditText) view.findViewById(R.id.et_my_addr);
        mTv_my_addr = (TextView) view.findViewById(R.id.tv_my_addr);

        String address = (String) SPUtil.get(this, SharedPreferenceConstants.ADDRESS, "");
        if (TextUtils.isEmpty(address)) {
            //没有地址显示编辑框
            et_my_addr.setVisibility(View.VISIBLE);
            mTv_my_addr.setVisibility(View.GONE);
            hadAddr = false;
        } else {
            //有地址,显示地址
            et_my_addr.setVisibility(View.GONE);
            mTv_my_addr.setVisibility(View.VISIBLE);
            mTv_my_addr.setText(address);
            mTvSubtitle.setText("编辑");
            hadAddr = true;
        }

       /* int userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, 0);
        ApiWrap.getUser(userId, new SimpleObserver<Result>() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: getUser" + e.getMessage());
                super.onError(e);
            }

            @Override
            public void onNext(Result result) {
                super.onNext(result);
                String address = result.getData().getAddress();
                if (TextUtils.isEmpty(address)) {
                    //没有地址显示编辑框
                    et_my_addr.setVisibility(View.VISIBLE);
                    mTv_my_addr.setVisibility(View.GONE);
                    hadAddr = false;
                } else {
                    //有地址,显示地址
                    et_my_addr.setVisibility(View.GONE);
                    mTv_my_addr.setVisibility(View.VISIBLE);
                    mTv_my_addr.setText(result.getData().getAddress());
                    mTvSubtitle.setText("编辑");
                    hadAddr = true;
                }
            }
        });*/
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.my_address));
        mTvSubtitle.setText(R.string.save);
    }

    private ProgressDialog updateDialog;

    @Override
    protected void doSubtitle() {
        if (hadAddr) {
            //编辑
            et_my_addr.setVisibility(View.VISIBLE);
            mTv_my_addr.setVisibility(View.GONE);
            mTvSubtitle.setText(UiUtils.getString(R.string.save));
            hadAddr = false;
        } else {
            //保存
            String address = et_my_addr.getText().toString().trim();
            if (!TextUtils.isEmpty(address)) {
                if (Utils.isAvailableByPing()) {
                    updateDialog = UiUtils.showProgressDialog(this);
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", SPUtil.get(this, SharedPreferenceConstants.USERID, 1));
                    map.put("address", address);
                    ApiWrap.updateUserInfo(map, new SimpleObserver<Result>() {
                        @Override
                        public void onError(Throwable e) {
                            UiUtils.makeText(getString(R.string.timeout_retry));
                            UiUtils.dismissProgressDialog(updateDialog);
                            LogUtils.i(TAG, "update user info failed: " + e.getMessage());
                        }

                        @Override
                        public void onNext(Result result) {
                            LogUtils.i(TAG, "result: " + result.toString());
                            UiUtils.dismissProgressDialog(updateDialog);
                            UiUtils.makeText(result.getMsg());
                            if (result.getCode() == 0) {
                                hadAddr = true;
                                mTvSubtitle.setText("编辑");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyAddressActivity.this.finish();
                                    }
                                }, 500);
                            }
                        }
                    });
                } else {
                    UiUtils.makeText(getString(R.string.net_broken));
                }
            }else {
                UiUtils.makeText("请输入您的地址");
            }
        }
    }

    @Override
    protected void onDestroy() {
        UiUtils.dismissProgressDialog(updateDialog);
        super.onDestroy();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
