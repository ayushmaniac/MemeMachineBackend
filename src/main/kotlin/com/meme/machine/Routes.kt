package com.meme.machine

import com.meme.machine.repo.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.Exception

const val CREATE_USER = "$API_VERSION/createUser"
const val DELETE_USER = "$API_VERSION/deleteUser"
const val UPDATE_USER = "$API_VERSION/updateUser"
const val GET_USER = "$API_VERSION/getUser"


@Location(CREATE_USER)
class CreateUser

@Location(DELETE_USER)
class DeleteUser

@Location(UPDATE_USER)
class UpdateUser


@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.createUser(
    userRepo: UserRepository
) {
    post<CreateUser> {
        val params = call.receive<Parameters>()
        val userName =
            params["userName"] ?: return@post call.respondText(
                text = "Missing Field",
                status = HttpStatusCode.Unauthorized
            )
        val userEmail =
            params["userEmail"] ?: return@post call.respondText(
                text = "Missing Field",
                status = HttpStatusCode.Unauthorized
            )

        val userPassword =
            params["userPassword"] ?: return@post call.respondText(
                text = "Missing Field",
                status = HttpStatusCode.Unauthorized
            )

        try {
            val user = userRepo.insert(userName, userEmail, userPassword)
            user?.userId?.let {
                call.respond(status = HttpStatusCode.OK, user)
            }
        } catch (e: Exception) {
            call.respondText { "${e.message}" }
        }
    }
}


fun Route.getUsers(
    userRepo: UserRepository
) {
    get("$API_VERSION/getUser/{userId}") {
        val params = call.parameters["userId"]
        try {
            val user = params?.toInt()?.let { userId ->
                userRepo.getUserByID(userId)
            } ?: return@get call.respondText(
                "Invalid userId",
                status = HttpStatusCode.BadRequest
            )
            user.userId.let {
                call.respond(status = HttpStatusCode.OK, user)
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Problems fetching user")
        }
    }
}

fun Route.getAllUser(
    userRepo: UserRepository
) {
    get("$API_VERSION/getAllUsers") {
        try {
            call.respond(status = HttpStatusCode.OK, userRepo.getAllUsers())
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Problems fetching users")

        }
    }

}

