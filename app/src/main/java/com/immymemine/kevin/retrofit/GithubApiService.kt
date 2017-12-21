package com.immymemine.kevin.retrofit

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by quf93 on 2017-12-19.
 */
interface GithubApiService {
    @GET("service/users")
    fun search(@Query("q") query : String,
               @Query("page") page : Int,
               @Query("per_page") perPage : Int) : Observable<Result>

    @POST("service/users")
    fun postUsage(@Body postBody : String) : Call<String>

    companion object Factory {
        fun create() : GithubApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.github.com/")
                    .build()

            return retrofit.create(GithubApiService::class.java)
        }
    }
}