package com.wenyou.yuilibrary.widget.imageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.wenyou.yuilibrary.R

/**
 * @description 有点击效果的imageView
 * @date: 2021/2/3 16:38
 * @author: jy
 */
class TouchImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var mSelectedMaskColor: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TouchImageView)
        mSelectedMaskColor = typedArray.getColor(
            R.styleable.TouchImageView_tiv_selected_mask_color,
            Color.TRANSPARENT
        )
        typedArray.recycle()
    }


    /**
     * 设置点击遮罩层颜色
     *
     * @param selectedMaskColor
     */
    fun setSelectedMaskColor(selectedMaskColor: Int) {
        mSelectedMaskColor = selectedMaskColor
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.setColorFilter(mSelectedMaskColor)
                return true
            }
            MotionEvent.ACTION_UP -> {
                this.colorFilter = null
                performClick()
            }
            MotionEvent.ACTION_CANCEL -> {
                this.colorFilter = null
            }
        }
        return super.onTouchEvent(event)
    }
}