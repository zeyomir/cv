package io.github.zeyomir.cv.base.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.github.zeyomir.cv.di.CvComponent

@AppScope
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun getCvComponentBuilder(): CvComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder
        fun build(): AppComponent
    }

    object Initializer {
        fun init(app: Application) =
            DaggerAppComponent.builder()
                .app(app)
                .build()
    }
}

