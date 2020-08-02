package io.github.zeyomir.cv.base.view

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.zeyomir.cv.R

class PaddingDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val padding: Int

    init {
        val paddingDP = context.resources.getDimension(R.dimen.paddingInList)
        val metrics = context.resources.displayMetrics

        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingDP, metrics).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == 0) {
            return
        }
        outRect.top = padding
    }
}
