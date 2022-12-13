package com.meme.machine.model.dao

import com.meme.machine.model.UserResponse

interface UserDao {

    suspend fun insert(
        userName : String,
        userEmail : String,
        userPassword : String
    ) : UserResponse?

    suspend fun getAllUsers() : List<UserResponse>?
    suspend fun getUserByID(userId : Int) : UserResponse?
    suspend fun deleteById(userId : Int) : Int
    suspend fun updateById(
        userId: Int,
        userName: String,
        userEmail: String,
        userPassword: String
    ): Int
}