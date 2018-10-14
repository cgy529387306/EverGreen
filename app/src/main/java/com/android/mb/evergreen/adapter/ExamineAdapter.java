package com.android.mb.evergreen.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    protected void convert(BaseViewHolder helper, final Examine item) {
        helper.setText(R.id.tv_date,item.getTestDate());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_result,item.getResult());
        CheckBox checkBox = helper.getView(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setChecked(b);
            }
        });
    }

}