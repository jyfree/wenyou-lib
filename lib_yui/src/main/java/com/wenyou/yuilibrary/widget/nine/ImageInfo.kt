package com.wenyou.yuilibrary.widget.nine

/**
 * @description
 * @date: 2021/12/16 14:23
 * @author: jy
 */
data class ImageInfo(
    val thumbnailUrl: String?,
    val bigImageUrl: String?,
    val imageViewHeight: Int,
    val imageViewWidth: Int,
    val imageViewX: Int,
    val imageViewY: Int
) {
    constructor(thumbnailUrl: String, bigImageUrl: String) : this(
        thumbnailUrl,
        bigImageUrl,
        0,
        0,
        0,
        0
    )
}
