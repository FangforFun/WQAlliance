package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import common.AppComponent;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:消息通知activity
 */
public class NotificationSettingActivity extends BaseContentActivity {

    private Switch aSwitch_new_msg_remind;

    @Override protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.notification_setting));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_notification_setting, null, false);
        aSwitch_new_msg_remind = (Switch) view.findViewById(R.id.aSwitch_new_msg_remind);
        Boolean checked = (Boolean) SPUtil.get(this, SharedPreferenceConstants.NOTIFICATION, true);
        aSwitch_new_msg_remind.setChecked(checked);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        aSwitch_new_msg_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                UiUtils.makeText(isChecked ? "已开启" : "已关闭");
                SPUtil.put(NotificationSettingActivity.this, SharedPreferenceConstants.NOTIFICATION, isChecked);
            }
        });
    }
}
