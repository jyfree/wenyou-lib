package com.wenyou.simpledemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.helper.PicHelper
import com.wenyou.baselibrary.pic.PicListener
import com.wenyou.baselibrary.utils.*
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimplePicActivityBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @description 拍照&图库
 * @date: 2021/12/16 16:58
 * @author: jy
 */
class PicSimpleActivity : BaseActivity<SimplePicActivityBinding>(), PicListener {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, PicSimpleActivity::class.java)
        }
    }


    override fun initUI(savedInstanceState: Bundle?) {

    }


    fun take(view: View) {
        when (view.id) {
            R.id.takeCamera -> PicHelper.takePic(
                this,
                PicHelper.TAKE_CAMERA,
                viewBinding.checkBox.isChecked,
                "获取摄像头和sdcard权限",
                this
            )
            R.id.takePhotoAlbum -> PicHelper.takePic(
                this,
                PicHelper.TAKE_PICTURE,
                viewBinding.checkBox.isChecked,
                "获取sdcard权限",
                this
            )
        }
    }

    override fun onTakePicSuccess(uri: Uri?) {

        YLogUtils.i("takePic--uri", uri)

        uri?.let {
            viewBinding.imageView.setImageURI(it)
        }
        //测试压缩图片
        compressBitmap(uri)
    }

    override fun onTakePicFail() {
        YLogUtils.i("takePic--fail")
    }


    //******************************压缩图片********************************************
    @SuppressLint("SimpleDateFormat")
    private val sFormat = SimpleDateFormat("yyMMddHHmmssSSS")

    private val nowTimeStr: String
        get() = sFormat.format(Date())

    private fun compressBitmap(uri: Uri?) {
        if (uri == null) return
        Observable.just(uri)
            .observeOn(Schedulers.io())
            .map {
                val cacheFile = File(FileUtils.getSdcardPath(), "$nowTimeStr.jpg")
                BitmapUtils.imageZoom(BaseUtils.getApp(), it, cacheFile, 80, 800.0)
                cacheFile
            }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(AndroidLifecycleScopeProvider.from(this))
            .subscribe {
                YLogUtils.i("compressBitmap", it.absolutePath)
            }
    }
}