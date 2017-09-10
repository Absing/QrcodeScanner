package com.storm.adapter;


import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.storm.R;
import com.storm.bean.Result;

import java.util.List;

public class HistoryAdapter extends BaseItemDraggableAdapter<Result, BaseViewHolder> {

    public HistoryAdapter(List<Result> data) {
        super(R.layout.item_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        helper.setText(R.id.tv_result, item.getContent())
                .addOnClickListener(R.id.btn_copy);
    }

}
