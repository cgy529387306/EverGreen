package com.android.mb.evergreen.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.entity.Category;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {

    public CategoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Category item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_date,item.getInsertDate());
        CheckBox checkBox = helper.getView(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setChecked(b);
            }
        });
    }
}