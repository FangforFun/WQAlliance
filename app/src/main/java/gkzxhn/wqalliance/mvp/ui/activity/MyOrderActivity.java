package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.di.component.DaggerMyOrderComponent;
import gkzxhn.wqalliance.di.module.MyOrderModule;
import gkzxhn.wqalliance.mvp.contract.MyOrderContract;
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
        order_list.setLayoutManager(new LinearLayoutManager(this));
        order_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        listAdapter = new MyOrderListAdapter(this, 1);
        order_list.setAdapter(listAdapter);
        table_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.i(TAG, "position: " + tab.getPosition());
                listAdapter.switchList(tab.getPosition() + 1);
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
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
