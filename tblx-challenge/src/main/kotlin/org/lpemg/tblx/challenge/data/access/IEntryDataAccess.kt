package org.lpemg.tblx.challenge.data.access

import org.lpemg.tblx.challenge.data.model.Entry
import org.lpemg.tblx.challenge.data.model.Operator
import org.lpemg.tblx.challenge.data.model.TimeFrame
import org.lpemg.tblx.challenge.data.model.Vehicle

interface IEntryDataAccess{
    fun save(entry: Entry) : Boolean
    fun bulkInsert(entries: List<Entry>) : Boolean
    fun find(timeframe: TimeFrame): List<Entry>
    fun find(timeframe: TimeFrame, operator: Operator): List<Entry>
    fun find(timeframe: TimeFrame, vehicleId: Vehicle): List<Entry>
}