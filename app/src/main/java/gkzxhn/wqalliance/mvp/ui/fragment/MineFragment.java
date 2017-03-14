package gkzxhn.wqalliance.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.UiUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.InfoChangedEvent;
import gkzxhn.wqalliance.mvp.model.entities.Result;
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

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar; // 头像
    @BindView(R.id.tv_sign_status)
    TextView tv_sign_status; // 签约状态
    @BindView(R.id.tv_login_status)
    TextView tv_login_status;// 登录状态
    @BindView(R.id.ll_my_order)
    LinearLayout ll_my_order;// 我的订单
    @BindView(R.id.ll_my_address)
    LinearLayout ll_my_address; // 我的地址
    @BindView(R.id.ll_contact_info)
    LinearLayout ll_contact_info; // 联系方式
    @BindView(R.id.ll_sign)
    LinearLayout ll_sign;// 签约
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting; // 设置

    private AlertDialog signDialog;
    private int mSignedStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Subscriber
    public void updateInfo(InfoChangedEvent event) {
        if (event != null) {
            Glide.with(getActivity()).load(event.getFaceUrl()).error(R.drawable.avatar_def).into(iv_avatar);
            tv_login_status.setText(event.getUserName());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String faceImgUrl = (String) SPUtil.get(getActivity(), SharedPreferenceConstants.FACEIMGURL, "");
        int userId = (int) SPUtil.get(getActivity(), SharedPreferenceConstants.USERID, 0);
        String userName = (String) SPUtil.get(getActivity(), SharedPreferenceConstants.USERNAME, "");

        Log.i(TAG, "onActivityCreated: userid   :" + userId );
        Log.i(TAG, "onActivityCreated: userName   :" + userName );
        Log.i(TAG, "onActivityCreated: faceImgUrl   :" + faceImgUrl );
        mSignedStatus = (int) SPUtil.get(getActivity(), SharedPreferenceConstants.SIGNEDSTATUS, 0);
        switch (mSignedStatus) {
            case 0:
                tv_sign_status.setText("未签约");
                break;
            case 1:
                tv_sign_status.setText("签约中");
                break;
            case 2:
                tv_sign_status.setText("签约失败");
                break;
            case 3:
                tv_sign_status.setText("已签约");
                break;
        }

        if (!TextUtils.isEmpty(userName))
            tv_login_status.setText(userName);
        if (!TextUtils.isEmpty(faceImgUrl)) {
            LogUtils.i(TAG, faceImgUrl);
            Glide.with(getActivity()).load(faceImgUrl).error(R.drawable.avatar_def).into(iv_avatar);
        }
        ApiWrap.getUser(userId, new SimpleObserver<Result>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(Result result) {
                super.onNext(result);
                LogUtils.i(TAG, result.toString());
                int signedStatus = result.getData().getSignedStatus();
                switch (signedStatus) {
                    case 0:
                        tv_sign_status.setText("未签约");
                        break;
                    case 1:
                        tv_sign_status.setText("签约中");
                        break;
                    case 2:
                        tv_sign_status.setText("签约失败");
                        break;
                    case 3:
                        tv_sign_status.setText("已签约");
                        break;
                }
                String userNameFromData = result.getData().getUserName();
                if (!TextUtils.isEmpty(userNameFromData)) {
                    tv_login_status.setText(userNameFromData);
                }
            }
        });
    }

    @OnClick({R.id.ll_my_order, R.id.ll_my_address, R.id.ll_contact_info,
            R.id.ll_sign, R.id.ll_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_status: //注销账号
//                UiUtils.startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
                break;
            case R.id.ll_my_order:
                UiUtils.startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.ll_my_address:
                UiUtils.startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
            case R.id.ll_contact_info:
                UiUtils.startActivity(new Intent(getActivity(), ContactWayActivity.class));
                break;
            case R.id.ll_sign://签约
                switch (mSignedStatus) {
                    case 1:  //签约中
                        UiUtils.makeText("您已提交签约,请等待审核结果");
                        break;
                }
                signDialog = DialogUtil.showSignDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UiUtils.makeText("线下");
                        signContractOffline();//线下签约
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.dismissDialog(signDialog);
                        UiUtils.startActivity(new Intent(getActivity(), SignActivity.class));
                    }
                });
                break;
            case R.id.ll_setting:
                UiUtils.startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    /**
     * 线下签约
     */
    private void signContractOffline() {
        int userId = (int) SPUtil.get(getActivity(), SharedPreferenceConstants.USERID, 0);

        ApiWrap.submitUserSign(userId, null, null, null, 1, new SimpleObserver<Result>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                UiUtils.makeText(UiUtils.getString(R.string.net_broken));
            }

            @Override
            public void onNext(Result result) {
                super.onNext(result);
                Log.i(TAG, "onNext: subiUserSign....." + result.getCode() + result.getMsg());
                UiUtils.makeText("已提交线下签约申请");
            }
        });
        if (signDialog != null) {
            DialogUtil.dismissDialog(signDialog);
        }
    }

    @Override
    public void onDestroy() {
        DialogUtil.dismissDialog(signDialog);
        super.onDestroy();
    }

}
