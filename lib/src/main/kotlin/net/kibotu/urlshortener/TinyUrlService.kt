package net.kibotu.urlshortener

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface TinyUrlService {

    @GET("api-create.php")
    fun shortenUrl(@Query("url") url: String): Observable<ResponseBody>
}