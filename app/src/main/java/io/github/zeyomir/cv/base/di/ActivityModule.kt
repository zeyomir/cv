package io.github.zeyomir.cv.base.di

import android.app.Activity
import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.base.AppNavigator
import io.github.zeyomir.cv.base.DateFormatter
import java.util.*

@Module
class ActivityModule {
    @Provides
    @ActivityScope
    fun context(activity: Activity): Context = activity

    @Provides
    @ActivityScope
    fun appNavigator(activity: Activity) = AppNavigator(activity)

    @Provides
    @ActivityScope
    fun locale(context: Context): Locale =
        ConfigurationCompat.getLocales(context.resources.configuration)[0]

    @Provides
    @ActivityScope
    fun dateFormatter(locale: Locale) =
        DateFormatter(locale)
}
