package com.wenyou.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.wenyou.baselibrary.loadsir.ILoading
import com.wenyou.baselibrary.loadsir.LoadSirManager
import com.wenyou.baselibrary.utils.ToastUtils

/**
 * @description ViewBinding fragment基类
 * @date: 2021/4/16 10:22
 * @author: jy
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    companion object {
        private const val STATE_SAVE_IS_HIDDEN = "state_save_is_hidden"
    }

    protected lateinit var viewBinding: VB

    //菊花
    val loadingAgent: ILoading? by lazy { LoadSirManager.get().initLoading() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ViewBindingCreator.createViewBinding(
            this, inflater, container
        )
        if (savedInstanceState != null) {
            val isSupportHidden =
                savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft =
                fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLoadSir()
        initUI(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(
            STATE_SAVE_IS_HIDDEN,
            isHidden
        )
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