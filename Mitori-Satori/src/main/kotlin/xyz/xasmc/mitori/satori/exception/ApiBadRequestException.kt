package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiBadRequestException : SatoriApiException(HttpStatusCode.BadRequest)