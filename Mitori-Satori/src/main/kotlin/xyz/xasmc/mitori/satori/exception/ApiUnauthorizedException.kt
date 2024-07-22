package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiUnauthorizedException : SatoriApiException(HttpStatusCode.Unauthorized)