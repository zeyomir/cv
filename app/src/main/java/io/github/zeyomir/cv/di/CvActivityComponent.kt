package io.github.zeyomir.cv.di

import android.app.Activity
import androidx.navigation.NavController
import dagger.BindsInstance
import dagger.Subcomponent
import io.github.zeyomir.cv.base.di.ActivityModule
import io.github.zeyomir.cv.base.di.ActivityScope
import io.github.zeyomir.cv.overview.CvOverviewViewModel
import io.github.zeyomir.cv.overview.certificates.CvCertificatesViewModel
import io.github.zeyomir.cv.overview.education.CvEducationViewModel
import io.github.zeyomir.cv.overview.experience.CvExperienceViewModel

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, CvActivityModule::class])
interface CvActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun navController(navController: NavController): Builder

        fun build(): CvActivityComponent
    }

    object Initializer {
        fun init(
            component: CvComponent,
            activity: Activity,
            navController: NavController
        ) =
            component
                .getCvActivityComponentBuilder()
                .activity(activity)
                .navController(navController)
                .build()
    }

    fun inject(viewModel: CvOverviewViewModel)
    fun inject(viewModel: CvExperienceViewModel)
    fun inject(viewModel: CvEducationViewModel)
    fun inject(viewModel: CvCertificatesViewModel)
}
