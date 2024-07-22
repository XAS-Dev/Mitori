package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiServerErrorException : SatoriApiException(HttpStatusCode.InternalServerError)