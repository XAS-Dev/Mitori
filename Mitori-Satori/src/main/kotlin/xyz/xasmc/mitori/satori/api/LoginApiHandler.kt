package xyz.xasmc.mitori.satori.api

import xyz.xasmc.mitori.satori.datatype.login.Login
import xyz.xasmc.mitori.satori.exception.ApiMethodNotAllowedException

interface LoginApiHandler {
    fun loginGet(): Login {
        throw ApiMethodNotAllowedException()
    }
}