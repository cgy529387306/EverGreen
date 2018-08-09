package com.android.mb.evergreen.adapter;

import android.util.Log;
import android.view.ViewGroup;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.entity.Examine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ExamineAdapter extends BaseQuickAdapter<Examine, BaseViewHolder> {

    public ExamineAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Examine item) {
        helper.setText(R.id.tv_title,item.getName());
    }

}