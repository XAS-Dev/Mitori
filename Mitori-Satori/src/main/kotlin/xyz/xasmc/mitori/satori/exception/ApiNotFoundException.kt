package xyz.xasmc.mitori.satori.exception

import io.ktor.http.*

class ApiNotFoundException : SatoriApiException(HttpStatusCode.NotFound)