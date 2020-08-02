package io.github.zeyomir.cv.base.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.zeyomir.cv.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

@Module
open class NetworkModuleBase {

    @Provides
    @AppScope
    fun moshi(): Moshi = Moshi.Builder().build()

    @Provides
    @AppScope
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("RetrofitLog").d(message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.valueOf(BuildConfig.RETROFIT_LOG_LEVEL)
        return loggingInterceptor
    }

    @Provides
    @AppScope
    fun certificatePinner() = createCertificatePinner()

    protected open fun createCertificatePinner(): CertificatePinner = CertificatePinner.DEFAULT

    @Provides
    @AppScope
    fun okHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .certificatePinner(certificatePinner)
            .build()

    @Provides
    @AppScope
    fun retrofit(moshi: Moshi, okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
}
