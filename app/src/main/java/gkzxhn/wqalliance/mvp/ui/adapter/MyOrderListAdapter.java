package gkzxhn.wqalliance.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.ui.activity.CheckProcessActivity;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:我的订单列表适配器
 */
public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>{

    private Context mContext;
    /**
     * 列表类型
     * 1  待审核
     * 2  处理中
     * 3  待支付
     * 4  已完成
     */
    private int type;

    public MyOrderListAdapter(Context context, int type){
        this.mContext = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        setItemStatus(holder);
        holder.tv_label1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查看流程
                UiUtils.makeText("查看流程： " + position);
                UiUtils.startActivity(new Intent(mContext, CheckProcessActivity.class));
            }
        });
        holder.tv_label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 支付订单
                UiUtils.makeText("支付订单： " + position);
            }
        });
    }

    private void setItemStatus(MyViewHolder holder) {
        holder.tv_total_spend.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
        holder.tv_label2.setVisibility(type == 3 ? View.VISIBLE : View.GONE);
        switch (type){
            case 1:// 待审核
                holder.tv_label1.setBackgroundResource(R.drawable.theme_border_bg);
                holder.tv_label1.setText("审核中");
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.B));
                break;
            case 2:// 处理中
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText("查看流程");
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            case 3:// 待支付
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText("查看流程");
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                holder.tv_label2.setBackgroundResource(R.drawable.orange_bg);
                holder.tv_label2.setText("支付订单");
                holder.tv_label2.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            case 4:// 已完成
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText("查看流程");
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    /**
     * 切换列表
     * @param type
     */
    public void switchList(int type){
        this.type = type;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_order_title;
        private TextView tv_order_time;
        private TextView tv_total_spend;
        private TextView tv_label1;
        private TextView tv_label2;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_order_title = (TextView) itemView.findViewById(R.id.tv_order_title);
            tv_order_time = (TextView) itemView.findViewById(R.id.tv_order_time);
            tv_total_spend = (TextView) itemView.findViewById(R.id.tv_total_spend);
            tv_label1 = (TextView) itemView.findViewById(R.id.tv_label1);
            tv_label2 = (TextView) itemView.findViewById(R.id.tv_label2);
        }
    }
}
