package com.ravn.edsonkrikey.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.ravn.edsonkrikey.R
import kotlinx.android.synthetic.main.view_empty_screen.view.*

/**
 * Created by Edson Arratea Ope on 5/8/19.
 * Ravn Development
 **/

class EmptyScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var attrEmptyScreenTitle: String? = String()
    private var attrEmptyScreenBody: String? = String()
    init {
        ConstraintLayout.inflate(context, R.layout.view_empty_screen, this)
        context.theme.obtainStyledAttributes(attrs, R.styleable.EmptyScreen, 0, 0).apply {
            try {
                attrEmptyScreenTitle = getString(R.styleable.EmptyScreen_emptyScreenTitle)
                attrEmptyScreenBody = getString(R.styleable.EmptyScreen_emptyScreenBody)
            } finally {
                setAttr(attrEmptyScreenTitle, attrEmptyScreenBody)
                recycle()
            }
        }
    }
    private fun setAttr(attrEmptyScreenTitle: String?, attrEmptyScreenBody: String?) {
        titleEmptyScreen.text = attrEmptyScreenTitle
        bodyEmptyScreen.text = attrEmptyScreenBody
    }
}