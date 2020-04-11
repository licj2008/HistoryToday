package com.yunshuting.historytoday.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yunshuting.historytoday.DetailActivity;
import com.yunshuting.historytoday.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    private List<historyBean> dataList  = new ArrayList<>();
    private Context mContext;

    public HistoryAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<historyBean> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_list_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tvDate.setText(dataList.get(position).date);
        holder.tvTitle.setText(dataList.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("detailId",dataList.get(position).id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTitle;

        private MyHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
