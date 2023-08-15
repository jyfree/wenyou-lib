package com.wenyou.baselibrary.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.wenyou.baselibrary.channel.ChannelToolbar
import com.wenyou.baselibrary.channel.ChannelToolbarStyle
import com.wenyou.baselibrary.channel.ChannelToolbarStyleDelegate
import com.wenyou.baselibrary.loadsir.ILoading
import com.wenyou.baselibrary.loadsir.LoadSirManager
import com.wenyou.baselibrary.utils.ToastUtils

/**
 * @description ViewBinding FragmentActivity基类
 * @date: 2021/4/16 10:24
 * @author: jy
 */
abstract class BaseFragmentActivity<VB : ViewBinding> : FragmentActivity() {
    protected lateinit var viewBinding: VB

    //状态栏代理
    private var channelStyle: ChannelToolbarStyle by ChannelToolbarStyleDelegate()

    //菊花
    val loadingAgent: ILoading? by lazy { LoadSirManager.get().initLoading() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ViewBindingCreator.createViewBinding(this, layoutInflater)
        channelStyle = getToolBarStyle()
        setContentView(viewBinding.root)
        registerLoadSir()
        initUI(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        ToastUtils.cancelToast()
    }

    /**
     * 初始化UI
     */
    protected abstract fun initUI(savedInstanceState: Bundle?)


    /**
     * 注册菊花
     */
    protected open fun registerLoadSir() {}

    /**
     * 可重写此方法，修改状态栏样式
     */
    open fun getToolBarStyle(): ChannelToolbarStyle =
        ChannelToolbar.get().defaultToolbarStyle() ?: ChannelToolbarStyle.WHITE
}