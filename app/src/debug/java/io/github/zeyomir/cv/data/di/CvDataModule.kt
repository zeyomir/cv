package io.github.zeyomir.cv.data.di

import dagger.Module
import io.github.zeyomir.cv.data.network.CvService
import io.github.zeyomir.cv.data.network.MockCvService
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

@Module
class CvDataModule : CvDataModuleBase() {
    override fun createCvService(retrofit: Retrofit): CvService = MockCvService(createMockRetrofit(retrofit).create(CvService::class.java))

    private fun createMockRetrofit(retrofit: Retrofit): MockRetrofit {
        val networkBehavior = NetworkBehavior.create()
        networkBehavior.setErrorPercent(10)
        networkBehavior.setFailurePercent(10)
        networkBehavior.setVariancePercent(70)
        networkBehavior.setDelay(500, TimeUnit.MILLISECONDS)
        return MockRetrofit.Builder(retrofit)
            .networkBehavior(networkBehavior)
            .build()
    }
}
