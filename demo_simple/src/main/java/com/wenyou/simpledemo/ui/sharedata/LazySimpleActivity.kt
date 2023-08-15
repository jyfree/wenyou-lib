package com.wenyou.simpledemo.ui.sharedata

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wenyou.baselibrary.adapter.MyFragmentPagerAdapter
import com.wenyou.baselibrary.base.BaseFragmentActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.simpledemo.databinding.SimpleLazyActivityBinding
import java.util.*


/**
 * @description viewModel数据共享
 * @date: 2021/8/23 19:40
 * @author: jy
 */
class LazySimpleActivity : BaseFragmentActivity<SimpleLazyActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, LazySimpleActivity::class.java)
        }
    }


    private val list = ArrayList<Fragment>()
    private val title = arrayOf("Android", "iOS")

    override fun initUI(savedInstanceState: Bundle?) {

        list.add(LazySimpleFragmentOne.newInstance())
        list.add(LazySimpleFragmentTwo.newInstance())

        for (element in title) {
            viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText(element))
        }
        val adapter = MyFragmentPagerAdapter(supportFragmentManager, list, title)
        viewBinding.viewPager.adapter = adapter
        viewBinding.viewPager.offscreenPageLimit = list.size

        viewBinding.tabLayout.setupWithViewPager(viewBinding.viewPager)
        viewBinding.tabLayout.setTabsFromPagerAdapter(adapter)

    }
}