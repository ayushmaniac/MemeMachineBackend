package com.meme.machine.repo

import com.meme.machine.model.MemeTable
import com.meme.machine.model.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureDatabase() {
    Database.connect(hikari())
    transaction {
        SchemaUtils.create(UserTable)
        SchemaUtils.create(MemeTable)
    }
}

private fun hikari(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = System.getenv("JDBC_DRIVER")
    config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    return HikariDataSource(config)
}

suspend fun <T> dbQuery(block : () -> T) : T = withContext(Dispatchers.IO){
    transaction { block() }
}