package com.wenyou.simpledemo.ui.http

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.YHandlerUtils
import com.wenyou.http.rxjava.RxApiManager
import com.wenyou.simpledemo.Constants
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleApiTestActivityBinding
import com.wenyou.simpledemo.repository.RxUserRepository
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModel
import com.wenyou.simpledemo.viewmodel.SimpleRxViewModelFactory

/**
 * @description 请求接口
 * @date: 2021/8/23 17:35
 * @author: jy
 */
class ApiRxJavaSimpleActivity : BaseActivity<SimpleApiTestActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ApiRxJavaSimpleActivity::class.java)
        }
    }


    private val viewModel: SimpleRxViewModel by lazy {
        ViewModelProvider(
            this,
            SimpleRxViewModelFactory(RxUserRepository())
        ).get(SimpleRxViewModel::class.java)
    }

    override fun initUI(savedInstanceState: Bundle?) {
        Glide.with(this)
            .load("https://cdn.xiaojingzb.com/images/anchor/cutImage/20230221/167695835793373.jpg")
            .placeholder(R.drawable.simple_load_default_image)
            .error(R.drawable.simple_load_default_image)
            .centerCrop()
            .into(viewBinding.imageView)
        viewModel.getData().observe(this, Observer {
            viewBinding.tvMsg.text = it
        })
    }

    fun onRequest(view: View) {
        when (view.id) {
            R.id.requestData -> viewModel.getCode("18613042029")
            R.id.testRequest -> {
                //模拟请求
                loadingAgent?.showLoadingView(true)
                YHandlerUtils.runOnBackThread(Runnable {
                    YHandlerUtils.runOnUiThread(Runnable {
                        loadingAgent?.showLoadingView(false)
                    })
                }, 2)
                //模拟tag取消Rx
                RxApiManager.get().cancel(Constants.SIMPLE_API_TAG)
            }
        }
    }

    override fun registerLoadSir() {
        loadingAgent?.register(viewBinding.tvMsg)
    }


}