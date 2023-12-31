package com.wenyou.uidemo;


import com.wenyou.yuilibrary.widget.banner.BannerImageType;
import com.wenyou.yuilibrary.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 演示数据
 * @date: 2021/2/4 17:05
 * @author: jy
 */
public class DemoDataProvider {

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    public static List<BannerItem> getBannerList() {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgObject = urls[i];
            item.title = titles[i];
            item.type = BannerImageType.URL;

            list.add(item);
        }

        return list;
    }

    public static List<Object> getUserGuides() {
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.demo_guide_img_1);
        list.add(R.drawable.demo_guide_img_2);
        list.add(R.drawable.demo_guide_img_3);
        list.add(R.drawable.demo_guide_img_4);
        return list;
    }

}
