package com.android.mb.evergreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.mb.evergreen.R;
import com.android.mb.evergreen.adapter.ExamineAdapter;
import com.android.mb.evergreen.entity.Examine;
import com.android.mb.evergreen.utils.Helper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * HomeFragment
 */

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ExamineAdapter mAdapter;
    private int mCurrentPage = 1;
    private List<Examine> mDateList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mAdapter = new ExamineAdapter(R.layout.item_examine,getData());
        mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null));
//        mAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setListener(){
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentPage >= 10) {
                            //数据全部加载完毕
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.addData(getData());
                            mAdapter.loadMoreComplete();
                            mCurrentPage++;
                        }
                    }

                }, 1000);

            }
        },mRecyclerView);
    }



    private List<Examine> getData(){
        List<Examine> dataList = new ArrayList<>();
        int i = mDateList.size();
        int j = mDateList.size()+10;
        for (;i<j;i++){
            Examine examine = new Examine();
            examine.setName("检测编号："+i+"   检测时间："+Helper.date2String(new Date()));
            dataList.add(examine);
        }
        return dataList;
    }


}
