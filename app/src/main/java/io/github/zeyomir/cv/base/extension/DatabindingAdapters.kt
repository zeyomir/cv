package io.github.zeyomir.cv.base.extension

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import io.github.zeyomir.cv.R

@BindingConversion
fun convertBooleanToVisibility(b: Boolean) = if (b) View.VISIBLE else View.GONE

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_refresh)
        .fallback(R.drawable.ic_placeholder_image)
        .error(R.drawable.ic_broken_image)
        .into(view)
}

@BindingAdapter("refreshing")
fun setRefreshing(view: SwipeRefreshLayout, state: Boolean) {
    view.isRefreshing = state
}

@BindingAdapter("android:src")
fun setImageDrawableFromResource(view: ImageView, @DrawableRes drawableId: Int?) =
    drawableId?.let {
        view.setImageDrawable(view.resources.getDrawable(it, view.context.theme))
    }
