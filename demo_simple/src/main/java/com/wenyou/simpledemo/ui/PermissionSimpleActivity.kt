package com.wenyou.simpledemo.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import com.wenyou.baselibrary.acp.Acp
import com.wenyou.baselibrary.acp.AcpListener
import com.wenyou.baselibrary.acp.AcpOptions
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.ToastUtils
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.databinding.SimplePermissionActivityBinding
import java.io.File


/**
 * @description 权限申请示例
 * @date: 2021/12/16 16:58
 * @author: jy
 */
class PermissionSimpleActivity : BaseActivity<SimplePermissionActivityBinding>() {
    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, PermissionSimpleActivity::class.java)
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        viewBinding.getSdcard.setOnClickListener {
            requestSdcard()
        }
        viewBinding.getCamera.setOnClickListener {
            requestCamera()
        }
        viewBinding.getAll.setOnClickListener {
            requestAll()
        }
    }

    private fun requestAll() {
        Acp.getInstance().acpManager
            .setShowRational(true)
            .setAcPermissionOptions(
                AcpOptions.beginBuilder().setPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                ).build()
            )
            .setAcPermissionListener(object : AcpListener {
                override fun onDenied(permissions: MutableList<String>?) {
                    YLogUtils.e("权限申请--拒绝", permissions?.toString())
                    ToastUtils.showToast(
                        "权限申请--拒绝" + permissions?.toString()
                    )

                }

                override fun onGranted() {
                    YLogUtils.i("权限申请--同意")
                    getAll()
                }
            })
            .request(this)
    }

    private fun requestSdcard() {
        Acp.getInstance().acpManager
            .setShowRational(false)
            .setAcPermissionOptions(
                AcpOptions.beginBuilder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setCanShowDeniedDialog(
                        false
                    ).build()
            )
            .setAcPermissionListener(object : AcpListener {
                override fun onDenied(permissions: MutableList<String>?) {
                    YLogUtils.e("权限申请--拒绝", permissions?.toString())
                    ToastUtils.showToast(
                        "权限申请--拒绝" + permissions?.toString()
                    )
                }

                override fun onGranted() {
                    YLogUtils.i("权限申请--同意")
                    writeSD()
                }
            })
            .request(this)
    }

    private fun requestCamera() {
        Acp.getInstance().acpManager
            .setShowRational(true)
            .setAcPermissionOptions(
                AcpOptions.beginBuilder().setPermissions(Manifest.permission.CAMERA)
                    .build()
            )
            .setAcPermissionListener(object : AcpListener {
                override fun onDenied(permissions: MutableList<String>?) {
                    YLogUtils.e("权限申请--拒绝", permissions?.toString())
                    ToastUtils.showToast(
                        "权限申请--拒绝" + permissions?.toString()
                    )
                }

                override fun onGranted() {
                    YLogUtils.i("权限申请--同意")
                    getCamera()
                }
            })
            .request(this)
    }


    private fun getAll() {
        val acpDir = getCacheDir("acp", this)
        ToastUtils.showToast("读摄像头成功\n写SD成功：$acpDir")
    }

    private fun getCamera() {
        ToastUtils.showToast("读摄像头成功")
    }

    private fun writeSD() {
        val acpDir = getCacheDir("acp", this)
        acpDir?.let { ToastUtils.showToast("写SD成功：" + acpDir.absolutePath) }

    }

    private fun getCacheDir(dirName: String, context: Context): File? {
        val result: File = if (existsSdcard() == true) {
            val cacheDir = context.externalCacheDir
            if (cacheDir == null) {
                File(
                    Environment.getExternalStorageDirectory(),
                    context.packageName + "/cache/" + dirName
                )
            } else {
                File(cacheDir, dirName)
            }
        } else {
            File(context.cacheDir, dirName)
        }
        return if (result.exists() || result.mkdirs()) {
            result
        } else {
            null
        }
    }

    private fun existsSdcard(): Boolean? {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}