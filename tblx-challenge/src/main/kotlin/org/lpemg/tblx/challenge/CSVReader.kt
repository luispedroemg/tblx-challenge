package org.lpemg.tblx.challenge

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.lpemg.tblx.challenge.data.model.Entry
import java.nio.file.Files
import java.nio.file.Paths

class CSVReader {
    fun readCSVFile(path: String): List<Entry> {
        val reader = Files.newBufferedReader(Paths.get(path))
        return CSVParser(reader, CSVFormat.DEFAULT).map { Entry.fromCSVRecord(it) }
    }
}