package io.github.zeyomir.cv.base

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import de.cketti.mailto.EmailIntentBuilder
import java.lang.ref.WeakReference

class AppNavigator(activity: Activity) {
    private val weakActivity = WeakReference(activity)

    fun openBrowserWithUrl(url: String) {
        if (url.isBlank()) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun startActivity(
        intent: Intent,
        bundle: Bundle = ActivityOptions.makeSceneTransitionAnimation(weakActivity.get()).toBundle()
    ) {
        weakActivity.get()!!.startActivity(intent, bundle)
    }

    fun openEmailApp(address: String) {
        weakActivity.get()?.let {
            EmailIntentBuilder.from(it)
                .to(address)
                .start()
        }
    }
}

