package gkzxhn.wqalliance.mvp.ui.activity;

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

import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;
import common.AppComponent;
import common.SuperActivity;
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
                break;
            case R.id.btn_register:
                Log.i(TAG, "onClick: register");
                UiUtils.makeText("register");
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
//        startActivity(new Intent(this, ));
    }
}
