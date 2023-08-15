package com.wenyou.sociallibrary.wx;

/**
 * @description 构造微信支付参数实体类
 * @date: 2021/12/16 14:07
 * @author: jy
 */
public class WXBasicNameValuePair {

    private String name;
    private String value;

    public WXBasicNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
