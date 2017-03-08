package gkzxhn.wqalliance.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.ui.activity.ContactWayActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MyAddressActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MyOrderActivity;
import gkzxhn.wqalliance.mvp.ui.activity.SettingActivity;
import gkzxhn.wqalliance.mvp.ui.activity.SignActivity;
import gkzxhn.wqalliance.mvp.widget.CircleImageView;

/**
 * Created by 方 on 2017/3/3.
 */
public class MineFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MineFragment";

    @BindView(R.id.iv_avatar) CircleImageView iv_avatar; // 头像
    @BindView(R.id.tv_sign_status) TextView tv_sign_status; // 签约状态
    @BindView(R.id.tv_login_status) TextView tv_login_status;// 登录状态
    @BindView(R.id.ll_my_order) LinearLayout ll_my_order;// 我的订单
    @BindView(R.id.ll_my_address) LinearLayout ll_my_address; // 我的地址
    @BindView(R.id.ll_contact_info) LinearLayout ll_contact_info; // 联系方式
    @BindView(R.id.ll_sign) LinearLayout ll_sign;// 签约
    @BindView(R.id.ll_setting) LinearLayout ll_setting; // 设置

    private AlertDialog signDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String faceImgUrl = (String) SPUtil.get(getActivity(), SharedPreferenceConstants.FACEIMGURL, "");
        if (!TextUtils.isEmpty(faceImgUrl)){
            LogUtils.i(TAG, faceImgUrl);
            Glide.with(getActivity()).load(faceImgUrl).error(R.drawable.avatar_def).into(iv_avatar);
        }
    }

    @OnClick({R.id.ll_my_order, R.id.ll_my_address, R.id.ll_contact_info,
            R.id.ll_sign, R.id.ll_setting})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_my_order:
                UiUtils.startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.ll_my_address:
                UiUtils.startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
            case R.id.ll_contact_info:
                UiUtils.startActivity(new Intent(getActivity(), ContactWayActivity.class));
                break;
            case R.id.ll_sign:
                signDialog = DialogUtil.showSignDialog(getActivity(), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        UiUtils.makeText("线下");
                    }
                }, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        UiUtils.startActivity(new Intent(getActivity(), SignActivity.class));
                    }
                });
                break;
            case R.id.ll_setting:
                UiUtils.startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismissDialog(signDialog);
        super.onDestroy();
    }

}
