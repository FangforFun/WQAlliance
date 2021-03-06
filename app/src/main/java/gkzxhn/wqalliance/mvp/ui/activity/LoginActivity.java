package gkzxhn.wqalliance.mvp.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
//        mEtAccount.setText("18163657553");
//        mEtPassword.setText("123456");
        //检查权限
        addPermission();
    }

    private void addPermission() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
                            //是否开启新消息提醒
                            boolean notification = (boolean) SPUtil.get(LoginActivity.this, SharedPreferenceConstants.NOTIFICATION, true);
                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.NOTIFICATION, notification);

                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.USERID, result.getData().getId());
                            if (result.getData().getFaceImgUrl() != null) {
                              SPUtil.put(LoginActivity.this, SharedPreferenceConstants.FACEIMGURL, result.getData().getFaceImgUrl());
                            }
                            int signedStatus = result.getData().getSignedStatus();
                            Log.i(TAG, "onNext: login-------singedStatus   :" + signedStatus);
                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.SIGNEDSTATUS, signedStatus);
                            SPUtil.put(LoginActivity.this, SharedPreferenceConstants.PHONE, result.getData().getPhone());

                            String address = result.getData().getAddress();
                            if (address != null) {
                                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.ADDRESS, address);
                            }

                            String contactNumber = result.getData().getContactNumber();
                            if (contactNumber != null) {
                                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.CONTACTNUMBER, contactNumber);
                            }

                            String email = result.getData().getAddress();
                            if (email != null) {
                                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.EMAIL, email);
                            }

                            String userName = result.getData().getUserName();
                            if (userName != null) {
                                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.USERNAME, userName);
                            }

                            String yxAccess = result.getData().getYxAccess();
                            String yxToken = result.getData().getYxToken();
                            if (TextUtils.isEmpty(yxAccess)) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                return;
                            }
                            loginNim(yxAccess, yxToken);
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
    private void loginNim(final String account, final String pwd) {
        NimController.login(account, pwd, new RequestCallback<LoginInfo>() {
            @Override public void onSuccess(LoginInfo param) {
                UiUtils.dismissProgressDialog(loginDialog);
                NimUIKit.setAccount(account);
                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.USER_YXACCESS, account);
                SPUtil.put(LoginActivity.this, SharedPreferenceConstants.USER_YXTOKEN, pwd);
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
