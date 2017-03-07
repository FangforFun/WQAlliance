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

public class Ed1Activity extends BaseContentActivity implements View.OnClickListener {

    private RelativeLayout mEd1_1;
    private RelativeLayout mEd1_2;
    private RelativeLayout mEd1_3;
    private RelativeLayout mEd1_4;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(UiUtils.getString(R.string.ed1));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ed1, null, false);
        mEd1_1 = (RelativeLayout) rootView.findViewById(R.id.rl_ed1_1);
        mEd1_2 = (RelativeLayout) rootView.findViewById(R.id.rl_ed1_2);
        mEd1_3 = (RelativeLayout) rootView.findViewById(R.id.rl_ed1_3);
        mEd1_4 = (RelativeLayout) rootView.findViewById(R.id.rl_ed1_4);

        mEd1_1.setOnClickListener(this);
        mEd1_2.setOnClickListener(this);
        mEd1_3.setOnClickListener(this);
        mEd1_4.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_ed1_1:
                UiUtils.makeText("ed1_1");
                break;
            case R.id.rl_ed1_2:
                UiUtils.makeText("ed1_2");
                break;
            case R.id.rl_ed1_3:
                UiUtils.makeText("ed1_3");
                break;
            case R.id.rl_ed1_4:
                UiUtils.makeText("ed1_4");
                break;
        }
    }
}
