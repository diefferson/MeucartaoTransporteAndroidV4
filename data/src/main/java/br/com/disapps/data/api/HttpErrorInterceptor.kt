package br.com.disapps.data.api

import br.com.disapps.data.entity.BaseResponse
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import com.google.gson.Gson
import okhttp3.ResponseBody

class HttpErrorInterceptor : Interceptor {

    @Throws(KnownException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        //Build new request
        val builder = request.newBuilder()

        //overwrite old request
        request = builder.build()

        //perform request, here original request will be executed
        val response = chain.proceed(request)

        when(response.code()){
            HttpURLConnection.HTTP_OK ->{
                response.body()?.let {
                    val contentType = it.contentType()
                    val data = it.string()
                    val baseResponse = Gson().fromJson<BaseResponse<*>>(data, BaseResponse::class.java)
                    if(baseResponse.code != "000"){
                        throw errorHandler(baseResponse.code)
                    }else{
                        return response.newBuilder().body(ResponseBody.create(contentType, data)).build()
                    }
                }
            }
            else-> throw KnownException(KnownError.NETWORK_EXCEPTION)
        }

        return response
    }

    private fun errorHandler(code : String) : KnownException{
        return when(code){
            "001" -> KnownException(KnownError.INVALID_ARGUMENT_EXCEPTION)
            "002" -> KnownException(KnownError.INVALID_OPERATION_EXCEPTION)
            "003" -> KnownException(KnownError.INVALID_DOCUMENT_EXCEPTION)
            "004" -> KnownException(KnownError.INVALID_CARD_EXCEPTION)
            "005" -> KnownException(KnownError.LINK_DOCUMENT_CARD_EXCEPTION)
            "006" -> KnownException(KnownError.INACTIVE_CARD_EXCEPTION)
            "007" -> KnownException(KnownError.UNAUTHORIZED_EXCEPTION)
            "008" -> KnownException(KnownError.INVALID_LINE_EXCEPTION)
            "009" -> KnownException(KnownError.EMPTY_EXCEPTION)
            "011" -> KnownException(KnownError.MAINTENANCE_EXCEPTION)
            else -> KnownException(KnownError.NETWORK_EXCEPTION)
        }
    }

}