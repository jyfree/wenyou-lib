package com.wenyou.simpledemo.ui.http

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.ext.livedata.observeState
import com.wenyou.baselibrary.loadsir.OnLoadSirListener
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleApiTestActivityBinding
import com.wenyou.simpledemo.repository.CrUserRepository
import com.wenyou.simpledemo.viewmodel.SimpleCrViewModel
import com.wenyou.simpledemo.viewmodel.SimpleCrViewModelFactory

/**
 * @description 请求接口
 * @date: 2021/8/23 17:35
 * @author: jy
 */
class ApiSimpleActivity : BaseActivity<SimpleApiTestActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ApiSimpleActivity::class.java)
        }
    }


    /**
     * //viewModels扩展，若是默认Factory，则无需重写
     *  private val viewModel: SimpleCrViewModel by viewModels {
     *      SimpleCrViewModelFactory(
     *      CrUserRepository())}
     */
    private val viewModel: SimpleCrViewModel by lazy {
        ViewModelProvider(
            this,
            SimpleCrViewModelFactory(CrUserRepository())
        ).get(SimpleCrViewModel::class.java)
    }

    override fun initUI(savedInstanceState: Bundle?) {
        Glide.with(this)
            .load("https://cdn.xiaojingzb.com/images/anchor/cutImage/20230221/167695835793373.jpg")
            .placeholder(R.drawable.simple_load_default_image)
            .error(R.drawable.simple_load_default_image)
            .centerCrop()
            .into(viewBinding.imageView)
        viewModel.getLoading().observe(this, Observer {
            loadingAgent?.showLoadingView(it)
        })
        viewModel.getData().observe(this, Observer {
            viewBinding.tvMsg.text = it
        })
        //模拟状态机
        viewModel.getStatefulLiveData().observeState(this) {
            onLoading = {
                loadingAgent?.showLoadingCoverView(it)
            }
            onSuccess = {
                viewBinding.tvMsg.text = it
            }
            onError = { errorMsg, exception ->
                loadingAgent?.showErrorView()
//                loadingAgent?.getLoad()?.let {
//                    if (it is LoadService<*>) {
//                        it.setCallBack(ErrorCallback::class.java) { context, view ->
//                            val mTvEmpty = view.findViewById<TextView>(R.id.loadSir_error_content)
//                            mTvEmpty.text = "code:${errorMsg},msg:${exception?.message}"
//                        }
//                    }
//                }
            }
        }
    }

    fun onRequest(view: View) {
        when (view.id) {
            R.id.requestData -> viewModel.getCode("18613042029")
            R.id.testRequest -> {
                viewModel.requestData()
            }
        }
    }

    override fun registerLoadSir() {
        loadingAgent?.register(viewBinding.tvMsg, object : OnLoadSirListener {
            override fun onReload(v: View?) {
                viewModel.requestData()
            }
        })
    }


}