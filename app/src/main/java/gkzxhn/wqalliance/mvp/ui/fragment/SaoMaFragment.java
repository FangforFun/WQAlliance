package gkzxhn.wqalliance.mvp.ui.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;

/**
 * Created by 方 on 2017/3/3.
 */
public class SaoMaFragment extends BaseContentFragment implements View.OnClickListener {

    private TextView mTv_saoma;
    private ImageView mIv_saoma;
    private TextView mTv_goods_info;
    private ScanningInfo mResult;

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
        mTv_goods_info = (TextView)contentView.findViewById(R.id.tv_goods_info);

        if (mResult != null) {
            Log.i(TAG, "initContentView: result goods  " + mResult);
            int code = mResult.code;
            switch (code) {
                case 0 :
                    //成功
                    mIv_saoma.setImageResource(R.drawable.saoma_first);
                    mTv_saoma.setText("此商品为正品");
                    mTv_goods_info.setText("商品信息: " + mResult.data.goodsName);
                    break;
                case 40001 :
                    //无此产品
                    mIv_saoma.setImageResource(R.drawable.saoma_bad);
                    mTv_saoma.setText("此商品未认证");
                    mTv_goods_info.setText("条码信息: " + mResult.data.goodsName);
                    break;
                case 40002 :
                    //此条码已被使用
                    mIv_saoma.setImageResource(R.drawable.saoma_used);
                    mTv_saoma.setText("此条码已被使用");
                    if (mResult.data.goodsName != null) {
                        mTv_goods_info.setText("商品信息: " + mResult.data.goodsName);
                    }
                    break;
                default:
                    break;
            }
        }
        return contentView;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    public void setResult(ScanningInfo result) {
        mResult = result;
    }
}
