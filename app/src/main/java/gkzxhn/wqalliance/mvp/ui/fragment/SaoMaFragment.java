package gkzxhn.wqalliance.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/3.
 */
public class SaoMaFragment extends BaseContentFragment implements View.OnClickListener {

    private TextView mTv_saoma;
    private ImageView mIv_saoma;
    private String mResult;

    @Override
    protected void setTitleData() {
        mIvBack.setVisibility(View.GONE);
        mTvSubtitle.setVisibility(View.GONE);
        mTvTitle.setText("二维码/条码");
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_saoma, null, false);
        mTv_saoma = (TextView)contentView.findViewById(R.id.tv_saoma_result);
        mIv_saoma = (ImageView)contentView.findViewById(R.id.iv_saoma_result);

        if (mResult != null) {
            mTv_saoma.setText(mResult);
        }
        return contentView;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    public void setResult(String result) {
        mResult = result;
    }
}
