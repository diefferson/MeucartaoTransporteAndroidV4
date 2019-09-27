package br.com.disapps.data.api

import br.com.disapps.data.BuildConfig
import br.com.disapps.data.entity.RetornoCartao
import br.com.disapps.data.entity.RetornoExtrato
import br.com.disapps.data.entity.Veiculo
import com.google.gson.JsonArray
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by dnso on 12/03/2018.
 */
interface RestApi {
    @POST("listaLinhas")
    fun listaLinhas(): Deferred<JsonArray>

    @POST("listaHorarios")
    fun listaHorarios(): Deferred<JsonArray>

    @FormUrlEncoded
    @POST("listaPontos")
    fun listaPontos(@Field("met") m: String): Deferred<JsonArray>

    @FormUrlEncoded
    @POST("listaShapes")
    fun listaShapes(@Field("met") m: String): Deferred<JsonArray>

    @FormUrlEncoded
    @POST("v2/listaVeiculos")
    fun listaVeiculos(@Header("Authorization") auth:String = BuildConfig.REQUEST_VEHICLES_KEY,
                      @Field("l") l: String): Deferred<List<Veiculo>>

    @FormUrlEncoded
    @POST("v2/cartao")
    fun saldoCartao(@Header("Authorization") auth:String = BuildConfig.REQUEST_CARDS_KEY,
                    @Field("c") c: String,
                    @Field("d") d: String,
                    @Field("t") t: String): Deferred<RetornoCartao>

    @FormUrlEncoded
    @POST("v2/cartao")
    fun extratoCartao(@Header("Authorization") auth:String = BuildConfig.REQUEST_CARDS_KEY,
                      @Field("c") c: String,
                      @Field("d") d: String,
                      @Field("t") t: String): Deferred<RetornoExtrato>
}