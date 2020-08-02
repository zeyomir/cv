package io.github.zeyomir.cv.data.network

import io.github.zeyomir.cv.data.network.model.ApiCvModel
import io.reactivex.Single
import retrofit2.http.GET

interface CvService {
    @GET("/zeyomir/b987f0b48ddf01404a46312cb611852e/raw/cv.json")
    fun cv(): Single<ApiCvModel>
}
