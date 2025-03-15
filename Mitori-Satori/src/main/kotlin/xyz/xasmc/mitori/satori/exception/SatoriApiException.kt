package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

abstract class SatoriApiException(private val httpStatusCode: HttpStatusCode) : Exception(httpStatusCode.description) {
    val httpCode get() = httpStatusCode.value
    val description get() = httpStatusCode.description
}
