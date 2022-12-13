package com.meme.machine

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.meme.machine.repo.configureDatabase
import com.meme.machine.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

const val API_VERSION = "/v1"

fun main() {
    embeddedServer(Netty, port = 1001, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Locations)
    configureDatabase()
    configureRouting()

}
