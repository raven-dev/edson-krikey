package com.ravn.edsonkrikey.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ravn.edsonkrikey.R
import kotlinx.android.synthetic.main.view_information_input.view.*

/**
 * Created by Edson Arratea Ope on 5/7/19.
 * Ravn Development
 **/

class CustomInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): LinearLayout(context, attrs, defStyleAttr) {

    private var attrTitle: String? = String()
    private var attrBody: String? = String()

    init {
        LinearLayout.inflate(context, R.layout.view_information_input, this)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomInput, 0, 0).apply {
            try {
                attrTitle = getString(R.styleable.CustomInput_inputTitle)
                attrBody = getString(R.styleable.CustomInput_inputBody)
            } finally {
                setAttr(attrTitle, attrBody)
                recycle()
            }
        }
    }

    private fun setAttr(attrTitle: String?, attrBody: String?) {
        inputTitle.text = attrTitle
        inputBody.text = attrBody
    }
}