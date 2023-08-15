package com.wenyou.simpledemo.ui.sharedata


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wenyou.baselibrary.base.BaseLazyFragment
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.databinding.SimpleLazyFragmentOneBinding
import com.wenyou.simpledemo.repository.RxUserRepository
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModel
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModelFactory

/**
 * @description
 * @date: 2021/8/23 19:31
 * @author: jy
 */
class LazySimpleFragmentOne : BaseLazyFragment<SimpleLazyFragmentOneBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): LazySimpleFragmentOne {
            return LazySimpleFragmentOne()
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

        viewBinding.requestData.setOnClickListener {
            viewModel.getCode("10086")
        }
    }

    override fun lazyLoad() {
        YLogUtils.i("lazyLoad：${javaClass.simpleName}")
    }

    override fun visibleToUser() {
        YLogUtils.i("visibleToUser：${javaClass.simpleName}")
    }


}