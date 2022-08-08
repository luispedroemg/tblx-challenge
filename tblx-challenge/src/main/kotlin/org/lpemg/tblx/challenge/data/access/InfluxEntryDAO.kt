package org.lpemg.tblx.challenge.data.access

import com.influxdb.client.InfluxDBClient
import org.lpemg.tblx.challenge.data.IDatabase
import org.lpemg.tblx.challenge.data.model.Entry
import org.lpemg.tblx.challenge.data.model.Operator
import org.lpemg.tblx.challenge.data.model.TimeFrame
import org.lpemg.tblx.challenge.data.model.Vehicle

class InfluxEntryDAO(private val database: IDatabase<InfluxDBClient>) : IEntryDataAccess {
    override fun save(entry: Entry) : Boolean {
        TODO("Not yet implemented")
    }

    override fun bulkInsert(entries: List<Entry>): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(timeframe: TimeFrame): List<Entry> {
        TODO("Not yet implemented")
        val fluxQuery = ("from(bucket: \"tblx\")\n"
                + " |> range(start: -1d)"
                + " |> filter(fn: (r) => (r[\"_measurement\"] == \"cpu\" and r[\"_field\"] == \"usage_system\"))")
        return database.tblxDB.queryApi.query(fluxQuery, Entry::class.java)
    }

    override fun find(timeframe: TimeFrame, operator: Operator): List<Entry> {
        TODO("Not yet implemented")
    }

    override fun find(timeframe: TimeFrame, vehicleId: Vehicle): List<Entry> {
        TODO("Not yet implemented")
    }

}