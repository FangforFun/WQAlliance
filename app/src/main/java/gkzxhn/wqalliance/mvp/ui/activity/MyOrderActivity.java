package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import java.util.List;

import common.AppComponent;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.di.component.DaggerMyOrderComponent;
import gkzxhn.wqalliance.di.module.MyOrderModule;
import gkzxhn.wqalliance.mvp.contract.MyOrderContract;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.OrderResult;
import gkzxhn.wqalliance.mvp.presenter.MyOrderPresenter;
import gkzxhn.wqalliance.mvp.ui.adapter.MyOrderListAdapter;
import gkzxhn.wqalliance.mvp.widget.DividerItemDecoration;

import static me.jessyan.rxerrorhandler.utils.Preconditions.checkNotNull;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:我的订单页面activity
 */
public class MyOrderActivity extends BaseContentActivity<MyOrderPresenter> implements MyOrderContract.View {

    private static final String TAG = "MyOrderActivity";
    private TabLayout table_layout;
    private RecyclerView order_list;
    private MyOrderListAdapter listAdapter;

    @Override
    protected void initData() {
        super.initData();
        table_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.i(TAG, "position: " + tab.getPosition());
                getOrderList(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
        getOrderList(0);// 默认进入页面获取待审查订单列表
    }

    private ProgressDialog getOrderDialog;

    /**
     * 获取默认订单列表   待审查
     * @param type  订单类型
     */
    private void getOrderList(final int type) {
        getOrderDialog = UiUtils.showProgressDialog(this);
        int userId = (int) SPUtil.get(this, SharedPreferenceConstants.USERID, -1);
        ApiWrap.getOrders(userId, type, new SimpleObserver<OrderResult>(){
            @Override public void onError(Throwable e) {
                UiUtils.dismissProgressDialog();
                UiUtils.makeText(getString(R.string.timeout_retry));
                LogUtils.i(TAG, "get order exception: " + e.getMessage());
            }

            @Override public void onNext(OrderResult orderResult) {
                LogUtils.i(TAG, "get order result: " + orderResult.toString());
                UiUtils.dismissProgressDialog(getOrderDialog);
                if (orderResult.getCode() == 0){
                    if (orderResult.getData().size() > 0){
                        setListLayout(type, orderResult.getData());
                        return;
                    }
                    UiUtils.makeText(getString(R.string.no_order));
                    order_list.setVisibility(View.GONE);
                    return;
                }
                UiUtils.makeText(orderResult.getMsg());
            }
        });
    }

    /**
     * 设置列表布局
     * @param data
     */
    private void setListLayout(int type, List<OrderResult.DataBean> data) {
        order_list.setVisibility(View.VISIBLE);
        if (listAdapter == null) {
            order_list.setLayoutManager(new LinearLayoutManager(this));
            order_list.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            listAdapter = new MyOrderListAdapter(this, type, data);
            order_list.setAdapter(listAdapter);
        }else {
            listAdapter.switchList(type, data);
        }
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_my_order, null, false);
        table_layout = (TabLayout) view.findViewById(R.id.table_layout);
        order_list = (RecyclerView) view.findViewById(R.id.order_list);
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(R.string.my_order);
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMyOrderComponent
                .builder()
                .appComponent(appComponent)
                .myOrderModule(new MyOrderModule(this))
                .build()
                .inject(this);
    }
}
