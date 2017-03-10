package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.utils.LogUtils;
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
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:修改密码activity
 */
public class ChangePasswordActivity extends BaseContentActivity {

    private EditText et_old_pwd;
    private EditText et_new_pwd;
    private EditText et_confirm_pwd;

    @Override protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.change_pwd));
        mTvSubtitle.setText(getString(R.string.save));
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_change_password, null, false);
        et_old_pwd = (EditText) view.findViewById(R.id.et_old_pwd);
        et_new_pwd = (EditText) view.findViewById(R.id.et_new_pwd);
        et_confirm_pwd = (EditText) view.findViewById(R.id.et_confirm_pwd);
        return view;
    }

    private ProgressDialog updatePasswordDialog;

    @Override
    protected void doSubtitle() {
        super.doSubtitle();
        String old = et_old_pwd.getText().toString().trim();
        String newPwd = et_new_pwd.getText().toString().trim();
        String newPwd2 = et_confirm_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(old) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwd2)){
            UiUtils.makeText(getString(R.string.can_not_be_empty));
            return;
        }
        if (!newPwd.equals(newPwd2)){
            UiUtils.makeText(getString(R.string.pwd_not_same));
            return;
        }
        updatePasswordDialog = UiUtils.showProgressDialog(this, getString(R.string.committing));
        if (NetworkUtils.isConnected()) {
            ApiWrap.updatePassword((String)(SPUtil.get(this, SharedPreferenceConstants.PHONE, "")),old, newPwd, new SimpleObserver<Result>() {
                @Override public void onError(Throwable e) {
                    UiUtils.dismissProgressDialog(updatePasswordDialog);
                    UiUtils.makeText(getString(R.string.timeout_retry));
                    LogUtils.e(TAG, "update password exception: " + e.getMessage());
                }

                @Override public void onNext(Result result) {
                    LogUtils.i(TAG, "update password result: " + result.toString());
                    UiUtils.dismissProgressDialog(updatePasswordDialog);
                    UiUtils.makeText(result.getMsg());
                    if (result.getCode() == 0){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ChangePasswordActivity.this.finish();
                            }
                        }, 500);
                    }
                }
            });
        }else {
            UiUtils.dismissProgressDialog(updatePasswordDialog);
            UiUtils.makeText(getString(R.string.net_broken));
        }
    }
}
