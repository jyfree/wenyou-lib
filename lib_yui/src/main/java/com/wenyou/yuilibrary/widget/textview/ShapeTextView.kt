package com.wenyou.yuilibrary.widget.textview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.wenyou.yuilibrary.R
import com.wenyou.yuilibrary.YUIHelper.dip2px

/**
 * @description 用于需要圆角矩形框背景的TextView的情况, 减少直接使用TextView时引入的shape资源文件
 * @date: 2021/2/3 17:15
 * @author: jy
 */
open class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val gd_background = GradientDrawable()
    private var backgroundColor = 0
    private var cornerRadius = 0
    private var strokeWidth = 0
    private var strokeColor = 0
    private var isRadiusHalfHeight = false
    private var isWidthHeightEqual = false

    init {
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView)
        backgroundColor = typedArray.getColor(
            R.styleable.ShapeTextView_stv_backgroundColor,
            Color.TRANSPARENT
        )
        cornerRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeTextView_stv_cornerRadius, 0
        )
        strokeWidth = typedArray.getDimensionPixelSize(
            R.styleable.ShapeTextView_stv_strokeWidth, 0
        )
        strokeColor = typedArray.getColor(
            R.styleable.ShapeTextView_stv_strokeColor,
            Color.TRANSPARENT
        )
        isRadiusHalfHeight = typedArray.getBoolean(
            R.styleable.ShapeTextView_stv_isRadiusHalfHeight, false
        )
        isWidthHeightEqual = typedArray.getBoolean(
            R.styleable.ShapeTextView_stv_isWidthHeightEqual,
            false
        )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isWidthHeightEqual() && width > 0 && height > 0) {
            val max = width.coerceAtLeast(height)//Math.max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(
                max,
                MeasureSpec.EXACTLY
            )
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRadiusHalfHeight()) {
            setCornerRadius(height / 2)
        } else {
            setBgSelector()
        }
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        setBgSelector()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = dip2px(cornerRadius.toFloat())
        setBgSelector()
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = dip2px(strokeWidth.toFloat())
        setBgSelector()
    }

    fun setStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        setBgSelector()
    }

    fun setIsRadiusHalfHeight(isRadiusHalfHeight: Boolean) {
        this.isRadiusHalfHeight = isRadiusHalfHeight
        setBgSelector()
    }

    fun setIsWidthHeightEqual(isWidthHeightEqual: Boolean) {
        this.isWidthHeightEqual = isWidthHeightEqual
        setBgSelector()
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    fun getCornerRadius(): Int {
        return cornerRadius
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }

    fun isRadiusHalfHeight(): Boolean {
        return isRadiusHalfHeight
    }

    fun isWidthHeightEqual(): Boolean {
        return isWidthHeightEqual
    }

    private fun setDrawable(gd: GradientDrawable, color: Int, strokeColor: Int) {
        gd.setColor(color)
        gd.cornerRadius = cornerRadius.toFloat()
        gd.setStroke(strokeWidth, strokeColor)
    }

    fun setBgSelector() {
        val bg = StateListDrawable()
        setDrawable(gd_background, backgroundColor, strokeColor)
        bg.addState(intArrayOf(-android.R.attr.state_pressed), gd_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //16
            background = bg
        } else {
            setBackgroundDrawable(bg)
        }
    }
}