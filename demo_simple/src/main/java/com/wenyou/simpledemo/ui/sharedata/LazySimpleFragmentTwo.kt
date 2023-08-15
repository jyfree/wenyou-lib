package com.wenyou.simpledemo.ui.sharedata

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wenyou.baselibrary.base.BaseLazyFragment
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.databinding.SimpleLazyFragmentTwoBinding
import com.wenyou.simpledemo.repository.RxUserRepository
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModel
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModelFactory


/**
 * @description
 * @date: 2021/8/23 19:37
 * @author: jy
 */
class LazySimpleFragmentTwo : BaseLazyFragment<SimpleLazyFragmentTwoBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): LazySimpleFragmentTwo {
            return LazySimpleFragmentTwo()
        }
    }

    private val viewModel: SimpleRxViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            SimpleRxViewModelFactory(RxUserRepository())
        ).get(SimpleRxViewModel::class.java)
    }


    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        viewModel.getData().observe(this, Observer<String> {
            viewBinding.tvMsg.text = it
        })
    }

    override fun lazyLoad() {
        YLogUtils.i("lazyLoad：${javaClass.simpleName}")
    }

    override fun visibleToUser() {
        YLogUtils.i("visibleToUser：${javaClass.simpleName}")
    }

}