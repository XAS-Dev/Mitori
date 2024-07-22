package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiForbiddenException : SatoriApiException(HttpStatusCode.Forbidden)