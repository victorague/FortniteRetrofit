package net.victor.fortniteretrofit.api

import net.victor.fortniteretrofit.model.Respuesta

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import rx.Observable

/**
 * Created by Victor on 26/02/2018.
 */
interface FortniteApiService {
    @Headers("TRN-Api-Key:fff2e4fa-086c-479f-bd7e-aed625622a32")
    @GET("v1/profile/{platform}/{userName}")



    fun getData(@Path("platform") plataforma:String, @Path("userName") nombreUsuario:String): Observable<Respuesta>

    companion object {
        fun create(): FortniteApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("https://api.fortnitetracker.com/")
                    .build()

            return retrofit.create(FortniteApiService::class.java)
        }
    }
}