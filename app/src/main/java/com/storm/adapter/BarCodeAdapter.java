package com.storm.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class BarCodeAdapter extends BaseAdapter {

    private ArrayList<String> list;

    private static volatile BarCodeAdapter mInstance;

    private Activity activity;
    private Handler handler;

    private BarCodeAdapter() {
    }

    public static BarCodeAdapter getInstance() {
        if (mInstance == null) {
            synchronized (BarCodeAdapter.class) {
                if (mInstance == null) {
                    mInstance = new BarCodeAdapter();
                }
            }
        }
        return mInstance;
    }

    public void init(Activity activity, Handler handler) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(activity);
        textView.setText(getItem(i));
        textView.setTextColor(Color.BLACK);
        textView.setPadding(20,10,10,10);
        textView.setTextSize(16);
        return textView;
    }

    private static final String TAG = "BarCodeAdapter";

    public void add(String result) {
        if (!list.contains(result)) {
            list.add(result);
            Log.e(TAG, "add: " + result);
            handler.sendEmptyMessage(1);
        }
    }
}
