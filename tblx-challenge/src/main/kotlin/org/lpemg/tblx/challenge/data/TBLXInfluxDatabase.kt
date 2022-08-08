package org.lpemg.tblx.challenge.data

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory

class TBLXInfluxDatabase : IDatabase<InfluxDBClientKotlin> {
    override val tblxDB: InfluxDBClientKotlin = InfluxDBClientKotlinFactory
        .create("http://localhost:8086", "my-token".toCharArray(), "my-org")
}