package com.wenyou.baselibrary.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * @description
 * @date: 2021/12/16 11:53
 * @author: jy
 */
class MyFragmentPagerAdapter(
    supportFragmentManager: FragmentManager,
    private var fragments: ArrayList<Fragment>,
    private var titles: Array<String>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}