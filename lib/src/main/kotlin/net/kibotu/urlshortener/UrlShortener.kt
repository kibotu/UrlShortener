package net.kibotu.urlshortener

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.util.concurrent.TimeUnit

object UrlShortener {

    fun shortenUrl(context: Context, url: String): Observable<ResponseModel> {
        return createNetworkServiceVideo(context).shortenUrl(context.getString(R.string.google_api_key), RequestModel(url))
    }

    private fun createNetworkServiceVideo(context: Context): GoogleService {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(GoogleService::class.java)
    }
}