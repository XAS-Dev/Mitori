package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.LoginApiHandler

fun Route.loginApiRouter(handler: LoginApiHandler) {
    post("login.get") {
        val result = handler.loginGet()
        call.respond(HttpStatusCode.OK, result)
    }
}