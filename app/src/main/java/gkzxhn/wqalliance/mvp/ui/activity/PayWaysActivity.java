package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/13
 * Email:943852572@qq.com
 * Description:线上支付页面  选择支付方式页面activity
 */
public class PayWaysActivity extends BaseContentActivity implements View.OnClickListener {

    private TextView tv_pay_note;
    private TextView tv_total_money;
    private LinearLayout ll_alipay;
    private LinearLayout ll_wx_pay;

    @Override protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void setTitleData() {
        mTvTitle.setText(R.string.inline_pay);
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View view = View.inflate(this, R.layout.activity_pay_ways, null);
        tv_pay_note = (TextView) view.findViewById(R.id.tv_pay_note);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        ll_alipay = (LinearLayout) view.findViewById(R.id.ll_alipay);
        ll_wx_pay = (LinearLayout) view.findViewById(R.id.ll_wx_pay);
        ll_alipay.setOnClickListener(this);
        ll_wx_pay.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_pay_note.setText(getString(R.string.pay_note));
        tv_total_money.setText("");
        int orderId = getIntent().getIntExtra("orderId", -1);
        int money = getIntent().getIntExtra("money", -1);
        if (money != -1){
            tv_total_money.setText("￥ " + money);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.ll_alipay:

                break;
            case R.id.ll_wx_pay:

                break;
        }
    }
}
