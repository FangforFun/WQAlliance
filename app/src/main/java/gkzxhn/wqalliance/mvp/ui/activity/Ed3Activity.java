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

public class Ed3Activity extends BaseContentActivity implements View.OnClickListener {
    private RelativeLayout mEd3_1;
    private RelativeLayout mEd3_2;
    private RelativeLayout mEd3_3;
    private RelativeLayout mEd3_4;
    private RelativeLayout mEd3_5;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(UiUtils.getString(R.string.ed3));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ed3, null, false);
        mEd3_1 = (RelativeLayout) rootView.findViewById(R.id.rl_ed3_1);
        mEd3_2 = (RelativeLayout) rootView.findViewById(R.id.rl_ed3_2);
        mEd3_3 = (RelativeLayout) rootView.findViewById(R.id.rl_ed3_3);
        mEd3_4 = (RelativeLayout) rootView.findViewById(R.id.rl_ed3_4);
        mEd3_5 = (RelativeLayout) rootView.findViewById(R.id.rl_ed3_5);

        mEd3_1.setOnClickListener(this);
        mEd3_2.setOnClickListener(this);
        mEd3_3.setOnClickListener(this);
        mEd3_4.setOnClickListener(this);
        mEd3_5.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_ed3_1:
                UiUtils.makeText("ed3_1");
                break;
            case R.id.rl_ed3_2:
                UiUtils.makeText("ed3_2");
                break;
            case R.id.rl_ed3_3:
                UiUtils.makeText("ed3_3");
                break;
            case R.id.rl_ed3_4:
                UiUtils.makeText("ed3_4");
                break;
            case R.id.rl_ed3_5:
                UiUtils.makeText("ed3_5");
                break;
        }
    }
}
