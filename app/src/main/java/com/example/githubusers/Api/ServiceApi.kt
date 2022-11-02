package com.example.githubusers.Api

import com.example.githubusers.model.UserData
import com.example.githubusers.model.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<UserData>

    @GET("user/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetails(
        @Path("username") username:String
    ):Call<UserDetail>
}