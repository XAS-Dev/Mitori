package xyz.xasmc.mitori.mitorivelocity.impl

import xyz.xasmc.mitori.mitorivelocity.MitoriVelocity
import xyz.xasmc.mitori.satori.api.LoginApiHandler

class LoginApiImpl : LoginApiHandler {
    private val plugin = MitoriVelocity.instant
    private val satoriServer = plugin.satoriServer

    override fun loginGet() = satoriServer.getLogin()
}