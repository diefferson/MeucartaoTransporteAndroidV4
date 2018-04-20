package br.com.disapps.data.api

import br.com.disapps.data.entity.RetornoCartao
import br.com.disapps.data.entity.RetornoExtrato
import br.com.disapps.data.entity.Veiculo
import com.google.gson.JsonArray
import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @POST("listaVeiculos")
    fun listaVeiculos(@Field("l") l: String): Deferred<List<Veiculo>>

    @FormUrlEncoded
    @POST("cartao")
    fun saldoCartao(@Field("c") c: String, @Field("d") d: String, @Field("t") t: String): Deferred<RetornoCartao>

    @FormUrlEncoded
    @POST("cartao")
    fun extratoCartao(@Field("c") c: String, @Field("d") d: String, @Field("t") t: String): Deferred<RetornoExtrato>
}