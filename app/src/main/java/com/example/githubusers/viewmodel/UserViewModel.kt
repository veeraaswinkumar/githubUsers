package com.example.githubusers.viewmodel

import android.app.Application
import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.Api.RetrofitClient
import com.example.githubusers.model.User
import com.example.githubusers.model.UserData
import com.example.githubusers.room.FavoriteUser
import com.example.githubusers.room.FavouriteUserDao
import com.example.githubusers.room.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val listUsers = MutableLiveData<ArrayList<User>>()

    private var userDao : FavouriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favouriteUserDao()
    }

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query = query)
            .enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {

                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun addToFav(username:String , id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,id
            )
            userDao?.addToFavourite(user)
        }
    }

    suspend fun checkFav(id:Int) = userDao?.checkUser(id)

    fun removeFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteUser(id)
        }
    }
}