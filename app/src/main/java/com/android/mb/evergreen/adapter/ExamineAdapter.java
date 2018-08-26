package com.android.mb.evergreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.android.mb.evergreen.R;
import com.android.mb.evergreen.entity.Examine;

import java.util.ArrayList;
import java.util.List;

public class ExamineAdapter extends RecyclerView.Adapter<ExamineAdapter.ViewHolder> {

    private Context mContext;
    private List<Examine> mDataList = new ArrayList<>();

    public List<Examine> getDataList() {
        return mDataList;
    }

    public void addData(List<Examine> dataList) {
        if (dataList!=null){
            this.mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        this.mDataList.clear();
    }

    public ExamineAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_examine, parent, false);
        final ViewHolder viewHolder=new ViewHolder(v);
        //使用view的条目点击事件
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置监听
                if (listener != null) {
                    listener.onRecyclerViewItemClick((int)v.getTag());
                }
            }
        });
        return new ViewHolder(v);
    }

    private OnRrecyclerViewItemClickListener listener;

    //定义接口 和抽象方法
    public interface OnRrecyclerViewItemClickListener {
        void onRecyclerViewItemClick(int position);
    }

    //提供设置监听的方法
    public void setOnRrecyclerViewItemClickListener(OnRrecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.tv_title.setText(mDataList.get(position).getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataList==null?0:mDataList.size();
    }

}