package debug

import android.app.Application
import android.content.Context
import android.os.Process
import androidx.multidex.MultiDex
import com.wenyou.baselibrary.BaseLibraryConfig
import com.wenyou.baselibrary.helper.LogCatHelper
import com.wenyou.baselibrary.thread.LoaderConfiguration
import com.wenyou.baselibrary.utils.AppUtils
import com.wenyou.baselibrary.utils.CrashUtils
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.loadsir.LoadSirExt
import com.wenyou.simpledemo.BuildConfig
import com.wenyou.simpledemo.R
import com.wenyou.sociallibrary.SDKConfig
import com.wenyou.yuilibrary.YUI
import debug.glide.GlideImageLoadStrategySimple


class DemoSimpleApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        val pid = Process.myPid() //进程id
        val process: String? = AppUtils.getCurProcessName(this, pid) //进程名
        if (null == process || applicationContext.packageName == process) {
            //初始化logcat任务
            initLog()
            //初始化基础库
            BaseLibraryConfig.init(this, LoaderConfiguration.beginBuilder().build())
            //初始化社会化SDK
            initSocialSDK()
            //初始化crash捕获
            initCrashUtils()
            //初始化UI组件
            YUI.init(this, GlideImageLoadStrategySimple(), true)
            //初始化菊花
            LoadSirExt.init()
        }
    }

    /**
     * 初始化log
     */
    private fun initLog() {
        LogCatHelper.getInstance().init(
            this,
            LogCatHelper.beginBuilder()
                .setBuffSize(100)//缓存区大小，单位kb
                .setQueueCapacity(500)//缓冲队列大小
                .setCacheDay(1)//缓存1天
                .setShowLog(true)//是否开启log
        )
        LogCatHelper.getInstance().start()
    }

    /**
     * 初始化社会化SDK
     */
    private fun initSocialSDK() {
        SDKConfig.beginBuilder()
            .qqAppID(BuildConfig.TENCENT_VALUE)
            .qqFileProvider("$packageName.FileProvider")
            .wxAppID(BuildConfig.WX_VALUE)
            .wbAppID(BuildConfig.WB_VALUE)
            .wbRedirectUrl("https://api.weibo.com/oauth2/default.html")
            .wbScope("statuses_to_me_read")
            .isShowLog(true)
            .appName(getString(R.string.app_name))
            .build(this)
    }

    /**
     * 初始化crash捕获
     */
    private fun initCrashUtils() {
        CrashUtils.init { crashInfo, _ ->
            YLogUtils.e("异常信息：${crashInfo}")
        }
    }
}