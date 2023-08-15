package com.wenyou.simpledemo.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import com.wenyou.baselibrary.acp.Acp
import com.wenyou.baselibrary.acp.AcpListener
import com.wenyou.baselibrary.acp.AcpOptions
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.ToastUtils
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.databinding.SimpleSupplyActivityBinding
import com.wenyou.simpledemo.download.ApkSimpleDownloadServer


/**
 * @description 上传下载服务
 * @date: 2021/12/16 16:58
 * @author: jy
 */
class SupplySimpleActivity : BaseActivity<SimpleSupplyActivityBinding>() {

    private var apkSimpleDownloadServer: ApkSimpleDownloadServer? = null

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, SupplySimpleActivity::class.java)
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        viewBinding.downloadApk.setOnClickListener {
            startSysDownload()
        }
        apkSimpleDownloadServer = ApkSimpleDownloadServer(this)
    }

    private fun startSysDownload() {
        Acp.getInstance().acpManager
            .setShowRational(true)
            .setAcPermissionOptions(
                AcpOptions.beginBuilder().setPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
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
                    apkSimpleDownloadServer?.startDownload(
                        "http://cdn2.xiaojingzb.com/apk/android/xiaojingzb_10.3.8.apk", null
                    )
                }
            })
            .request(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        apkSimpleDownloadServer?.release()
    }
}