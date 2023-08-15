package com.wenyou.uidemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wenyou.uidemo.R

/**
 * @description 弹性布局，普通流布局
 * @date: 2021/3/19 14:03
 * @author: jy
 */
class NormalFlowLayoutFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): NormalFlowLayoutFragment {
            return NormalFlowLayoutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_normal_flowlayout_demo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}