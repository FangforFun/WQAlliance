package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/6.
 */

public class UploadEdActivity extends BaseContentActivity implements View.OnClickListener {

    private RelativeLayout mRlEd1;
    private RelativeLayout mRlEd2;
    private RelativeLayout mRlEd3;
    private RelativeLayout mRlEd4;
    public static final String TYPE = "type";

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(R.string.upload_ed);
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View uploadEd = LayoutInflater.from(this).inflate(R.layout.activity_upload_ed, null, false);
        mRlEd1 = (RelativeLayout) uploadEd.findViewById(R.id.rl_ed1);
        mRlEd2 = (RelativeLayout) uploadEd.findViewById(R.id.rl_ed2);
        mRlEd3 = (RelativeLayout) uploadEd.findViewById(R.id.rl_ed3);
        mRlEd4 = (RelativeLayout) uploadEd.findViewById(R.id.rl_ed4);
        mRlEd1.setOnClickListener(this); ;
        mRlEd2.setOnClickListener(this);
        mRlEd3.setOnClickListener(this);
        mRlEd4.setOnClickListener(this);
        return uploadEd;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(UploadEdActivity.this, EdActivity.class);
        switch (view.getId()) {
            case R.id.rl_ed1:
                intent.putExtra(TYPE, 0);
                intent.putExtra("title", "当事人主题资格");
                startActivity(intent);
                break;
            case R.id.rl_ed2:
                intent.putExtra("title", "原告拥有商标权");
                intent.putExtra(TYPE, 1);
                startActivity(intent);
                break;
            case R.id.rl_ed3:
                intent.putExtra(TYPE, 2);
                intent.putExtra("title", "被告实施商标侵权行为");
                startActivity(intent);
                break;
            case R.id.rl_ed4:
                intent.putExtra(TYPE, 3);
                intent.putExtra("title", "禁止侵权开支");
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
