package com.wenyou.yuilibrary.widget.tab

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.wenyou.yuilibrary.R
import com.wenyou.yuilibrary.YUIHelper

/**
 * @description
 * @date: 2021/12/16 13:44
 * @author: jy
 */
@SuppressLint("AppCompatCustomView")
class TabTextView : TextView {
    private var mSelectedDrawable: Drawable? = null
    private var notify = false
    private var pointPaint: Paint? = null
    private var pointSize = 0
    private var mData: TabItem? = null

    @JvmOverloads
    constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        initView()
    }

    @TargetApi(21)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        pointPaint = Paint()
        pointPaint!!.style = Paint.Style.FILL
        pointPaint!!.color = Color.parseColor("#ff7333")
        setTextSize(TypedValue.COMPLEX_UNIT_PX, YUIHelper.dip2px(14f).toFloat())
        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        gravity = Gravity.CENTER
        mSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.yui_shape_tab_indicator)
        mSelectedDrawable?.setBounds(
            0,
            0,
            YUIHelper.dip2px(18f),
            YUIHelper.dip2px(4f)
        )
        setTextColor(Color.parseColor("#999999"))
        pointSize = YUIHelper.dip2px(7f)
        setPaddingRelative(0, 0, pointSize, 0)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        selectView(selected)
    }

    fun setData(tabItem: TabItem?): TabTextView {
        mData = tabItem
        selectView(isSelected)
        return this
    }

    private fun selectView(selected: Boolean) {
        if (mData == null) {
            return
        }
        if (selected) {
            setTextColor(mData!!.selectedTextColor)
            if (mData!!.showIndicator) {
                setCompoundDrawables()
                setCompoundDrawablesRelative(null, null, null, mSelectedDrawable)
            }
            setTextSize(TypedValue.COMPLEX_UNIT_SP, mData!!.selectedTextSize.toFloat())
            typeface = mData!!.selectTextStyle
            paint.isFakeBoldText = true
        } else {
            setTextColor(mData!!.defaultTextColor)
            setCompoundDrawablesRelative(null, null, null, null)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, mData!!.textSize.toFloat())
            paint.isFakeBoldText = false
            typeface = mData!!.defaultTextStyle
        }
        this.text = mData!!.text
    }

    private fun setCompoundDrawables() {
        if (mData == null) {
            return
        }
        if (mData?.indicatorDrawable == null) {
            return
        }
        mSelectedDrawable = mData!!.indicatorDrawable
        val right =
            if (mData!!.indicatorWidth > 0) mData!!.indicatorWidth else YUIHelper.dip2px(18f)
        val bottom =
            if (mData!!.indicatorHeight > 0) mData!!.indicatorHeight else YUIHelper.dip2px(4f)
        mSelectedDrawable?.setBounds(
            0,
            0,
            right, bottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (notify) {
            val cx: Float = pointSize / 2.toFloat()
            val cy: Float = measuredHeight / 2 - pointSize.toFloat()
            canvas.drawCircle(cx, cy, pointSize / 2.toFloat(), pointPaint!!)
        }
    }

    fun setNotify(notify: Boolean) {
        this.notify = notify
        invalidate()
    }

    fun isNotify(): Boolean {
        return notify
    }
}