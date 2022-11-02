package com.example.githubusers.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class UserData(
    @SerializedName("items")
    val items :ArrayList<User>
)

data class User(
    val login: String,
    val id:Int,
    val avatar_url:String
)

data class UserDetail(
    val login : String,
    val id :Int,
    val avatar_url:String,
    val repos_url: String
)
