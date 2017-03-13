package gkzxhn.wqalliance.mvp.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jess.arms.utils.UiUtils;

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
        ll_alipay = (LinearLayout) view.findViewById(R.id.ll_alipay);//支付宝支付
        ll_wx_pay = (LinearLayout) view.findViewById(R.id.ll_wx_pay);//微信支付
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
                //选择支付宝支付
                payByAlipay("alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017030306022281&biz_content=%7B%22body%22%3A%22%E7%BB%B4%E6%9D%83%E8%81%94%E7%9B%9F%E8%AE%A2%E5%8D%95%EF%BC%9A%E8%B4%A6%E6%88%B7%E8%A2%AB%E7%9B%97%22%2C%22out_trade_no%22%3A%221000001019159702%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%B4%A6%E6%88%B7%E8%A2%AB%E7%9B%97%22%2C%22timeout_express%22%3A%225d%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2F&sign=h72TaqEQTCqhHtw0eCf41WDZoZgFKIo4WMdD2UpV%2FWTWga8kVT0A0rYKeH25Tf5fyv7k2F02i4Dt8AOGlq4ptmhyUmmVfRIG5%2FGOUX5Vh83YaNyiJpIP4JGhW6BimcvmMXxPocTeEQj3T0YThjyhjopscOWOEcyZn5nBeFnFpOs%3D");
                break;
            case R.id.ll_wx_pay:
                //选择微信支付

                break;
        }
    }

    private void payByAlipay(final String paysign){

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayWaysActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(paysign, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                        PayResult payResult = new PayResult((String) msg.obj);
                        /**
                         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                         * docType=1) 建议商户依赖异步通知
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
//                            payedSuccess();
                            UiUtils.makeText("success");
                        } else {
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
//                                payedSuccess();
                                UiUtils.makeText("      8000");
                            } else if (TextUtils.equals(resultStatus, "6001")) {
//                                payedCancel();
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                UiUtils.makeText("6001     支付失败    ----");
                            }else{
//                                payedFailed();
                                UiUtils.makeText("支付失败");
                            }
                        }


                    break;
                }
                default:
                    break;
            }
        }
    };


    class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(String rawResult) {

            if (TextUtils.isEmpty(rawResult))
                return;

            String[] resultParams = rawResult.split(";");
            for (String resultParam : resultParams) {
                if (resultParam.startsWith("resultStatus")) {
                    resultStatus = gatValue(resultParam, "resultStatus");
                }
                if (resultParam.startsWith("result")) {
                    result = gatValue(resultParam, "result");
                }
                if (resultParam.startsWith("memo")) {
                    memo = gatValue(resultParam, "memo");
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        private String gatValue(String content, String key) {
            String prefix = key + "={";
            return content.substring(content.indexOf(prefix) + prefix.length(),
                    content.lastIndexOf("}"));
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }


    }
}
