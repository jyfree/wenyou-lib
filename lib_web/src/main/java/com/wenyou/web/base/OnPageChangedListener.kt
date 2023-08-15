package com.wenyou.web.base

import android.graphics.Bitmap
import android.webkit.WebView

interface OnPageChangedListener {
    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    fun onPageFinished(view: WebView?, url: String?)
    fun onProgressChanged(view: WebView?, newProgress: Int)
    fun onReceivedTitle(view: WebView?, title: String?)
    fun onLoadResource(view: WebView?, url: String?)
}