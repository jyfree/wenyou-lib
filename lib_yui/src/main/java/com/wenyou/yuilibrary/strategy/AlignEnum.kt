package com.wenyou.yuilibrary.strategy

/**
 * @description 对齐方式
 * @date: 2021/2/4 15:29
 * @author: jy
 */
enum class AlignEnum {
    /**
     * 默认方式
     */
    DEFAULT,
    /**
     * 中心裁剪（将图片放在ImageView的中心点，然后对图片进行等比例缩放，等比例缩放图片的宽和高均不小于控件对应的宽高）
     */
    CENTER_CROP,
    /**
     * 中心填充（将图片放在ImageView的中心点，然后对图片进行等比例缩放,等比例缩放图片的宽和高均不大于控件对应的宽高）
     */
    CENTER_INSIDE,
    /**
     * 中心适应（将图片放在ImageView的中心点，对图片进行等比例缩放从而完整地显示图片，使得图片的宽高中至少有一个值恰好等于控件的宽或者高）
     */
    FIT_CENTER
}