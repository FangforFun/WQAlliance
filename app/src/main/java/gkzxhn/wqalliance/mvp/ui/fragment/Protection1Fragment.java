package gkzxhn.wqalliance.mvp.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.ui.activity.UploadEdActivity;

/**
 * Created by 方 on 2017/3/3.
 */
public class Protection1Fragment extends BaseContentFragment implements View.OnClickListener {

    private EditText mTheme;//主题
    private RelativeLayout mUploadEd;//上传证据
    private EditText mDesc;//案件详情
    private TextView mCommit;//提交按钮

    @Override
    protected void setTitleData() {
        mIvBack.setVisibility(View.GONE);
        mTvSubtitle.setVisibility(View.GONE);
        mTvTitle.setText("我要维权");
    }

    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_protection, null, false);
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
                startActivity(new Intent(Protection1Fragment.this.getActivity(), UploadEdActivity.class));
                break;
            case R.id.tv_commit:
                UiUtils.makeText("commit");
                //TODO ...提交案件
//                new AlertDialog.Builder(getActivity()).setView(R.layout.custom_dialog)
                break;
        }
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }
}
