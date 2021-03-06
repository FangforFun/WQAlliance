package gkzxhn.wqalliance.mvp.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.jess.arms.utils.UiUtils;

import java.util.List;

import common.AppComponent;
import common.SuperApplication;
import gkzxhn.utils.DialogUtil;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.di.component.DaggerProtectionComponent;
import gkzxhn.wqalliance.di.module.ProtectionModule;
import gkzxhn.wqalliance.mvp.contract.ProtectionContract;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.OrderEvidence;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.presenter.ProtectionPresenter;
import gkzxhn.wqalliance.mvp.ui.activity.MyOrderActivity;
import gkzxhn.wqalliance.mvp.ui.activity.SignActivity;
import gkzxhn.wqalliance.mvp.ui.activity.UploadEdActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by 方 on 2017/3/6.
 */

public class ProtectionFragment extends BaseContentFragment<ProtectionPresenter> implements ProtectionContract.View, View.OnClickListener {

    private TextView mTv_checkout;
    private View mContentView;
    private ProgressDialog mProgressDialog;
    private PopupWindow mPopupWindow;
    private AlertDialog mDialog;

    public static ProtectionFragment newInstance() {
        ProtectionFragment fragment = new ProtectionFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerProtectionComponent
                .builder()
                .appComponent(appComponent)
                .protectionModule(new ProtectionModule(this))//请将ProtectionModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    private EditText mTheme;//主题
    private RelativeLayout mUploadEd;//上传证据
    private EditText mDesc;//案件详情
    private TextView mCommit;//提交按钮

    @Override
    protected void setTitleData() {
        mIvBack.setVisibility(View.GONE);
        mTvSubtitle.setVisibility(View.GONE);
        mTvTitle.setText("我要维权");

        int userId = (int) SPUtil.get(getActivity(), SharedPreferenceConstants.USERID, 0);
        getSignStatusFromNet(userId);
    }

    /**
     * 从网络获取签约状态
     * @param userId
     */
    private void getSignStatusFromNet(int userId) {
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
                SPUtil.put(getActivity(), SharedPreferenceConstants.SIGNEDSTATUS, signedStatus);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected View initContentView() {
        mContentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_protection, null, false);
        mTheme = (EditText) mContentView.findViewById(R.id.et_theme);   //主题
        mUploadEd = (RelativeLayout) mContentView.findViewById(R.id.rl_upload_ed);
        mDesc = (EditText) mContentView.findViewById(R.id.desc);
        mCommit = (TextView) mContentView.findViewById(R.id.tv_commit);

        mUploadEd.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        return mContentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_upload_ed:
                startActivity(new Intent(ProtectionFragment.this.getActivity(), UploadEdActivity.class));
                break;
            case R.id.tv_commit:
//                new AlertDialog.Builder(getActivity()).setView(R.layout.custom_dialog)
                commitCase();
                break;
            case R.id.tv_checkout:
                //TODO ...查看维权提交情况
                UiUtils.makeText("查看详情");
                mActivity.startActivity(new Intent(mActivity, MyOrderActivity.class));
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
        }
    }

    /**
     * 提交案件
     */
    public void commitCase() {
        int signedStatus = getSignedStatus();
        if (signedStatus == 3) {

            int userId = (int) SPUtil.get(mActivity, SharedPreferenceConstants.USERID, 0);
            String title = mTheme.getText().toString().trim();
            String description = mDesc.getText().toString().trim();
            List<OrderEvidence> orderEvidences = SuperApplication.getOrderEvidences();

            if (orderEvidences.size() < 18) {
                UiUtils.makeText("请上传所有证据信息");
                return;
            }

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || orderEvidences.size() == 0) {
                UiUtils.makeText("请完善信息");
                return;
            }
            mProgressDialog = UiUtils.showProgressDialog(mActivity);

            String orderEvidenceJs = new Gson().toJson(orderEvidences);
            Log.i(TAG, "onClick: orderEvidenceJs       " + orderEvidenceJs);
            Log.i(TAG, "onClick: userId       " + userId);
            Log.i(TAG, "onClick: title       " + title);
            Log.i(TAG, "onClick: description       " + description);
            mPresenter.addOrder(userId, title, description, orderEvidenceJs);
        }else if(0 == signedStatus) {
            mDialog = DialogUtil.showUnSignedDialog(mActivity, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog(mDialog);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog(mDialog);
                    launchActivity(new Intent(mActivity, SignActivity.class));
                }
            });
        }else if (1 == signedStatus) {
            UiUtils.makeText("您的签约还在审核中,请等待审核结果");
        }else if (2 == signedStatus) {
            UiUtils.makeText("您的签约审核失败,请重新提交签约");
        }
        return;
    }

    /**
     * 是否已签约
     * @return
     */
    private int getSignedStatus() {
        int i = (int) SPUtil.get(mActivity, SharedPreferenceConstants.SIGNEDSTATUS, 0);
        return i;
    }

//    @Subscriber()
//    public void updateClick(ClickEvent event) {
//        Log.i(TAG, "update: clickEvent..........");
//        commitCase();
//    }

    /**
     * 弹出提交操作成功的popupwindow
     */
    public void showSuccessPopupWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_success, null, false);

        mTv_checkout = (TextView) view.findViewById(R.id.tv_checkout);

        mTv_checkout.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        mPopupWindow.setTouchable(true);

//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Log.i("mengdd", "onTouch : ");
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popupwindow_background));

        //设置背景透明度
        backgroundAlpha(0.3f);

        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        // 设置好参数之后再show
        mPopupWindow.showAtLocation(mContentView, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dip2px(20));
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            UiUtils.dismissProgressDialog(mProgressDialog);
        }
    }

    /**
     * 提交成功后,清除提交内容
     */
    public void clearContent() {
        mTheme.setText("");
        mDesc.setText("");
        SuperApplication.getOrderEvidences().clear();
    }
}