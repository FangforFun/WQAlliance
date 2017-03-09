package gkzxhn.wqalliance.mvp.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;
import gkzxhn.wqalliance.mvp.ui.adapter.EvidenceListAdapter;

/**
 * Created by 方 on 2017/3/9.
 */

public class EdActivity extends BaseContentActivity {

    private RecyclerView mRl_evidence;
    private EvidenceListAdapter mEvidenceListAdapter;
    private int mType;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mType = getIntent().getIntExtra("type", 0);
        mTvTitle.setText(UiUtils.getString(R.string.upload_ed));
        mTvSubtitle.setVisibility(View.GONE);
    }

    private ProgressDialog loginDialog;

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_ed, null, false);
        mRl_evidence = (RecyclerView) contentView.findViewById(R.id.rl_evidence);
        if (NetworkUtils.isConnected()) {
            loginDialog = UiUtils.showProgressDialog(this, getString(R.string.loading));
            ApiWrap.getEvidences(mType, new SimpleObserver<EvidenceList>() {
                @Override
                public void onError(Throwable e) {
                    UiUtils.dismissProgressDialog(loginDialog);
                    UiUtils.makeText(getString(R.string.timeout_retry));
                    LogUtils.e(TAG, "show_evidence_list exception: " + e.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EdActivity.this.finish();
                        }
                    }, 500);
                }

                @Override
                public void onNext(EvidenceList evidenceList) {
                    LogUtils.i(TAG, "show_evidence_list: " + evidenceList.toString());
                    UiUtils.makeText(evidenceList.getMsg());
                    //设置ToolBar标题
                    mTvTitle.setText(evidenceList.getData().get(mType).getEvidenceName());
                    mRl_evidence.setLayoutManager(new LinearLayoutManager(EdActivity.this));
                    mEvidenceListAdapter = new EvidenceListAdapter(evidenceList.getData());
                    mRl_evidence.setAdapter(mEvidenceListAdapter);
                    UiUtils.dismissProgressDialog(loginDialog);
                }
            });
        }

        return contentView;
    }
}
