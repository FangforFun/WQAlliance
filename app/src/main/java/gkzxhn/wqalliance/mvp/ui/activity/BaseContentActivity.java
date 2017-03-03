package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import common.AppComponent;
import common.SuperActivity;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/2.
 */

public abstract class BaseContentActivity<p extends Presenter> extends SuperActivity<p> {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_subtitle)
    TextView mTvSubtitle;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    private Unbinder mChildUnbinder;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_base_content, null, false);
    }

    @Override
    protected void initData() {
        setTitleData();
        View contentView = initContentView();
        mFlContent.addView(contentView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化内容视图
     * @return
     */
    protected abstract View initContentView();

    /**
     * 设置标题内容
     */
    protected abstract void setTitleData();

    @OnClick({R.id.iv_back, R.id.tv_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_subtitle:
                doSubtitle();
                break;
        }
    }

    /**
     * 副标题点击操作
     */
    protected void doSubtitle() {

    }
}
