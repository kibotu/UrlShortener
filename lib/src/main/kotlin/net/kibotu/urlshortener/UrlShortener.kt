package net.kibotu.urlshortener

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class UrlShortener {

    companion object {

        @JvmStatic var enableLogging: Boolean = false

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (enableLogging)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    })
                    .build()
        }

        private val tinyUrlService: TinyUrlService by lazy {
            Retrofit.Builder()
                    .baseUrl("http://tinyurl.com/")
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create(TinyUrlService::class.java)
        }

        private val googleService: GoogleService by lazy {
            Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create(GoogleService::class.java)
        }

        @JvmStatic fun shortenUrlByGoogle(context: Context, url: String): Observable<ResponseModel> {
            return googleService.shortenUrl(context.getString(R.string.google_api_key), RequestModel(url))
        }

        @JvmStatic fun shortenUrlByTinyUrl(url: String): Observable<String> {
            return tinyUrlService.shortenUrl(url).flatMap { Observable.just(it.source().readUtf8Line()) }
        }
    }
}