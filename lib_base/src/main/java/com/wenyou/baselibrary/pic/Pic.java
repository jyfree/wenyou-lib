package com.wenyou.baselibrary.pic;

/**
 * @description
 * @date: 2021/12/16 13:34
 * @author: jy
 */
public class Pic {
    private PicManager picManager;

    private Pic() {
        picManager = new PicManager();
    }

    public static synchronized Pic getInstance() {
        return PicHolder.instance;
    }

    private static class PicHolder {
        private static final Pic instance = new Pic();
    }

    public PicManager getPicManager() {
        return picManager;
    }
}
