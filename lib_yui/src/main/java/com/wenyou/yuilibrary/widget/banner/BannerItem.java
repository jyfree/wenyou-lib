package com.wenyou.yuilibrary.widget.banner;


/**
 * @description 图片轮播条目
 * @date: 2021/2/5 14:08
 * @author: jy
 */
public class BannerItem {
    public int id;
    public String title;
    public Object imgObject;
    public int type = BannerImageType.URL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImgObject() {
        return imgObject;
    }

    public void setImgObject(Object imgObject) {
        this.imgObject = imgObject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public BannerItem setTitle(String title) {
        this.title = title;
        return this;
    }
}
