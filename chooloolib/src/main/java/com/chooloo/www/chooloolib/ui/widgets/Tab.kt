package com.chooloo.www.chooloolib.ui.widgets

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.chooloo.www.chooloolib.BaseApp
import com.chooloo.www.chooloolib.R
import com.chooloo.www.chooloolib.util.getAttrColor
import java.util.*

class Tab : AppCompatTextView {
    private val enabledColor by lazy { context.getAttrColor(R.attr.colorOnSurface) }
    private val disabledColor by lazy { context.getAttrColor(R.attr.colorLightBackground) }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("CustomViewStyleable", "Recycle")
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleRes) {
        textSize = 28f
        text = text.toString().replaceFirstChar { it.uppercase(Locale.ROOT) }
        typeface = ResourcesCompat.getFont(context, R.font.google_sans_bold)

        setTextColor(enabledColor)
    }


    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)
        setColor(if (activated) enabledColor else disabledColor)
        if (activated) {
            animateAttention()
        }
    }


    private fun animateAttention() {
        (context.applicationContext as BaseApp).component.animations.show(this, true)
    }

    private fun setColor(@ColorInt color: Int) {
        ValueAnimator.ofObject(ArgbEvaluator(), currentTextColor, color).apply {
            addUpdateListener { setTextColor(it.animatedValue as Int) }
            start()
        }
    }
}