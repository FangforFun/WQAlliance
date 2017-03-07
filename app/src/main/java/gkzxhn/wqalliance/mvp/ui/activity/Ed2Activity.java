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

public class Ed2Activity extends BaseContentActivity implements View.OnClickListener {
    private RelativeLayout mEd2_1;
    private RelativeLayout mEd2_2;
    private RelativeLayout mEd2_3;
    private RelativeLayout mEd2_4;
    private RelativeLayout mEd2_5;
    private RelativeLayout mEd2_6;

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
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ed2, null, false);
        mEd2_1 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_1);
        mEd2_2 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_2);
        mEd2_3 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_3);
        mEd2_4 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_4);
        mEd2_5 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_5);
        mEd2_6 = (RelativeLayout) rootView.findViewById(R.id.rl_ed2_6);

        mEd2_1.setOnClickListener(this);
        mEd2_2.setOnClickListener(this);
        mEd2_3.setOnClickListener(this);
        mEd2_4.setOnClickListener(this);
        mEd2_5.setOnClickListener(this);
        mEd2_6.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_ed2_1:
                UiUtils.makeText("ed2_1");
                break;
            case R.id.rl_ed2_2:
                UiUtils.makeText("ed2_2");
                break;
            case R.id.rl_ed2_3:
                UiUtils.makeText("ed2_3");
                break;
            case R.id.rl_ed2_4:
                UiUtils.makeText("ed2_4");
                break;
            case R.id.rl_ed2_5:
                UiUtils.makeText("ed2_5");
                break;
            case R.id.rl_ed2_6:
                UiUtils.makeText("ed2_6");
                break;
        }
    }
}
