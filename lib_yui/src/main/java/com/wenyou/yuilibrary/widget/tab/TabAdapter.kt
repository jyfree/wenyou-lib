package com.wenyou.yuilibrary.widget.tab

import android.util.SparseArray
import android.view.View

/**
 * @description
 * @date: 2021/12/16 13:44
 * @author: jy
 */
class TabAdapter {
    private var mDatas: ArrayList<TabItem>? =
        ArrayList()
    private val mTypeCounts = SparseArray<Int>()
    private val mViews: MutableMap<Int, View?> =
        HashMap()
    private val mNotifyPos: MutableList<Int> = ArrayList()
    val count: Int
        get() = mDatas!!.size

    fun getItemViewTypeCount(type: Int): Int {
        val types = mTypeCounts[type]
        return types ?: 0
    }

    fun onCreateView(
        container: TabContainer,
        pos: Int
    ): View {
        val data = getItem(pos)
        var view: View? = null
        view = when (data!!.type) {
            TabItem.Type.TYPE_TAB_IMAGE -> TabImageView(container.context)
            TabItem.Type.TYPE_TAB_TEXT -> TabTextView(container.context)
            else -> View(container.context)
        }
        if (!mViews.containsKey(pos)) {
            mViews[pos] = view
        }
        if (data.params != null) {
            view.layoutParams = data.params
        }
        return view
    }

    fun setData(datas: ArrayList<TabItem>?) {
        mDatas = datas
        mTypeCounts.clear()
        if (datas != null) {
            var i = 0
            val len = datas.size
            while (i < len) {
                val type = mDatas!![i].type
                val count = mTypeCounts[type]
                if (count == null) {
                    mTypeCounts.put(type, 1)
                } else {
                    mTypeCounts.put(type, count + 1)
                }
                i++
            }
        }
    }

    fun addData(item: TabItem?) {
        if (item == null) {
            return
        }
        mDatas!!.add(item)
        val count = mTypeCounts[item.type]
        if (count == null) {
            mTypeCounts.put(item.type, 1)
        } else {
            mTypeCounts.put(item.type, count + 1)
        }
    }

    fun getItem(pos: Int): TabItem? {
        return if (pos < 0 || pos >= mDatas!!.size) {
            null
        } else {
            mDatas!![pos]
        }
    }

    fun onDestoryView(
        container: TabContainer?,
        item: View?,
        pos: Int
    ) {
    }

    fun bindData(view: View, pos: Int) {
        val data = getItem(pos)
        view.background = data!!.bgDrawable
        when (data.type) {
            TabItem.Type.TYPE_TAB_IMAGE -> if (view is TabImageView) {
                view.setData(data)
            }
            TabItem.Type.TYPE_TAB_TEXT -> if (view is TabTextView) {
                view.setData(data)
            }
            else -> {
            }
        }
        if (mNotifyPos.contains(pos)) {
            notifyTabViewDirectly(pos)
        }
    }

    fun notifyTabView(index: Int) {
        if (mViews.isEmpty()) { //说明还没初始化完
            if (!mNotifyPos.contains(index)) {
                mNotifyPos.add(index)
            }
        } else { //已经初始化完
            notifyTabViewDirectly(index)
        }
    }

    private fun notifyTabViewDirectly(index: Int) {
        if (mViews.containsKey(index)) {
            val view = mViews[index]
            if (view is TabImageView) {
                view.showMessageView()
            }
        }
    }

    fun clearData() {
        mViews.clear()
        mDatas!!.clear()
        mTypeCounts.clear()
        mNotifyPos.clear()
    }
}