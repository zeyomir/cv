package io.github.zeyomir.cv.base.extension

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan

fun SpannableStringBuilder.setBoldSpan(word: String) {
    val indexOfWord = indexOf(word)
    val endIndexOfWord = indexOfWord + word.length
    setSpan(StyleSpan(Typeface.BOLD), indexOfWord, endIndexOfWord, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}
