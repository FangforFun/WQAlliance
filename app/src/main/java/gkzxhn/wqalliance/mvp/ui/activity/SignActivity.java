package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.utils.UiUtils;

import common.AppComponent;
import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:签约页面activity
 */
public class SignActivity extends BaseContentActivity implements View.OnClickListener {

    private ImageView iv_upload_trademark;
    private ImageView iv_knowledge_right;
    private EditText et_company_name;
    private LinearLayout ll_sign_contract;

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_sign, null, false);
        iv_upload_trademark = (ImageView) view.findViewById(R.id.iv_upload_trademark);
        iv_knowledge_right = (ImageView) view.findViewById(R.id.iv_knowledge_right);
        et_company_name = (EditText) view.findViewById(R.id.et_company_name);
        ll_sign_contract = (LinearLayout) view.findViewById(R.id.ll_sign_contract);
        return view;
    }

    @Override
    protected void setTitleData() {
        mTvTitle.setText(getString(R.string.sign));
        mTvSubtitle.setVisibility(View.GONE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {}

    @Override
    protected void initData() {
        super.initData();
        iv_upload_trademark.setOnClickListener(this);
        iv_knowledge_right.setOnClickListener(this);
        ll_sign_contract.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_upload_trademark:
                UiUtils.makeText("上传商标");
                break;
            case R.id.iv_knowledge_right:
                UiUtils.makeText("知识产权");
                break;
            case R.id.ll_sign_contract:
                UiUtils.makeText("签订合同");
                break;
        }
    }
}
