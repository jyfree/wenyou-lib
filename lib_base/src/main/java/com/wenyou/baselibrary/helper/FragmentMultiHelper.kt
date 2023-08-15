package com.wenyou.baselibrary.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.wenyou.baselibrary.utils.YLogUtils

/**
 * @description 多fragment
 * @date: 2021/11/16 16:30
 * @author: jy
 */
class FragmentMultiHelper(
    var mFragmentManager: FragmentManager,
    var fragmentId: Int,
    var fragments: Array<Fragment>
) {

    // 当前fragment的位置
    private var currentTabIndex = -1
    private var showingFragment: Fragment? = null
    var positionCallback: ((prePosition: Int, currentPosition: Int) -> Unit)? = null
    var isMFinishing: (() -> Boolean?)? = null

    fun gotoFragment(position: Int) {
        try {
            if (currentTabIndex != position) {
                val trx = mFragmentManager.beginTransaction()
                if (!fragments[position].isAdded &&
                    null == mFragmentManager.findFragmentByTag(position.toString())
                ) {
                    if (showingFragment != null) {
                        trx.hide(fragments[currentTabIndex])
                            .add(fragmentId, fragments[position], position.toString())
                    } else {
                        trx.add(fragmentId, fragments[position])
                    }
                } else {
                    if (showingFragment != null) {
                        trx.hide(fragments[currentTabIndex]).show(fragments[position])
                    } else {
                        trx.show(fragments[position])
                    }
                }
                showingFragment = fragments[position]
                if (isMFinishing?.invoke() != true) {
                    trx.commitAllowingStateLoss()
                    mFragmentManager.executePendingTransactions()
                }
            }
            positionCallback?.invoke(currentTabIndex, position)
            currentTabIndex = position
        } catch (e: Exception) {
            YLogUtils.e(e.message)
        }

    }
}