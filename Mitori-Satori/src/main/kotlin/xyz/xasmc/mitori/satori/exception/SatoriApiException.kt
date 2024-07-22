package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

abstract class SatoriApiException(private val httpStatusCode: HttpStatusCode) : Exception(httpStatusCode.description) {
    fun getHttpCode() = httpStatusCode.value
    fun getDescription() = httpStatusCode.description
}
