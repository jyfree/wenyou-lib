package com.wenyou.baselibrary.helper

import android.Manifest
import android.content.Context
import android.os.Build
import com.wenyou.baselibrary.acp.Acp
import com.wenyou.baselibrary.acp.AcpListener
import com.wenyou.baselibrary.acp.AcpOptions
import com.wenyou.baselibrary.pic.Pic
import com.wenyou.baselibrary.pic.PicListener
import com.wenyou.baselibrary.pic.PicOptions
import com.wenyou.baselibrary.utils.YLogUtils


/**
 * @description 拍照|相册辅助类
 * @date: 2021/12/16 11:58
 * @author: jy
 */
object PicHelper {

    const val TAKE_CAMERA = 1//拍照
    const val TAKE_PICTURE = 2//相册

    /**
     * 注意！！！
     * 若sdk android13及以上，需要注册android.permission.READ_MEDIA_IMAGES权限
     */
    fun takePic(
        context: Context,
        type: Int,
        crop: Boolean,
        rationalMessage: String,
        listener: PicListener
    ) {

        val array = getPermissions(type)
        val picOptions = getPicOptions(crop)

        Acp.getInstance().acpManager
            .setShowRational(true)
            .setAcPermissionOptions(
                AcpOptions.beginBuilder().setPermissions(*array).setRationalMessage(rationalMessage)
                    .build()
            )
            .setAcPermissionListener(object : AcpListener {
                override fun onDenied(permissions: MutableList<String>?) {
                    YLogUtils.e("权限申请--拒绝", permissions?.toString())
                }

                override fun onGranted() {
                    YLogUtils.i("权限申请--同意")
                    if (type == TAKE_CAMERA) {
                        Pic.getInstance().picManager.setPicOptions(picOptions).setListener(listener)
                            .takeCamera(context)

                    } else if (type == TAKE_PICTURE) {
                        Pic.getInstance().picManager.setPicOptions(picOptions).setListener(listener)
                            .takePhotoAlbum(context)
                    }
                }
            })
            .request(context)

    }


    private fun getPermissions(type: Int): Array<String> {
        //适配Android 13
        if (Build.VERSION.SDK_INT >= 33) {
            return if (type == TAKE_CAMERA) {
                arrayOf(
                    "android.permission.READ_MEDIA_IMAGES", Manifest.permission.CAMERA
                )
            } else {
                arrayOf(
                    "android.permission.READ_MEDIA_IMAGES"
                )
            }
        } else {
            return if (type == TAKE_CAMERA) {
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            } else {
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun getPicOptions(crop: Boolean): PicOptions {
        return PicOptions.beginBuilder()
            .setCrop(crop)
            .setCropHeight(200)
            .setCropWidth(200)
            .build()
    }
}
