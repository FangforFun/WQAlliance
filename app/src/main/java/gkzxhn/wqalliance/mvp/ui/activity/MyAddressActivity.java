package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_my_address, null, false);
        et_my_addr = (EditText) view.findViewById(R.id.et_my_addr);
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
        String address = et_my_addr.getText().toString().trim();
        if (!TextUtils.isEmpty(address)){
            if (Utils.isAvailableByPing()){
                updateDialog = UiUtils.showProgressDialog(this);
                Map<String, Object> map = new HashMap<>();
                map.put("userId", SPUtil.get(this, SharedPreferenceConstants.USERID, 1));
                map.put("address", address);
                ApiWrap.updateUserInfo(map, new SimpleObserver<Result>(){
                    @Override public void onError(Throwable e) {
                        UiUtils.makeText(getString(R.string.timeout_retry));
                        UiUtils.dismissProgressDialog(updateDialog);
                        LogUtils.i(TAG, "update user info failed: " + e.getMessage());
                    }

                    @Override public void onNext(Result result) {
                        LogUtils.i(TAG, "result: " + result.toString());
                        UiUtils.dismissProgressDialog(updateDialog);
                        UiUtils.makeText(result.getMsg());
                        if (result.getCode() == 0){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MyAddressActivity.this.finish();
                                }
                            }, 500);
                        }
                    }
                });
            }else {
                UiUtils.makeText(getString(R.string.net_broken));
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
