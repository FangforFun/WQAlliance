package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.OrderProcedure;
import gkzxhn.wqalliance.mvp.widget.TimelineView;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:查看流程页面activity
 */
public class CheckProcessActivity extends BaseContentActivity {

    private TimelineView tlv_pass;
    private TextView tv_case_handle_person;
    private TextView tv_case_theme;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void setTitleData() {
        mTvTitle.setText(R.string.check_process);
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_check_process, null, false);
        tlv_pass = (TimelineView) view.findViewById(R.id.tlv_pass);
        tlv_pass.setMarker(getResources().getDrawable(R.drawable.marker_pass));
        tv_case_handle_person = (TextView) view.findViewById(R.id.tv_case_handle_person);
        tv_case_theme = (TextView) view.findViewById(R.id.tv_case_theme);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        int orderId = getIntent().getIntExtra("orderId", -1);
        getOrderProcedure(orderId);
    }

    private ProgressDialog dialog;

    /**
     * 获取订单流程
     * @param orderId
     */
    private void getOrderProcedure(int orderId) {
        int userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, -1);
        dialog = UiUtils.showProgressDialog(this);
        ApiWrap.getOrderProcedure(userId, orderId, new SimpleObserver<OrderProcedure>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog(dialog);
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.e(TAG, "get order procedure exception: " + e.getMessage());
            }

            @Override public void onNext(OrderProcedure dataBean) {
                LogUtils.i(TAG, "get order procedure result: " + dataBean.toString());
                UiUtils.dismissProgressDialog(dialog);
                if (dataBean.getCode() == 0) {
                    setLayoutData(dataBean);
                }else {
                    UiUtils.makeText(dataBean.getMsg());
                }
            }
        });
    }

    /**
     * 设置数据到布局上
     * @param dataBean
     */
    private void setLayoutData(OrderProcedure dataBean) {
        try {
            tv_case_theme.setText(dataBean.getData().getOrder().getTitle());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
