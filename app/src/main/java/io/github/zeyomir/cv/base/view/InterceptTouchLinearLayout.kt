package io.github.zeyomir.cv.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * LinearLayout that intercepts all touch events, not allowing them to reach children.
 *
 * Class is needed to set onClick when one of the child views is RecyclerView which otherwise steals clicks.
 */
class InterceptTouchLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}
