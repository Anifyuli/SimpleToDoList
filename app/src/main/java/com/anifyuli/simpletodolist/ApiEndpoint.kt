package com.anifyuli.simpletodolist

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {
    @GET("data.php")
    fun data(): Call<ToDoModels>

    @FormUrlEncoded
    @POST("create.php")
    fun create(
        @Field("did") did: String
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("update.php")
    fun update(
        @Field("id") id: String,
        @Field("did") did: String
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun delete(
        @Field("id") id: String
    ): Call<SubmitModel>
}