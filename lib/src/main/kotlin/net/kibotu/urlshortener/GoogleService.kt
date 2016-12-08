package net.kibotu.urlshortener

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface GoogleService {

    @POST("urlshortener/v1/url")
    fun shortenUrl(@Query("key") api: String, @Body model: RequestModel): Observable<ResponseModel>
}