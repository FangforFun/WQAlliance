package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/9.
 */

public class SignContractActivity extends BaseContentActivity implements View.OnClickListener {
    /**
     * 是否已阅读
     */
    boolean isChecked = false;

    private TextView mTv_sign_contract;
    private ImageView mIv_read_checkbox;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText("签订合同");
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_sign_contract, null, false);
        mIv_read_checkbox = (ImageView) contentView.findViewById(R.id.iv_read_checkbox);
        mTv_sign_contract = (TextView) contentView.findViewById(R.id.tv_sign_contract);

        mIv_read_checkbox.setOnClickListener(this);
        mTv_sign_contract.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_read_checkbox:
                if (isChecked) {
                    mIv_read_checkbox.setImageResource(R.drawable.unread);
                    isChecked = false;
                }else {
                    mIv_read_checkbox.setImageResource(R.drawable.read);
                    isChecked = true;
                }
                break;
            case R.id.tv_sign_contract:
                //TODO ... 提交签约
                UiUtils.makeText("签约");
                break;
            case R.id.tv_subtitle:
                finish();
                break;
        }
    }
}
