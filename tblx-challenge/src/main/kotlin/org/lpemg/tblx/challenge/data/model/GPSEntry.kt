package org.lpemg.tblx.challenge.data.model

import java.time.Instant

data class GPSEntry (
    val timestamp: Instant,
    val long: Float,
    val lat: Float
)