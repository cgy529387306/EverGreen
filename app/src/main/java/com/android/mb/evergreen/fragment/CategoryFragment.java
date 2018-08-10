package com.android.mb.evergreen.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.CategoryAdapter;
import com.android.mb.evergreen.adapter.ExamineAdapter;
import com.android.mb.evergreen.db.GreenDaoManager;
import com.android.mb.evergreen.entity.Category;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.utils.DialogHelper;
import com.android.mb.evergreen.utils.Helper;
import com.android.mb.evergreen.utils.ToastHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * HomeFragment
 */

public class CategoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListener();
        refreshData();
    }



    private void initViews(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pullLoadMoreRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mAdapter = new CategoryAdapter(R.layout.item_category,new ArrayList());
        mAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_category, null));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListener(){
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Category category = mAdapter.getData().get(position);
                changeData(category);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Category category = mAdapter.getData().get(position);
                showDeleteAlert(category);
                return false;
            }
        });
    }

    private void showDeleteAlert(final Category category){
        SpannableStringBuilder ssb = new SpannableStringBuilder("确定要删除" + category.getName() + "吗?");
        ssb.setSpan(new ForegroundColorSpan(Color.RED),5,5+category.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(getActivity()).setMessage(ssb)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GreenDaoManager.getInstance().getNewSession().getCategoryDao().delete(category);
                        refreshData();
                    }
                }).setNegativeButton("取消",null).show();
    }

    public void refreshData(){
        List<Category> dataList = GreenDaoManager.getInstance().getNewSession().getCategoryDao().queryBuilder().limit(20).build().list();
        mAdapter.setNewData(dataList);
    }


    public void changeData(final Category category){
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_edit, null);//这里必须是final的
        final EditText et = (EditText) view.findViewById(R.id.et_input);
        et.setText(category.getName()==null?"":category.getName());
        et.setSelection(category.getName()==null?0:category.getName().length());
        new AlertDialog.Builder(getActivity()).setTitle("修改检测类型")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = et.getText().toString();
                        if (TextUtils.isEmpty(name)){
                            ToastHelper.showToast("请输入检测类型");
                        }else{
                            category.setName(name);
                            GreenDaoManager.getInstance().getNewSession().getCategoryDao().update(category);
                            refreshData();
                        }
                    }
                }).setNegativeButton("取消",null).show();
    }


}
