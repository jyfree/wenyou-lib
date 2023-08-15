package com.wenyou.sociallibrary.bean;

/**
 * @description 分享渠道实体类
 * @date: 2021/12/16 13:58
 * @author: jy
 */
public class SDKShareChannel {

    private int platform;
    private int icon;
    private String name;

    public SDKShareChannel(int platform, int icon, String name) {
        this.platform = platform;
        this.icon = icon;
        this.name = name;
    }


    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
