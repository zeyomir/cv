package io.github.zeyomir.cv.base.di

import dagger.Module
import okhttp3.CertificatePinner

@Module
class NetworkModule : NetworkModuleBase() {
    override fun createCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add("gist.githubusercontent.com", "sha256/xlDAST56PmiT3SR0WdFOR3dghwJrQ8yXx6JLSqTIRpk=")
            .build()
}
