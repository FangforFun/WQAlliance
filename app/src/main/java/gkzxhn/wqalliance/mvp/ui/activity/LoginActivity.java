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
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/2.
 */

public class LoginActivity extends SuperActivity {
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_forget_psw)
    TextView mTvForgetPsw;
    @BindView(R.id.btn_login)
    RelativeLayout mBtnLogin;
    @BindView(R.id.btn_register)
    RelativeLayout mBtnRegister;
    @BindView(R.id.activity_splash)
    LinearLayout mActivitySplash;

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
        mEtAccount.setText("gkzxhn001");
        mEtPassword.setText("123456");
    }

    @OnClick({R.id.tv_forget_psw, R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_psw:
                UiUtils.makeText("忘记密码");
                break;
            case R.id.btn_login:
                UiUtils.makeText("login");
                Log.i(TAG, "onClick: login");
                login();
                break;
            case R.id.btn_register:
                Log.i(TAG, "onClick: register");
                UiUtils.makeText("register");
                register();
                break;
        }
    }

    /**
     * 登录操作
     */
    private void login() {
        //TODO ... 登录操作逻辑
        final String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPassword.getText().toString().trim();
        if(EmptyUtils.isNotEmpty(account) && EmptyUtils.isNotEmpty(pwd)){
            // 网络判断
            if (NetworkUtils.isConnected()) {
                final ProgressDialog loginDialog = UiUtils.showProgressDialog(this, getString(R.string.login_ing));
                NimController.login(account, pwd, new RequestCallback<LoginInfo>() {
                    @Override public void onSuccess(LoginInfo param) {
                        UiUtils.dismissProgressDialog(loginDialog);
                        NimUIKit.setAccount(account);
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
            }else {
                UiUtils.makeText(getString(R.string.network_unavailable));
            }
        }else {
            UiUtils.makeText(getString(R.string.empty_acc_pwd));
        }
    }

    /**
     * 跳转注册
     */
    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
