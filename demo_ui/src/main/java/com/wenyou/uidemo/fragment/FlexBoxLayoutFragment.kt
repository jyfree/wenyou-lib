package com.wenyou.uidemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.wenyou.uidemo.R
import com.wenyou.uidemo.adapter.FlexBoxLayoutAdapter
import kotlinx.android.synthetic.main.fragment_flexbox_layout_demo.*

/**
 * @description google弹性布局
 * @date: 2021/3/19 14:29
 * @author: jy
 */
class FlexBoxLayoutFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): FlexBoxLayoutFragment {
            return FlexBoxLayoutFragment()
        }
    }

    private var mAdapter1: FlexBoxLayoutAdapter? = null
    private var mAdapter2: FlexBoxLayoutAdapter? = null
    private var mAdapter3: FlexBoxLayoutAdapter? = null
    private var mAdapter4: FlexBoxLayoutAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flexbox_layout_demo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
    }

    private fun initView() {
        val array: Array<String> = resources.getStringArray(R.array.tags_values)
        recycler_view_1.layoutManager = getFlexBoxLayoutManager()
        recycler_view_1.adapter = FlexBoxLayoutAdapter(array).also { mAdapter1 = it }

        recycler_view_2.layoutManager = getFlexBoxLayoutManager()
        recycler_view_2.adapter = FlexBoxLayoutAdapter(array).also { mAdapter2 = it }

        recycler_view_3.layoutManager = getFlexBoxLayoutManager()
        recycler_view_3.adapter = FlexBoxLayoutAdapter(array).setCancelable(true).also {
            mAdapter3 = it
        }

        recycler_view_4.layoutManager = getFlexBoxLayoutManager()
        recycler_view_4.itemAnimator = null
        recycler_view_4.adapter = FlexBoxLayoutAdapter(array).setIsMultiSelectMode(true).also {
            mAdapter4 = it
        }

        mAdapter2?.select(2)
        mAdapter3?.select(3)
        mAdapter4?.multiSelect(1, 2, 3)
    }

    private fun initListeners() {
        mAdapter1?.setOnItemClickListener { _, item, _ -> show("点击了：$item") }
        mAdapter2?.setOnItemClickListener { _, _, position ->
            if (mAdapter2?.select(position) == true) {
                show("选中的内容：" + mAdapter2?.selectContent)
            }
        }
        mAdapter3?.setOnItemClickListener { _, _, position ->
            if (mAdapter3?.select(position) == true) {
                show("选中的内容：" + mAdapter3?.selectContent)
            }
        }
        mAdapter4?.setOnItemClickListener { _, _, position ->
            mAdapter4?.select(position)
            show("选中的内容：" + mAdapter4?.multiContent)
        }
    }

    private fun show(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun getFlexBoxLayoutManager(): FlexboxLayoutManager? { //设置布局管理器
        val flexBoxLayoutManager = FlexboxLayoutManager(context)
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 按正常方向换行
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START
        return flexBoxLayoutManager
    }
}