package org.lpemg.tblx.challenge.api.controller

import io.javalin.http.Context
import org.lpemg.tblx.challenge.data.access.IEntryDataAccess
import org.lpemg.tblx.challenge.data.model.GPSEntry
import org.lpemg.tblx.challenge.data.model.Operator
import org.lpemg.tblx.challenge.data.model.TimeFrame
import org.lpemg.tblx.challenge.data.model.Vehicle
import java.time.Instant

class EntryController(private val dataAccess: IEntryDataAccess) {
    fun getEntry(ctx:  Context){
        ctx.json(dataAccess.find(getTimeframeFromQuery(ctx)))
        ctx.status(200)
    }

    fun getOperatorVehicles(ctx:  Context){
        val operator = Operator(ctx.pathParamAsClass<String>("id").get())
        val timeFrame = getTimeframeFromQuery(ctx)
        val entries = dataAccess.find(timeFrame, operator)
        ctx.json(entries.map{ entry -> Vehicle(entry.vehicleId) }.toSet())
        ctx.status(200)
    }

    fun getVehiclePositions(ctx: Context){
        val vehicle = Vehicle(ctx.pathParamAsClass<String>("id").get())
        val timeFrame = getTimeframeFromQuery(ctx)
        val entries = dataAccess.find(timeFrame, vehicle)
        ctx.json(entries.map { entry -> GPSEntry(entry.timestamp, entry.lat, entry.long) })
        ctx.status(200)
    }

    // TODO: Find a way to register a translator for Timeframe with multiple query params in javalin
    private fun getTimeframeFromQuery(ctx: Context): TimeFrame {
        val timeframeStart = ctx.queryParamAsClass<Instant>("timeframe[start]").get()
        val timeframeEnd = ctx.queryParamAsClass<Instant>("timeframe[end]").
            check({ it.isAfter(timeframeStart) }, "Invalid timeframe").get()
        return TimeFrame(timeframeStart, timeframeEnd)
    }
}