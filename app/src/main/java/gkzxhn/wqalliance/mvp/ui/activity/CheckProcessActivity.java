package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.util.List;

import common.AppComponent;
import gkzxhn.utils.DateUtils;
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

    private TextView tv_case_handle_person;
    private TextView tv_case_theme;
    private LinearLayout ll_procedures;

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
        ll_procedures = (LinearLayout) view.findViewById(R.id.ll_procedures);
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
        LogUtils.i(TAG, "orderId is : " + orderId + ", userId is : " + userId);
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
            tv_case_handle_person.setText(dataBean.getData().getLawyer().getLawfirmName() + " " + dataBean.getData().getLawyer().getName());
            List<OrderProcedure.DataBean.OrderProceduresBean> procedures = dataBean.getData().getOrderProcedures();
            if (procedures.size() > 0){
                // 动态添加layout_gray_time_line_all布局
                for (int i = 0; i < procedures.size(); i++){
                    OrderProcedure.DataBean.OrderProceduresBean bean = procedures.get(i);
                    View view = View.inflate(CheckProcessActivity.this, R.layout.layout_gray_time_line_all, null);
                    TimelineView timelineView = (TimelineView) view.findViewById(R.id.timeline_view);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    TextView tv_date_hour = (TextView) view.findViewById(R.id.tv_date_hour);
                    TextView tv_date_year = (TextView) view.findViewById(R.id.tv_date_year);

                    Log.i(TAG, "setLayoutData: type+++++++: " + bean.getType());
                    tv_title.setText(getTitle(bean.getType()));
                    tv_date_hour.setText(DateUtils.formatTime(bean.getCreatedAt(), "HH:mm"));
                    tv_date_year.setText(DateUtils.formatTime(bean.getCreatedAt(), "yyyy-MM-dd"));
                    if (i == procedures.size() - 1) {
                        timelineView.setMarker(getResources().getDrawable(R.drawable.marker));
                        timelineView.setStartLine(getResources().getDrawable(R.drawable.theme_line));
                        timelineView.setEndLine(getResources().getDrawable(R.drawable.theme_line));
                    }
                    ll_procedures.addView(view, 1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * // type  0-提交；1-审核拒绝；2-审核同意；3-报价；4-已支付; 5-已完成;
     * @param type
     * @return
     */
    private String getTitle(int type) {
        String result;
        switch (type){
            case 0:
                result = "案件已提交";
                break;
            case 1:
                result = "案件审核被拒绝";
                break;
            case 2:
                result = "案件审核通过";
                break;
            case 3:
                result = "案件报价";
                break;
            case 4:
                result = "已支付";
                break;
            case 5:
                result = "已完成";
                break;
            default:
                result = "未知";
                break;
        }
        return result;
    }
}
