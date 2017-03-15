package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import common.SuperApplication;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:设置页面activity
 */
public class SettingActivity extends BaseContentActivity implements View.OnClickListener {

    private LinearLayout ll_notification_setting;//通知设置
    private LinearLayout ll_change_pwd;//密码修改
    private LinearLayout ll_change_info;//资料修改
    private Button bt_checkout_account;// 切换账号

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
        bt_checkout_account = (Button) view.findViewById(R.id.bt_checkout_account);
        ll_change_info.setOnClickListener(this);
        ll_notification_setting.setOnClickListener(this);
        ll_change_pwd.setOnClickListener(this);
        bt_checkout_account.setOnClickListener(this);
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
            case R.id.bt_checkout_account:
                UiUtils.showAlertDialog(this, "确定切换账号吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtil.clear(SettingActivity.this);
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                        SuperApplication.getOrderEvidences().clear();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}
