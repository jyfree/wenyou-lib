package com.wenyou.web.apiModule;

/**
 * @description
 * @date: 2021/12/16 10:43
 * @author: jy
 */
public class ResultData {
    public ResultData() {
    }

    public ResultData(int code) {
        this.code = code;
    }

    public int code = 0;
    public String msg = "";
    public Object data = "";
}
