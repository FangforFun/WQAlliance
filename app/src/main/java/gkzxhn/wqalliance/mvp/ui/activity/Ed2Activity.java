package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/6.
 */

public class Ed2Activity extends BaseContentActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(UiUtils.getString(R.string.ed2));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_ed2, null, false);
    }
}
