package br.com.disapps.data.api

import br.com.disapps.data.BuildConfig
import br.com.disapps.domain.listeners.DownloadProgressListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

object DownloadClient{

    fun getRetrofitDownloadClient(progressListener: DownloadProgressListener): RestApi {

        val okHttp = OkHttpClient.Builder()
                 .hostnameVerifier(getHostnameVerifier())
                 .addInterceptor(DownloadProgressInterceptor(progressListener))
                 .retryOnConnectionFailure(true)

        return  Retrofit.Builder()
                .baseUrl(BuildConfig.HOST + "/")
                .client(okHttp.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RestApi::class.java)
    }

    private class DownloadProgressInterceptor(private val listener: DownloadProgressListener) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse = chain.proceed(chain.request())

            return originalResponse.newBuilder()
                    .body(DownloadProgressResponseBody(originalResponse.body()!!, listener))
                    .build()
        }
    }

    private fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ ->
            true
        }
    }
}
