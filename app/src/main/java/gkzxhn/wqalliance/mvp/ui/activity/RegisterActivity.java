package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/3.
 */

public class RegisterActivity extends BaseContentActivity {
    @Override
    protected View initContentView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_register, null, false);
    }

    @Override
    protected void setTitleData() {

    }
}
