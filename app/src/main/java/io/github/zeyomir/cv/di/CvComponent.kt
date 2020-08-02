package io.github.zeyomir.cv.di

import dagger.Subcomponent
import io.github.zeyomir.cv.base.di.AppComponent
import io.github.zeyomir.cv.base.di.CvScope
import io.github.zeyomir.cv.data.di.CvDataModule
import io.github.zeyomir.cv.domain.di.CvDomainModule

@CvScope
@Subcomponent(modules = [CvDataModule::class, CvDomainModule::class])
interface CvComponent {
    fun getCvActivityComponentBuilder(): CvActivityComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        fun build(): CvComponent
    }

    object Initializer {
        fun init(appComponent: AppComponent) =
            appComponent
                .getCvComponentBuilder()
                .build()
    }
}
