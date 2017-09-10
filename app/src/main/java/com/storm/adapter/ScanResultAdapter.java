package com.storm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.storm.R;
import com.storm.bean.Result;

import java.util.List;

public class ScanResultAdapter extends BaseQuickAdapter<Result, BaseViewHolder> {

    public ScanResultAdapter(int layoutResId, List<Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        helper.setText(R.id.tv_result, item.getContent())
                .addOnClickListener(R.id.btn_copy);
    }

}
