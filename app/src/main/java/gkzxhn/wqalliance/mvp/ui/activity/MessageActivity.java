package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import com.jess.arms.utils.UiUtils;
import com.netease.nim.uikit.NimUIKit;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/6.
 */

public class MessageActivity extends BaseContentActivity{
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(UiUtils.getString(R.string.my_msg));
        mTvSubtitle.setVisibility(View.GONE);
        NimUIKit.startP2PSession(this,"Raleigh");
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_message, null, false);
        return contentView;
    }
}
