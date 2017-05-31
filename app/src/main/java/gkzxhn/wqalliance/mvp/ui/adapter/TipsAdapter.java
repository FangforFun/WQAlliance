package gkzxhn.wqalliance.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

import java.util.List;

import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.TipsClickEvent;
import gkzxhn.wqalliance.mvp.model.entities.TipsData;

/**
 * Created by 方 on 2017/5/27.
 */

public class TipsAdapter extends RecyclerView.Adapter {

    private List<TipsData> mData;
    private View mView;

    public TipsAdapter(List<TipsData> data, View view) {
        mData = data;
        mView = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pop_tips_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final String name = mData.get(position).name;
        final int brandId = mData.get(position).brandId;
        final int goodsId = mData.get(position).goodsId;
        itemViewHolder.mTv_tip_item.setText(name);
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new TipsClickEvent(goodsId, brandId, name, mView));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<TipsData> data) {
        mData = data;
    }

    public void setView(View view) {
        mView = view;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTv_tip_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTv_tip_item = (TextView)itemView.findViewById(R.id.tv_tip_item);
        }
    }
}
