package com.wenyou.baselibrary.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @description 将fragment存储到saveInstance中，防止app崩溃/修改字体导致的fragment重叠的问题
 * @date: 2021/12/16 11:57
 * @author: jy
 */
object FragmentSaveInstanceHelp {
    fun saveFragments(
        supportFragmentManager: FragmentManager,
        fragments: Array<Fragment>?,
        savedInstanceState: Bundle?,
        tag: String
    ) {
        fragments?.forEachIndexed { index, fragment ->
            if (fragment.isAdded && savedInstanceState != null)
                supportFragmentManager.putFragment(savedInstanceState, "$tag$index", fragment)
        }
    }

    fun getFragments(
        supportFragmentManager: FragmentManager,
        savedInstanceState: Bundle?,
        tag: String,
        sourceFragments: Array<Fragment>
    ): Array<Fragment> {
        val fragments = mutableListOf<Fragment>()
        if (savedInstanceState != null) {
            sourceFragments.forEachIndexed { index, _ ->
                val fragment = supportFragmentManager.getFragment(savedInstanceState, "$tag$index")
                if (fragment != null)
                    fragments.add(fragment)
                else
                    fragments.add(sourceFragments[index])
            }
        } else {
            fragments.addAll(sourceFragments)
        }
        return fragments.toTypedArray()
    }

    fun getFragmentByIndex(
        supportFragmentManager: FragmentManager,
        savedInstanceState: Bundle?,
        tag: String,
        index: Int
    ): Fragment? {
        return if (savedInstanceState == null) null else supportFragmentManager.getFragment(
            savedInstanceState,
            "$tag$index"
        )
    }

    fun <T> assembleFragment(fragments: Array<Fragment>?, index: Int, clazz: Class<T>): T? {
        if (fragments == null) {
            return null
        }
        return if (fragments.size > index && fragments[index].javaClass == clazz) fragments[index] as T else null
    }

    fun saveCurrentIndex(currentIndex: Int, savedInstanceState: Bundle?, tag: String) {
        savedInstanceState?.putInt("${tag}_index", currentIndex)
    }

    fun getCurrentIndex(savedInstanceState: Bundle?, tag: String): Int {
        return savedInstanceState?.getInt("${tag}_index", 0) ?: 0
    }

    fun getCurrentIndex(savedInstanceState: Bundle?, tag: String, defaultValue: Int): Int {
        return savedInstanceState?.getInt("${tag}_index", defaultValue) ?: defaultValue
    }
}