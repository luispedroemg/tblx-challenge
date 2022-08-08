package org.lpemg.tblx.challenge.data
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

class TBLXMongoDatabase(connectionString: String) : IDatabase<MongoDatabase> {
    private val client = KMongo.createClient(connectionString)
    override val tblxDB: MongoDatabase = client.getDatabase("tblx")
}