package com.android.mb.evergreen.adapter;

import com.android.mb.evergreen.entity.Examine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<Examine, BaseViewHolder> {

    public HistoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Examine item) {

    }
}