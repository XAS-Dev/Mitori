package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.PagingList
import xyz.xasmc.mitori.satori.datatype.user.User
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface UserApiHandler {
    fun userGet(userId: String): User {
        throw ApiMethodNotAllowedException()
    }

    fun friendList(next: String?): PagingList<User> {
        throw ApiMethodNotAllowedException()
    }

    fun friendApprove(messageId: String, approved: Boolean, comment: String?) {
        throw ApiMethodNotAllowedException()
    }

}