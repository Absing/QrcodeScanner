package com.storm.bean;

import android.text.TextUtils;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * Created by Ding on 2017/9/7.
 */
@Entity
public class Result implements Serializable {

    public Result() {
    }

    public Result(String content, int type) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        this.content = content;
        this.type = type;
    }

    @Id(assignable = false)
    private long id = 0;
    @Index
    private String content;
    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        return content != null ? content.equals(result.content) : result.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
