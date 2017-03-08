package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.utils.Utils;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:忘记密码
 */
public class ForgetPwdActivity extends BaseContentActivity implements View.OnClickListener {

    private EditText et_phone_number;
    private TextView tv_send_code;
    private EditText et_auth_code;
    private EditText et_password;
    private TextView tv_commit;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(R.string.forget_pwd);
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_forget_pwd, null, false);
        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);
        tv_send_code = (TextView) view.findViewById(R.id.tv_send_code);
        et_auth_code = (EditText) view.findViewById(R.id.et_auth_code);
        et_password = (EditText) view.findViewById(R.id.et_password);
        tv_commit = (TextView) view.findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        tv_send_code.setOnClickListener(this);
        return view;
    }

    private ProgressDialog updatePasswordDialog;

    @Override
    protected void onDestroy() {
        UiUtils.dismissProgressDialog(updatePasswordDialog);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_commit:
                String phone = et_phone_number.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                if (Utils.isAvailableByPing()) {
                    updatePasswordDialog = UiUtils.showProgressDialog(this, getString(R.string.committing));
                    ApiWrap.updatePassword(phone, pwd, new SimpleObserver<Result>() {
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
                                        ForgetPwdActivity.this.finish();
                                    }
                                }, 500);
                            }
                        }
                    });
                }else {
                    UiUtils.makeText(getString(R.string.net_broken));
                }
                break;
            case R.id.tv_send_code:

                break;
        }
    }
}
