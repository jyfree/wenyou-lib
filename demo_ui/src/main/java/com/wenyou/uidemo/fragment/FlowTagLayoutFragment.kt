package com.wenyou.uidemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.widget.flowlayout.DefaultFlowTagAdapter
import com.wenyou.yuilibrary.widget.flowlayout.FlowTagLayout
import kotlinx.android.synthetic.main.fragment_flow_tag_layout_demo.*

/**
 * @description 弹性布局，流标签
 * @date: 2021/3/19 14:03
 * @author: jy
 */
class FlowTagLayoutFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): FlowTagLayoutFragment {
            return FlowTagLayoutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow_tag_layout_demo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initNormalFlowTagLayout()
        initSingleFlowTagLayout()
        initSingleCancelableFlowTagLayout()
        initMultiFlowTagLayout()

        btn_add_tag.setOnClickListener { flowLayout_display.addTag("标签" + (Math.random() * 100).toInt()) }
        btn_clear_tag.setOnClickListener { flowLayout_display.clearTags<String>() }
    }

    /**
     * 默认点击
     */
    private fun initNormalFlowTagLayout() {
        //可自定义，继承BaseTagAdapter
        val tagAdapter = DefaultFlowTagAdapter(context)
        flowLayout_normal_select.adapter = tagAdapter
        flowLayout_normal_select.setOnTagClickListener { parent, view, position ->
            Toast.makeText(context, "点击了：" + parent.adapter.getItem(position), Toast.LENGTH_SHORT)
                .show()
        }
        tagAdapter.addTags(resources.getStringArray(R.array.tags_values))
    }

    /**
     * 单选(不可取消)
     */
    private fun initSingleFlowTagLayout() {
        //可自定义，继承BaseTagAdapter
        val tagAdapter = DefaultFlowTagAdapter(context)

        flowLayout_single_select.adapter = tagAdapter
        flowLayout_single_select.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE)
        flowLayout_single_select.setOnTagSelectListener { parent, position, selectedList ->
            Toast.makeText(context, getSelectedText(parent, selectedList), Toast.LENGTH_SHORT)
                .show()
        }

        tagAdapter.addTags(resources.getStringArray(R.array.tags_values))
        tagAdapter.setSelectedPositions(2)
    }

    /**
     * 单选(可点击取消)
     */
    private fun initSingleCancelableFlowTagLayout() {
        //可自定义，继承BaseTagAdapter
        val tagAdapter = DefaultFlowTagAdapter(context)

        flowLayout_single_select_cancelable.adapter = tagAdapter
        flowLayout_single_select_cancelable.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE)
        flowLayout_single_select_cancelable.setOnTagSelectListener { parent, position, selectedList ->
            Toast.makeText(context, getSelectedText(parent, selectedList), Toast.LENGTH_SHORT)
                .show()
        }
        tagAdapter.addTags(resources.getStringArray(R.array.tags_values))
        tagAdapter.setSelectedPositions(2)
    }

    /**
     * 多选
     */
    private fun initMultiFlowTagLayout() {
        val tagAdapter = DefaultFlowTagAdapter(context)

        flowLayout_multi_select.adapter = tagAdapter
        flowLayout_multi_select.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI)
        flowLayout_multi_select.setOnTagSelectListener { parent, position, selectedList ->
            Toast.makeText(context, getSelectedText(parent, selectedList), Toast.LENGTH_SHORT)
                .show()
        }

        tagAdapter.addTags(resources.getStringArray(R.array.tags_values))
        tagAdapter.setSelectedPositions(2, 3, 4)
    }

    private fun getSelectedText(
        parent: FlowTagLayout,
        selectedList: List<Int>
    ): String? {
        val sb = StringBuilder("选中的内容：\n")
        for (index in selectedList) {
            sb.append(parent.adapter.getItem(index))
            sb.append(";")
        }
        return sb.toString()
    }
}