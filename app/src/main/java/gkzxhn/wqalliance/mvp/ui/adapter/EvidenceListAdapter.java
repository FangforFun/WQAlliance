package gkzxhn.wqalliance.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.EvidenceList;

/**
 * Created by 方 on 2017/3/9.
 */

public class EvidenceListAdapter extends RecyclerView.Adapter{

    private final List<EvidenceList.DataBean> mData;

    public EvidenceListAdapter(List<EvidenceList.DataBean> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvidenceItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_evidence_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EvidenceItemViewHolder viewHolder = (EvidenceItemViewHolder) holder;
        viewHolder.mTv_evidence_name.setText(mData.get(position).getEvidenceName());
        viewHolder.upload_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ...上传照片
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class EvidenceItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView upload_ed;
        private final TextView mTv_evidence_name;

        public EvidenceItemViewHolder(View itemView) {
            super(itemView);
            upload_ed = (ImageView) itemView.findViewById(R.id.iv_upload_ed);
            mTv_evidence_name = (TextView) itemView.findViewById(R.id.tv_evidence_name);
        }
    }
}
