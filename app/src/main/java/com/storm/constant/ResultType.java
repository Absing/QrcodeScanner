package com.storm.constant;

/**
 * Created by Ding on 2017/9/7.
 */

public interface ResultType {
    int NORMAL = 0;//纯文本
    int URL = 1;//一般是指 http://  或者 https://
    int EMAIL = 2;//邮箱
}
