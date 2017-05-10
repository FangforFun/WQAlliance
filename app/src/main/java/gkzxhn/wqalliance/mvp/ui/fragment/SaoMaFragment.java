package gkzxhn.wqalliance.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.Subscriber;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;
import gkzxhn.wqalliance.mvp.model.entities.ToHomeEvent;
import gkzxhn.wqalliance.mvp.ui.activity.FightFakeActivity;

/**
 * Created by 方 on 2017/3/3.
 */
public class SaoMaFragment extends BaseContentFragment implements View.OnClickListener {

    private TextView mTv_saoma;
    private ImageView mIv_saoma;
    private TextView mTv_goods_info;
    private ScanningInfo mResult;
    private TextView tv_fignt_fake;
    private String mGoodsName;
    private LinearLayout mLl_saoma_result;
    private TextView tv_fake;
    private TextView mTv_fake_scan;
    private RelativeLayout mRl_real;

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
        tv_fignt_fake = (TextView)contentView.findViewById(R.id.tv_fignt_fake);
        tv_fake = (TextView)contentView.findViewById(R.id.tv_fake);
        mLl_saoma_result = (LinearLayout)contentView.findViewById(R.id.ll_saoma_result);
        mTv_fake_scan = (TextView)contentView.findViewById(R.id.tv_fake_scan);
        mRl_real = (RelativeLayout)contentView.findViewById(R.id.rl_real);

        tv_fignt_fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FightFakeActivity.class);
                intent.putExtra("goods_name", mGoodsName);
                startActivity(intent);
            }
        });

        if (mResult != null) {
            Log.i(TAG, "initContentView: result goods  " + mResult);
            int code = mResult.code;
            switch (code) {
                case 0 :
                    //成功,真产品
                    mTv_fake_scan.setVisibility(View.GONE);
                    mRl_real.setVisibility(View.VISIBLE);
                    mGoodsName = mResult.data.goods.goodsName;
                    mIv_saoma.setImageResource(R.drawable.saoma_first);
                    mTv_goods_info.setText("商品: " + mGoodsName + "\r\n" + mResult.data.goods.goodsDesc);
                    tv_fignt_fake.setVisibility(View.GONE);
                    break;
                case 40001 :
                    mTv_fake_scan.setVisibility(View.VISIBLE);
                    mRl_real.setVisibility(View.GONE);
                    mIv_saoma.setImageResource(R.drawable.saoma_bad);
                    tv_fignt_fake.setVisibility(View.VISIBLE);

                    /*//无此产品
                    mGoodsName = null;
                    mIv_saoma.setImageResource(R.drawable.saoma_bad);
                    mLl_saoma_result.setVisibility(View.INVISIBLE);
                    tv_fake.setVisibility(View.VISIBLE);
                    tv_fake.setText("此商品未认证");
                    mTv_goods_info.setText("条码信息: " + mResult.scanningCode);
                    tv_fignt_fake.setVisibility(View.VISIBLE);*/
                    break;
                case 40002 :
                    //成功,真产品
                    mTv_fake_scan.setVisibility(View.GONE);
                    mRl_real.setVisibility(View.VISIBLE);
                    mGoodsName = mResult.data.goods.goodsName;
                    mIv_saoma.setImageResource(R.drawable.saoma_first);
                    mTv_goods_info.setText("商品: " + mGoodsName + "\r\n" + mResult.data.goods.goodsDesc);
                    tv_fignt_fake.setVisibility(View.GONE);

                    /*//此条码已被扫描
                    mIv_saoma.setImageResource(R.drawable.saoma_used);
                    mTv_saoma.setText(mResult.data.scanNumber+"");
                    mGoodsName = mResult.data.goods.goodsName;
                    if (mGoodsName != null) {
                        mTv_goods_info.setText(mGoodsName + "\r\n" + mResult.data.goods.goodsDesc);
                    }
                    tv_fignt_fake.setVisibility(View.VISIBLE);*/
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

    @Subscriber
    public void toHome(ToHomeEvent toHomeEvent){
        tv_fignt_fake.setText("提交打假成功 √");
        tv_fignt_fake.setClickable(false);
        tv_fignt_fake.setBackgroundColor(Color.TRANSPARENT);
        tv_fignt_fake.setTextColor(UiUtils.getColor(R.color.B));
    }
}
