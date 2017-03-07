package gkzxhn.wqalliance.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import common.AppComponent;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.widget.TimelineView;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:查看流程页面activity
 */
public class CheckProcessActivity extends BaseContentActivity {

    private TimelineView tlv_pass;

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
        tlv_pass = (TimelineView) view.findViewById(R.id.tlv_pass);
        tlv_pass.setMarker(getResources().getDrawable(R.drawable.marker_pass));
        return view;
    }
}
