package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

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
    protected void doSubtitle() {
        UiUtils.makeText(getString(R.string.save));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
