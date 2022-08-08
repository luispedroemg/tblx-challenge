package org.lpemg.tblx.challenge.data.model
import java.time.Instant

data class TimeFrame(
    val start: Instant,
    val end: Instant,
){
    init {
        if(start > end){
            throw Exception("Invalid timeframe parameters")
        }
    }
}