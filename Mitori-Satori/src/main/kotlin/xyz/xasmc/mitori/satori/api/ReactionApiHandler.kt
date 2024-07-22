package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface ReactionApiHandler {
    fun reactionCreate(channelId: String, messageId: String, emoji: String) {
        throw ApiMethodNotAllowedException()
    }

    fun reactionDelete(channelId: String, messageId: String, emoji: String, userId: String?) {
        throw ApiMethodNotAllowedException()
    }

    fun reactionClear(channelId: String, messageId: String, emoji: String?) {
        throw ApiMethodNotAllowedException()
    }

    fun reactionList(channelId: String, messageId: String, emoji: String, next: String?): PagingList<User> {
        throw ApiMethodNotAllowedException()
    }
}