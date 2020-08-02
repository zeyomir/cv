package io.github.zeyomir.cv.di

import android.content.Context
import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.CvNavigator
import io.github.zeyomir.cv.base.AppNavigator
import io.github.zeyomir.cv.base.DateFormatter
import io.github.zeyomir.cv.base.di.ActivityScope
import io.github.zeyomir.cv.overview.*

@Module
class CvActivityModule {
    @Provides
    @ActivityScope
    fun cvNavigator(
        navController: NavController,
        appNavigator: AppNavigator
    ) = CvNavigator(navController, appNavigator)

    @Provides
    @ActivityScope
    fun cvCertificateTextProvider(
        context: Context
    ) = CvCertificateTextProvider(context)

    @Provides
    @ActivityScope
    fun cvEducationTextProvider(
        context: Context
    ) = CvEducationTextProvider(context)

    @Provides
    @ActivityScope
    fun cvExperienceTextProvider(
        context: Context,
        dateFormatter: DateFormatter
    ) = CvExperienceTextProvider(context, dateFormatter)

    @Provides
    @ActivityScope
    fun cvLinkIconProvider() = CvLinkIconProvider()

    @Provides
    @ActivityScope
    fun cvOverviewResourcesProvider(
        educationTextProvider: CvEducationTextProvider,
        experienceTextProvider: CvExperienceTextProvider,
        linkIconProvider: CvLinkIconProvider
    ) = CvOverviewResourcesProvider(
        educationTextProvider,
        experienceTextProvider,
        linkIconProvider
    )
}
