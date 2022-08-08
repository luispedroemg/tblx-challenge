package org.lpemg.tblx.challenge

import org.lpemg.tblx.challenge.data.access.IEntryDataAccess
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DataLoader(private val dataAccess: IEntryDataAccess, private val csvReader: CSVReader) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    fun loadFromFilePath(path: String) : Boolean {
        logger.info("Loading data from $path")
        val entries = csvReader.readCSVFile(path)
        logger.info("Loaded ${entries.count()} entries")
        logger.info("Inserting into database...")

        return try {
            val result = dataAccess.bulkInsert(entries)
            if (result)
                logger.info("Finished inserting into DB")
            else
                logger.error("Unable to load entries into database")
            result
        }catch(e: Exception){
            e.printStackTrace()
            logger.error(e.message)
            false
        }
    }
}