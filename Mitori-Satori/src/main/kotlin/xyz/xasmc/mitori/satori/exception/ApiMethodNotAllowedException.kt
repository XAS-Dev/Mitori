package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiMethodNotAllowedException : SatoriApiException(HttpStatusCode.MethodNotAllowed)