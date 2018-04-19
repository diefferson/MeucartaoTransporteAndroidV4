package br.com.disapps.data.api

import br.com.disapps.data.entity.RetornoCartao
import br.com.disapps.data.entity.RetornoExtrato
import br.com.disapps.data.entity.Veiculo
import com.google.gson.JsonArray
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by dnso on 12/03/2018.
 */
interface RestApi {
    @POST("listaLinhas")
    fun listaLinhas(): Single<JsonArray>

    @POST("listaHorarios")
    fun listaHorarios(): Single<JsonArray>

    @FormUrlEncoded
    @POST("listaPontos")
    fun listaPontos(@Field("met") m: String): Single<JsonArray>

    @FormUrlEncoded
    @POST("listaShapes")
    fun listaShapes(@Field("met") m: String): Single<JsonArray>

    @FormUrlEncoded
    @POST("listaVeiculos")
    fun listaVeiculos(@Field("l") l: String): Observable<List<Veiculo>>

    @FormUrlEncoded
    @POST("cartao")
    fun saldoCartao(@Field("c") c: String, @Field("d") d: String, @Field("t") t: String): Single<RetornoCartao>

    @FormUrlEncoded
    @POST("cartao")
    fun extratoCartao(@Field("c") c: String, @Field("d") d: String, @Field("t") t: String): Single<RetornoExtrato>
}