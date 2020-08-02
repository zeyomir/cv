package io.github.zeyomir.cv.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Image View that clips its image to shape provided in background.
 *
 * Class is needed because of this bug https://issuetracker.google.com/issues/37036728 (it's not fixed since 2015!)- the property 'clipToOutline' cannot be set in xml.
 * */
class ClippingImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    init {
        clipToOutline = true
    }
}
