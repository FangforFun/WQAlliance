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

import java.util.HashMap;
import java.util.Map;

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
 * Description:联系方式页面activity
 */
public class ContactWayActivity extends BaseContentActivity {

    private EditText et_phone;
    private EditText et_Email;

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_contact_way, null, false);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_Email = (EditText) view.findViewById(R.id.et_email);
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.contact_info));
        mTvSubtitle.setText(getString(R.string.save));
    }

    @Override
    protected void initData() {
        super.initData();
        String contactNumber = (String) SPUtil.get(this, SharedPreferenceConstants.CONTACTNUMBER, "");
        String email = (String) SPUtil.get(this, SharedPreferenceConstants.EMAIL, "");
        if (!TextUtils.isEmpty(contactNumber))
            et_phone.setText(contactNumber);
        if (!TextUtils.isEmpty(email))
            et_Email.setText(email);
    }

    private ProgressDialog updateDialog;

    @Override
    protected void doSubtitle() {
        String phone = et_phone.getText().toString().trim();
        String eMail = et_Email.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(eMail)){
            UiUtils.makeText(getString(R.string.can_not_be_empty));
            return;
        }
        updateDialog = UiUtils.showProgressDialog(this);
        if (NetworkUtils.isConnected()){
            Map<String, Object> map = new HashMap<>();
            map.put("userId", SPUtil.get(this, SharedPreferenceConstants.USERID, 1));
            map.put("email", eMail);
            map.put("contactNumber", phone);
            ApiWrap.updateUserInfo(map, new SimpleObserver<Result>(){
                @Override public void onError(Throwable e) {
                    UiUtils.dismissProgressDialog(updateDialog);
                    UiUtils.makeText(getString(R.string.timeout_retry));
                    LogUtils.i(TAG, "update user info failed: " + e.getMessage());
                }

                @Override public void onNext(Result result) {
                    LogUtils.i(TAG, "result: " + result.toString());
                    UiUtils.dismissProgressDialog(updateDialog);
                    UiUtils.makeText(result.getMsg());
                    if (result.getCode() == 0){
                        SPUtil.put(ContactWayActivity.this, SharedPreferenceConstants.EMAIL, result.getData().getEmail());
                        SPUtil.put(ContactWayActivity.this, SharedPreferenceConstants.CONTACTNUMBER, result.getData().getContactNumber());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ContactWayActivity.this.finish();
                            }
                        }, 500);
                    }
                }
            });
        }else {
            UiUtils.dismissProgressDialog(updateDialog);
            UiUtils.makeText(getString(R.string.net_broken));
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
