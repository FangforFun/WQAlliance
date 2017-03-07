package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/6.
 */

public class Ed4Activity extends BaseContentActivity implements View.OnClickListener {
    private RelativeLayout mEd4_1;
    private RelativeLayout mEd4_2;
    private RelativeLayout mEd4_3;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(UiUtils.getString(R.string.ed4));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ed4, null, false);
        mEd4_1 = (RelativeLayout) rootView.findViewById(R.id.rl_ed4_1);
        mEd4_2 = (RelativeLayout) rootView.findViewById(R.id.rl_ed4_2);
        mEd4_3 = (RelativeLayout) rootView.findViewById(R.id.rl_ed4_3);

        mEd4_1.setOnClickListener(this);
        mEd4_2.setOnClickListener(this);
        mEd4_3.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_ed4_1:
                UiUtils.makeText("ed4_1");
                break;
            case R.id.rl_ed4_2:
                UiUtils.makeText("ed4_2");
                break;
            case R.id.rl_ed4_3:
                UiUtils.makeText("ed4_3");
                break;
        }
    }
}
