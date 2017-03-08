package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:设置页面activity
 */
public class SettingActivity extends BaseContentActivity implements View.OnClickListener {

    private LinearLayout ll_notification_setting;
    private LinearLayout ll_change_pwd;
    private LinearLayout ll_change_info;

    @Override protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void setTitleData() {
        mTvTitle.setText("设置");
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_setting, null, false);
        ll_notification_setting = (LinearLayout) view.findViewById(R.id.ll_notification_setting);
        ll_change_pwd = (LinearLayout) view.findViewById(R.id.ll_change_pwd);
        ll_change_info = (LinearLayout) view.findViewById(R.id.ll_change_info);
        ll_change_info.setOnClickListener(this);
        ll_notification_setting.setOnClickListener(this);
        ll_change_pwd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.ll_change_info:
                UiUtils.startActivity(new Intent(this, ChangeInfoActivity.class));
                break;
            case R.id.ll_change_pwd:
                UiUtils.startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.ll_notification_setting:
                UiUtils.startActivity(new Intent(this, NotificationSettingActivity.class));
                break;
        }
    }
}
