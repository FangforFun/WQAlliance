package gkzxhn.wqalliance.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;
import common.SuperFragment;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/3.
 */

public abstract class BaseContentFragment<p extends Presenter> extends SuperFragment<p> {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_subtitle)
    TextView mTvSubtitle;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_base_content, null, false);
    }

    @Override
    protected void initData() {
        setTitleData();
        View contentView = initContentView();
        mFlContent.addView(contentView);
    }

    /**
     * 设置标题内容,包括返回键以及副标题的显示隐藏
     */
    protected abstract void setTitleData();

    /**
     * 初始化内容视图
     *
     * @return
     */
    protected abstract View initContentView();

    @OnClick({R.id.iv_back, R.id.tv_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
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
