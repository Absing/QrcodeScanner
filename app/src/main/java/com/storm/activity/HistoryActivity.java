package com.storm.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.storm.App;
import com.storm.R;
import com.storm.adapter.HistoryAdapter;
import com.storm.bean.Result;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        showActionBar();
        showHistory();
    }

    private void showActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showHistory() {
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        Box<Result> resultBox = boxStore.boxFor(Result.class);
        List<Result> all = resultBox.getAll();
        HistoryAdapter historyAdapter = new HistoryAdapter(all);
        RecyclerView recyclerView = findViewById(R.id.recycler_history);
        recyclerView.setAdapter(historyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Result result = all.get(pos);
                resultBox.remove(result);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        };
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(historyAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 开启滑动删除
        historyAdapter.enableSwipeItem();
        historyAdapter.setOnItemSwipeListener(onItemSwipeListener);
        historyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            String content = all.get(position).getContent();
            ClipData clipData = ClipData.newPlainText("com.storm", content);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(HistoryActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}