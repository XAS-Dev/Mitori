package xyz.xasmc.mitori.satori.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.xasmc.mitori.satori.api.UserApiHandler
import xyz.xasmc.mitori.satori.datatype.api.User.FriendApprove
import xyz.xasmc.mitori.satori.datatype.api.User.FriendList
import xyz.xasmc.mitori.satori.datatype.api.User.UserGet

fun Route.userApiRouter(handler: UserApiHandler) {
    post("user.get") {
        val data = call.receive<UserGet>()
        val result = handler.userGet(data.user_id)
        call.respond(HttpStatusCode.OK, result)
    }
    post("friend.list") {
        val data = call.receive<FriendList>()
        val result = handler.friendList(data.next)
        call.respond(HttpStatusCode.OK, result)
    }
    post("friend.approve") {
        val data = call.receive<FriendApprove>()
        handler.friendApprove(data.message_id, data.approve, data.comment)
        call.respond(HttpStatusCode.OK)
    }
}