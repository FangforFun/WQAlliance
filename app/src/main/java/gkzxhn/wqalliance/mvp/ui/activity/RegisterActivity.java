package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.io.IOException;

import common.AppComponent;
import gkzxhn.utils.Utils;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import okhttp3.ResponseBody;

/**
 * Created by 方 on 2017/3/3.
 */

public class RegisterActivity extends BaseContentActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private EditText et_phone_number;
    private TextView tv_send_code;
    private EditText et_auth_code;
    private EditText et_password;
    private TextView tv_register;

    private ProgressDialog registerDialog;

    @Override
    protected void onDestroy() {
        UiUtils.dismissProgressDialog(registerDialog);
        super.onDestroy();
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_register, null, false);
        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);
        tv_send_code = (TextView) view.findViewById(R.id.tv_send_code);
        et_auth_code = (EditText) view.findViewById(R.id.et_auth_code);
        et_password = (EditText) view.findViewById(R.id.et_password);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        tv_send_code.setOnClickListener(this);
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText("注册");
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_register:
                if (Utils.isAvailableByPing()) {
                    String phone = et_phone_number.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    String code = et_auth_code.getText().toString().trim();
                    registerDialog = UiUtils.showProgressDialog(this, "正在注册");
                    ApiWrap.register(phone, password, code, new SimpleObserver<Result>() {
                        @Override
                        public void onError(Throwable e) {
                            UiUtils.dismissProgressDialog(registerDialog);
                            UiUtils.makeText(getString(R.string.server_exeption));
                            LogUtils.e(TAG, "register request exception: " + e.getMessage());
                        }

                        @Override
                        public void onNext(Result result) {
                            Log.i(TAG, "onNext:        " + result.toString());
                            UiUtils.dismissProgressDialog(registerDialog);
                            if (result.getCode() == 0) {
                                UiUtils.makeText(getString(R.string.register_success));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 500);
                            } else {
                                UiUtils.makeText(result.getMsg());
                            }
                            LogUtils.i(TAG, "register request result: " + result.toString());
                        }
                    });
                } else {
                    UiUtils.makeText(getString(R.string.net_broken));
                }
                break;
            case R.id.tv_send_code:
                String phone = et_phone_number.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    UiUtils.makeText("请填写手机号");
                    return;
                }

                //发送验证码
                new Thread(new MyCountDownTimer()).start();
                ApiWrap.sendMsg(phone, new SimpleObserver<ResponseBody>(){
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: send_code   " + e.getMessage());
                        super.onError(e);
                    }

//                    @Override
//                    public void onNext(Code code) {
//                        super.onNext(code);
//                        Log.i(TAG, "onNext: code" + code.code);
//                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        String result = null;
                        try {
                            result = responseBody.string();
                            Log.i(TAG, "onNext: send _ code _ result  " + result);
//                            if ("success".equals(result)) {
                                UiUtils.makeText("验证码已发送");
//                            }else {
//                                UiUtils.makeText("验证码发送失败");
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onNext: send_code   " + result);
                    }
                });
                break;
        }
    }

    public Handler mHandler = new Handler();

    class MyCountDownTimer implements Runnable {
        private int time = 60;

        @Override
        public void run() {
            while (time > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_send_code.setEnabled(false);
                        tv_send_code.setText(time + "秒后重发");
                        tv_send_code.setBackgroundResource(R.drawable.btn_code_shape);
                    }
                });
                SystemClock.sleep(1000);
                time -- ;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    tv_send_code.setText(UiUtils.getString(R.string.send_code));
                    tv_send_code.setBackgroundResource(R.drawable.btn_login_shape);
                    tv_send_code.setEnabled(true);
                }
            });
            time = 60;
        }
    }
}
