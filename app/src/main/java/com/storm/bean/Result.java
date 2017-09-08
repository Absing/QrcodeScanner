package com.storm.bean;

import java.io.Serializable;

/**
 * Created by Ding on 2017/9/7.
 */

public class Result implements Serializable {
    public Result(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String content;
    public int type;

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
