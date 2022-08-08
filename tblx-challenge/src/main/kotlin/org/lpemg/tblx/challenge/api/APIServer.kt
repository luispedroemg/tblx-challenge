package org.lpemg.tblx.challenge.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.validation.JavalinValidation
import io.javalin.core.validation.ValidationException
import org.lpemg.tblx.challenge.api.controller.EntryController
import org.lpemg.tblx.challenge.api.log.requestLogger
import org.lpemg.tblx.challenge.data.access.IEntryDataAccess
import org.slf4j.LoggerFactory
import java.time.Instant

class APIServer(dataAccess: IEntryDataAccess, version: KotlinVersion) : IAPIServer<IEntryDataAccess> {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val app: Javalin = Javalin.create { config ->
        config.requestLogger { ctx, ms -> requestLogger(logger)(ctx,ms) }
    }.apply {
        exception(Exception::class.java) { e, ctx ->
            e.printStackTrace()
            ctx.status(500)
        }
        exception(ValidationException::class.java) { e, ctx ->
            ctx.status(400).json(e.errors)
        }
    }

    init {
        JavalinValidation.register(Instant::class.java) { Instant.parse(it) }
        val entryController = EntryController(dataAccess)
        app.routes {
            get("api/v${version.major}/entry") { ctx -> entryController.getEntry(ctx) }
            get("api/v${version.major}/operator/{id}/vehicle") { ctx -> entryController.getOperatorVehicles(ctx) }
            get("api/v${version.major}/vehicle/{id}/position") { ctx -> entryController.getVehiclePositions(ctx) }
        }
    }

    override fun start(port: Int) {
        app.start(port)
    }
}

