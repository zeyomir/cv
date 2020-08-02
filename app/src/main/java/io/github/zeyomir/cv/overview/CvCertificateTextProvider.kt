package io.github.zeyomir.cv.overview

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import io.github.zeyomir.cv.R
import io.github.zeyomir.cv.base.extension.setBoldSpan

class CvCertificateTextProvider(private val context: Context) {

    fun getIssuedText(issuer: String, year: Int): Spannable =
        SpannableStringBuilder(context.getString(R.string.issuedText, issuer, year))
            .apply {
                setBoldSpan(issuer)
                setBoldSpan(year.toString())
            }
}
