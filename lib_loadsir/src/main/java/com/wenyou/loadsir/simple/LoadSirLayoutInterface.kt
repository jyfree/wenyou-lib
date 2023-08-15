package com.wenyou.loadsir.simple

/**
 * 实现此接口可自定义callback的layout
 */
interface LoadSirLayoutInterface {
    fun emptyLayout(): Int?
    fun errorLayout(): Int?
    fun loadingLayout(): Int?
}