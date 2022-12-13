package com.meme.machine.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

@Serializable
data class MemesResponse(
    val memeId : Int,
    val memeType : String,
    val memeImageUrl : String,
    val memeCreator : String
)

object MemeTable : Table(){
    val memeId : Column<Int> = integer("memeId").autoIncrement()
    val memeType : Column<String> = varchar("memeType", 512)
    val memeImageUrl : Column<String> = varchar("memeImageUrl", 512)
    val memeCreator : Column<String> = varchar("memeCreator", 512)
}