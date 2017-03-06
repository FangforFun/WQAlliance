package gkzxhn.wqalliance.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/3.
 */
public class ProtectionFragment extends BaseContentFragment {

    @Override
    protected void setTitleData() {
        mIvBack.setVisibility(View.GONE);
        mTvSubtitle.setVisibility(View.GONE);
        mTvTitle.setText("我要维权");
    }

    @Override
    protected View initContentView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_protection, null, false);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }
}
