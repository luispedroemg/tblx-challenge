package org.lpemg.tblx.challenge.data.model
import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import org.apache.commons.csv.CSVRecord
import java.time.Instant

@Measurement(name = "Entry")
data class Entry(
    @Column
    val timestamp: Instant,
    //val lineId: String,
    //val direction: Int,
    //val journeyId: String,
    //val timeframe: LocalDate,
    //val vehicleJourneyId: String,
    @Column(tag = true)
    val operator: String,
    //val congestion: Boolean,
    @Column
    val long: Float,
    @Column
    val lat: Float,
    //val delay: Int,
    //val blockId: String,
    @Column(tag = true)
    val vehicleId: String,
    @Column(tag = true)
    val stopId: String,
    @Column(tag = true)
    val isAtStop: Boolean
){
    companion object Factory {
        fun fromCSVRecord(csvRecord: CSVRecord): Entry {
            return Entry(
                Instant.ofEpochMilli(csvRecord.get(0).toLong() / 1000),
                //csvRecord.get(1),
                //csvRecord.get(2).toInt(),
                //csvRecord.get(3),
                //LocalDate.parse(csvRecord.get(4)),
                //csvRecord.get(5),
                csvRecord.get(6),
                //csvRecord.get(7).toInt() > 0,
                csvRecord.get(8).toFloat(),
                csvRecord.get(9).toFloat(),
                //csvRecord.get(10).toInt(),
                //csvRecord.get(11),
                csvRecord.get(12),
                csvRecord.get(13),
                csvRecord.get(14).toInt() > 0,
            )
        }
    }
}