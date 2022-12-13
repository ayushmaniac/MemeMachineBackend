package com.meme.machine.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


@Serializable
data class UserResponse(
    val userId : Int,
    val userName : String,
    val userEmail : String
)

object UserTable : Table(){
    val userId : Column<Int> = integer("user_id").autoIncrement()
    val userName : Column<String> = varchar("name", 512)
    val userPassword : Column<String> = varchar("password", 512)
    val userEmail : Column<String> = varchar("email", 512)

    override val primaryKey: PrimaryKey = PrimaryKey(userId)
}