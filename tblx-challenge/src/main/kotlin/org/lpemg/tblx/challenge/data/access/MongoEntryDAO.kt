package org.lpemg.tblx.challenge.data.access
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*
import org.lpemg.tblx.challenge.data.IDatabase
import org.lpemg.tblx.challenge.data.model.Operator
import org.lpemg.tblx.challenge.data.model.TimeFrame
import org.lpemg.tblx.challenge.data.model.Vehicle
import org.lpemg.tblx.challenge.data.model.*

class MongoEntryDAO(database: IDatabase<MongoDatabase>) : IEntryDataAccess {
    private val collection = database.tblxDB.getCollection<Entry>()
    init {
        collection.ensureIndex(Entry::timestamp)
        collection.ensureIndex(Entry::timestamp, Entry::vehicleId)
        collection.ensureIndex(Entry::timestamp, Entry::operator)
    }

    override fun save(entry: Entry): Boolean {
        val result = collection.insertOne(entry)
        return result.wasAcknowledged()
    }

    override fun bulkInsert(entries: List<Entry>): Boolean {
        val result = collection.insertMany(entries)
        return result.wasAcknowledged()
    }

    override fun find(timeframe: TimeFrame): List<Entry> {
        return collection.find(
            Entry::timestamp gte timeframe.start, Entry::timestamp lte timeframe.end,
        ).toList()
    }

    override fun find(timeframe: TimeFrame, operator: Operator): List<Entry> {
        return collection.find(
            Entry::timestamp gte timeframe.start, Entry::timestamp lte timeframe.end,
            Entry::operator eq operator.id
        ).toList()
    }

    override fun find(timeframe: TimeFrame, vehicleId: Vehicle): List<Entry> {
        return collection.find(
            Entry::timestamp gte timeframe.start, Entry::timestamp lte timeframe.end,
            Entry::vehicleId eq vehicleId.id
        ).ascendingSort(Entry::timestamp).toList()
    }
}


