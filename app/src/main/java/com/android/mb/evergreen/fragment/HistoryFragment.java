package com.android.mb.evergreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.ExamineAdapter;
import com.android.mb.evergreen.adapter.HistoryAdapter;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.utils.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * HomeFragment
 */

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListener();
    }



    private void initViews(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pullLoadMoreRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mAdapter = new HistoryAdapter(R.layout.item_history,getData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListener(){
    }



    private List<Examine> getData(){
        List<Examine> dataList = new ArrayList<>();
        for (int i=0;i<100;i++){
            Examine examine = new Examine();
            examine.setName("检测编号："+i+"   检测时间："+ Helper.date2String(new Date()));
            dataList.add(examine);
        }
        return dataList;
    }


}
