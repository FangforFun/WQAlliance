package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.ClickEvent;

/**
 * Created by 方 on 2017/3/6.
 */

public class ProtectionActivity extends BaseContentActivity implements View.OnClickListener {
    private EditText mTheme;//主题
    private RelativeLayout mUploadEd;//上传证据
    private EditText mDesc;//案件详情
    private TextView mCommit;//提交按钮

//    @Nullable
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText("我要维权");
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.fragment_protection, null, false);
        mTheme = (EditText) contentView.findViewById(R.id.et_theme);
        mUploadEd = (RelativeLayout) contentView.findViewById(R.id.rl_upload_ed);
        mDesc = (EditText) contentView.findViewById(R.id.desc);
        mCommit = (TextView) contentView.findViewById(R.id.tv_commit);

        mUploadEd.setOnClickListener(this);
        mCommit.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_upload_ed:
                startActivity(new Intent(this, UploadEdActivity.class));
                break;
            case R.id.tv_commit:
//                UiUtils.makeText("commit");
                //TODO ...提交案件
//                new AlertDialog.Builder(getActivity()).setView(R.layout.custom_dialog)
                EventBus.getDefault().post(new ClickEvent());
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
