package com.wenyou.baselibrary.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @description 多fragment选择器
 * @date: 2021/11/18 13:46
 * @author: jy
 */
class FragmentMultiHelper2(
    var fragmentManager: FragmentManager,
    var fragmentId: Int,
    var fragments: MutableList<Fragment>
) {

    private var mCurrentPos: Int = -1

    fun fragmentSelect(pos: Int) {
        if (mCurrentPos >= 0) {
            hideFragment(mCurrentPos)
        }
        showFragment(pos)
        mCurrentPos = pos
    }


    private fun showFragment(pos: Int) {
        if (pos < 0 || pos >= fragments.size) {
            return
        }

        fragments[pos].let {
            if (it.isAdded) {
                fragmentManager
                    .beginTransaction()
                    .show(it)
                    .commitAllowingStateLoss()
            } else {
                fragmentManager
                    .beginTransaction()
                    .add(fragmentId, it)
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun hideFragment(pos: Int) {
        if (pos < 0 || pos >= fragments.size) {
            return
        }
        fragments[pos].let {
            if (it.isAdded) {
                fragmentManager.beginTransaction().hide(it).commitAllowingStateLoss()
            }
        }
    }
}