package com.wenyou.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding
import com.wenyou.baselibrary.loadsir.ILoading
import com.wenyou.baselibrary.loadsir.LoadSirManager
import com.wenyou.baselibrary.utils.ToastUtils

/**
 * @description viewBinding DialogFragment基类
 * @date: 2021/4/16 10:19
 * @author: jy
 */
abstract class BaseDialogFragment<VB : ViewBinding> : AppCompatDialogFragment() {

    protected lateinit var viewBinding: VB

    //菊花
    val loadingAgent: ILoading? by lazy { LoadSirManager.get().initLoading() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ViewBindingCreator.createViewBinding(this, inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLoadSir()
        initUI(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        ToastUtils.cancelToast()
    }

    /**
     * 初始化UI
     */
    protected abstract fun initUI(view: View?, savedInstanceState: Bundle?)


    /**
     * 注册菊花
     */
    protected open fun registerLoadSir() {}

}