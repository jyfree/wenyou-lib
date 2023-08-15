package com.wenyou.yuilibrary.widget.tab

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.wenyou.yuilibrary.R

/**
 * @description
 * @date: 2021/12/16 13:44
 * @author: jy
 */
class TabImageView : FrameLayout {
    private var mImageView: ImageView? = null
    private var mDescTextView: TextView? = null
    private var mMessageTextView: TextView? = null
    private var mRedPointView: View? = null
    private var mData: TabItem? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.yui_layout_tab_imageview, this)
        mImageView = view.findViewById(R.id.iv_icon)
        mDescTextView = view.findViewById(R.id.tv_desc)
        mMessageTextView = view.findViewById(R.id.tv_message)
        mRedPointView = view.findViewById(R.id.view_red_point)
    }

    @TargetApi(21)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    fun setData(item: TabItem?): TabImageView {
        if (item == null) {
            mImageView!!.setImageDrawable(null)
            mData = null
            return this
        }
        mData = item
        selectView(isSelected)
        return this
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        selectView(selected)
    }

    private fun selectView(selected: Boolean) {
        if (mData == null) {
            return
        }
        if (mData!!.drawableSize != 0) {
            val params =
                mImageView!!.layoutParams as LinearLayout.LayoutParams
            params.width = mData!!.drawableSize
            params.height = mData!!.drawableSize
            mImageView!!.layoutParams = params
        }
        if (selected) {
            mImageView!!.setImageDrawable(mData!!.selectedDrawable)
        } else {
            mImageView!!.setImageDrawable(mData!!.defaultDrawable)
        }
        if (mData?.text.isNullOrEmpty()) {
            mDescTextView!!.visibility = View.GONE
        } else {
            mDescTextView!!.visibility = View.VISIBLE
            mDescTextView!!.text = mData!!.text
            mDescTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, mData!!.textSize.toFloat())
            mDescTextView!!.setTextColor(if (selected) mData!!.selectedTextColor else mData!!.defaultTextColor)
        }
    }

    fun showMessageView() {
        when (mData!!.messageType) {
            TabItem.MessageType.TYPE_NONE -> {
                mMessageTextView!!.visibility = View.GONE
                mRedPointView!!.visibility = View.GONE
            }
            TabItem.MessageType.TYPE_POINT -> {
                mRedPointView!!.visibility = View.VISIBLE
                mMessageTextView!!.visibility = View.GONE
            }
            TabItem.MessageType.TYPE_TEXT -> {
                mRedPointView!!.visibility = View.GONE
                mMessageTextView!!.visibility = View.VISIBLE
                mMessageTextView!!.text = mData!!.msgText
            }
            else -> {
            }
        }
    }
}