package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import com.jess.arms.utils.UiUtils;

import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:我的地址页面activity
 */
public class MyAddressActivity extends BaseContentActivity {

    @Override
    protected View initContentView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_my_address, null, false);
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.my_address));
        mTvSubtitle.setText(R.string.save);
    }

    @Override
    protected void doSubtitle() {
        UiUtils.makeText(getString(R.string.save));
    }
}
