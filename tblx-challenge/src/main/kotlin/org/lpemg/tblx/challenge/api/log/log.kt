package org.lpemg.tblx.challenge.api.log

import io.javalin.http.Context
import org.slf4j.Logger

fun requestLogger(logger: Logger): (Context, Float) -> Unit {
    return fun(ctx: Context, ms: Float) = logger.info("${ctx.ip()} : ${ctx.url()} : ${ctx.status()} : $ms")
}