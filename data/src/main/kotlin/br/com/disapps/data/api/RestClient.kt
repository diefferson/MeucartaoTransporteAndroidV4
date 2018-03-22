package br.com.disapps.data.api

import br.com.disapps.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by diefferson.santos on 23/08/17.
 */
class RestClient {

    val api: RestApi

    private var httpClient: OkHttpClient? = null
    private val retrofitClient: Retrofit

    init {

        if (BuildConfig.HTTP_LOG_ENABLED) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
        } else {
            httpClient = OkHttpClient.Builder()
                    .build()
        }


        retrofitClient = Retrofit.Builder()
                .baseUrl(BuildConfig.HOST + "/")
                .client(httpClient!!)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retrofitClient.create(RestApi::class.java)
    }
}
