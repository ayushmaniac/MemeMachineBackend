package com.meme.machine.plugins

import com.meme.machine.createUser
import com.meme.machine.getAllUser
import com.meme.machine.getUsers
import com.meme.machine.repo.UserRepository
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    val userRepo = UserRepository()
    routing {
        createUser(userRepo)
        getUsers(userRepo)
        getAllUser(userRepo)
        get("/ping"){
            call.respondText {
                "Ping Successfully"
            }
        }
    }
}


