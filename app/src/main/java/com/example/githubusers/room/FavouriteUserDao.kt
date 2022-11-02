package com.example.githubusers.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface FavouriteUserDao {
    @Insert
    suspend fun addToFavourite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM fav_user")
    fun getFavouriteUser():LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    suspend fun checkUser(id:Int) : Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    suspend fun deleteUser(id: Int) : Int
}