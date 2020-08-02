package io.github.zeyomir.cv.base.di

import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.base.SchedulersProvider
import io.github.zeyomir.cv.base.SchedulersProviderImpl

@Module
class AppModule {

    @Provides
    @AppScope
    fun schedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}
