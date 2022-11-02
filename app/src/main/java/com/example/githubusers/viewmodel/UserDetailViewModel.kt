package com.example.githubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.Api.RetrofitClient
import com.example.githubusers.model.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    val user = MutableLiveData<UserDetail>()

    fun setUserDetails(username:String){
        RetrofitClient.apiInstance
            .getUserDetails(username)
            .enqueue(object : Callback<UserDetail>{
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.e("",t.message)
                }

            })
    }

    fun getUserDetails():LiveData<UserDetail>{
        return user
    }

}