package gkzxhn.wqalliance.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;

import java.util.List;

import gkzxhn.utils.DateUtils;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.entities.OrderResult;
import gkzxhn.wqalliance.mvp.ui.activity.CheckProcessActivity;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:我的订单列表适配器
 */
public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>{

    private static final String TAG = "MyOrderListAdapter";
    private Context mContext;
    /**
     * 列表类型
     * 0  待审核
     * 1  处理中
     * 2  待支付
     * 3  已支付
     * 4  已完成
     */
    private int type;
    private List<OrderResult.DataBean> mDataList;

    public MyOrderListAdapter(Context context, int type, List<OrderResult.DataBean> list){
        this.mContext = context;
        this.type = type;
        this.mDataList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_order_title.setText(mDataList.get(position).getTitle());
        holder.tv_order_time.setText(DateUtils.getTimeString(mDataList.get(position).getCreatedAt()));
        if (type == 3){
            Log.i(TAG, "onBindViewHolder: mdataList...." + mDataList.get(position));
            holder.tv_total_spend.setText("总计费用：" + mDataList.get(position).getMoney());
        }
        setItemStatus(holder);
        holder.tv_label1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查看流程
                if (type == 0) return;
//                UiUtils.makeText("查看流程： " + position);
                Intent intent = new Intent(mContext, CheckProcessActivity.class);
                intent.putExtra("orderId", mDataList.get(position).getId());
                LogUtils.i(TAG, "orderId is : " + mDataList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        //TODO. .. .
        /*holder.tv_label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 支付订单
                if (type == 3) {
                    // 待支付  去往支付页面
//                    UiUtils.makeText("支付订单： " + position);
                    Intent intent = new Intent(mContext, PayWaysActivity.class);
                    intent.putExtra("orderId", mDataList.get(position).getId());
                    intent.putExtra("money", mDataList.get(position).getMoney());
                    LogUtils.i(TAG, "orderId is: " + mDataList.get(position).getId()
                            + ", money is : " + mDataList.get(position).getMoney());
                    mContext.startActivity(intent);
                }
            }
        });*/
    }

    private void setItemStatus(MyViewHolder holder) {
        holder.tv_total_spend.setVisibility(type == 0 || type == 2 ? View.GONE : View.VISIBLE);
        holder.tv_label2.setVisibility(type == 3 ? View.VISIBLE : View.GONE);
        switch (type){
            case 0:// 待审核
                holder.tv_label1.setBackgroundResource(R.drawable.theme_border_bg);
                holder.tv_label1.setText(R.string.examming);
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.B));
                break;
            case 2:// 处理中
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText(R.string.check_process);
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            case 3:// 待支付
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText(R.string.check_process);
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                holder.tv_label2.setBackgroundResource(R.drawable.orange_bg);
                holder.tv_label2.setText(R.string.pay_order);
                holder.tv_label2.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            case 4:// 已支付
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText(R.string.check_process);
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            case 5:// 已完成
                holder.tv_label1.setBackgroundResource(R.drawable.theme_bg);
                holder.tv_label1.setText(R.string.check_process);
                holder.tv_label1.setTextColor(mContext.getResources().getColor(R.color.W));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 切换列表
     * @param type
     */
    public void switchList(int type, List<OrderResult.DataBean> data){
        this.type = type;
        this.mDataList = data;
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