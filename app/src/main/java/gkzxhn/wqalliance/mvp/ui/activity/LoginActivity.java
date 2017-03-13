package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.jess.arms.utils.UiUtils;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

import butterknife.BindView;
import butterknife.OnClick;
import common.AppComponent;
import common.SuperActivity;
import common.im.NimController;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;

/**
 * Created by 方 on 2017/3/2.
 */

public class LoginActivity extends SuperActivity {

    @BindView(R.id.et_account) EditText mEtAccount;
    @BindView(R.id.et_password) EditText mEtPassword;
    @BindView(R.id.tv_forget_psw) TextView mTvForgetPsw;
    @BindView(R.id.btn_login) RelativeLayout mBtnLogin;
    @BindView(R.id.btn_register) RelativeLayout mBtnRegister;
    @BindView(R.id.activity_splash) LinearLayout mActivitySplash;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        mEtAccount.setText("18774810958");
        mEtPassword.setText("123456");
    }

    @OnClick({R.id.tv_forget_psw, R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_psw:
                UiUtils.startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.btn_login:
                login();// 登录
                break;
            case R.id.btn_register:
                Log.i(TAG, "onClick: register");
                register();
                break;
        }
    }

    private ProgressDialog loginDialog;

    @Override
    protected void onDestroy() {
        UiUtils.dismissProgressDialog(loginDialog);
        super.onDestroy();
    }

    /**
     * 登录操作
     */
    private void login() {
        final String account = mEtAccount.getText().toString().trim();
        final String pwd = mEtPassword.getText().toString().trim();
        if(EmptyUtils.isNotEmpty(account) && EmptyUtils.isNotEmpty(pwd)){
            // 网络判断
            if (NetworkUtils.isConnected()) {
                loginDialog  = UiUtils.showProgressDialog(this, getString(R.string.login_ing));
                ApiWrap.login(account, pwd, new SimpleObserver<Result>(){
                    @Override public void onError(Throwable e) {
                        UiUtils.dismissProgressDialog(loginDialog);
                        UiUtils.makeText(getString(R.string.login_timeout));
                        LogUtils.e(TAG, "login request exception: " + e.getMessage());
                    }

                    @Override public void onNext(Result result) {
                        // 登录成功之后清除之前存的数据
                        SPUtil.clear(LoginActivity.this);
                        LogUtils.i(TAG, "login request result: " + result.toString());
                        if (result.getCode() == 0){
                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.USERID, result.getData().getId());
                            if (result.getData().getFaceImgUrl() != null) {
                              SPUtil.put(LoginActivity.this, SharedPreferenceConstants.FACEIMGURL, result.getData().getFaceImgUrl());
                            }
                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.PHONE, result.getData().getPhone());
                            loginNim(result.getData().getYxAccess(), result.getData().getYxToken());
                        }else{
                            UiUtils.makeText(result.getMsg());
                            UiUtils.dismissProgressDialog(loginDialog);
                        }
                    }
                });
            }else {
                UiUtils.makeText(getString(R.string.network_unavailable));
            }
        }else {
            UiUtils.makeText(getString(R.string.empty_acc_pwd));
        }
    }

    /**
     * 登录云信
     * @param account
     * @param pwd
     */
    private void loginNim(final String account, String pwd) {
        NimController.login("gkzxhn001", "123456", new RequestCallback<LoginInfo>() {
            @Override public void onSuccess(LoginInfo param) {
                UiUtils.dismissProgressDialog(loginDialog);
                NimUIKit.setAccount("gkzxhn001");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }

            @Override public void onFailed(int code) {
                UiUtils.dismissProgressDialog(loginDialog);
                NimController.checkLoginResultCode(code);
            }

            @Override public void onException(Throwable exception) {
                LogUtils.e(TAG, "login nim exception: " + exception.getMessage());
                UiUtils.dismissProgressDialog(loginDialog);
                UiUtils.makeText(getString(R.string.login_exception));
            }
        });
    }

    /**
     * 跳转注册
     */
    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
