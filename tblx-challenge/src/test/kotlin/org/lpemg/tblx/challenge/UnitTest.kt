package org.lpemg.tblx.challenge

import com.mongodb.MongoException
import io.javalin.core.validation.JavalinValidation
import io.javalin.http.Context
import io.mockk.*
import org.junit.Test
import org.lpemg.tblx.challenge.api.controller.EntryController
import org.lpemg.tblx.challenge.data.access.IEntryDataAccess
import org.lpemg.tblx.challenge.data.model.Entry
import org.lpemg.tblx.challenge.data.model.TimeFrame
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UnitTest {
    private val ctx = mockk<Context>(relaxed = true)
    private val dataAccess = mockk<IEntryDataAccess>(relaxed = true)
    private val csvReader = mockk<CSVReader>(relaxed = true)
    private val entryList = List(1) { _ -> Entry(Instant.now(), "OP", 0f, 0f, "VEHICLE", "STOP_ID", false) }

    @Test
    fun `GET entry returns 200 status code`() {
        JavalinValidation.register(Instant::class.java) { Instant.parse(it) }

        every { ctx.queryParamAsClass<Instant>("timeframe[start]").get() } returns Instant.now()
        every { ctx.queryParamAsClass<Instant>("timeframe[end]").check(any(), ofType(String::class)).get() } returns Instant.now()
        every { dataAccess.find(any()) } returns entryList

        EntryController(dataAccess).getEntry(ctx)

        verify { ctx.status(200) }
    }

    @Test
    fun `Timeframe valid`() {
        val start = Instant.now()
        val end = start.plus(1, ChronoUnit.MINUTES)

        val timeFrame = TimeFrame(start, end)

        assertEquals(timeFrame.start, start)
        assertEquals(timeFrame.end, end)
    }

    @Test
    fun `Timeframe same instant is allowed`() {
        val start = Instant.now()
        val end = start

        val timeFrame = TimeFrame(start, end)

        assertEquals(timeFrame.start, start)
        assertEquals(timeFrame.end, end)
    }

    @Test
    fun `Timeframe reversed times not allowed`() {
        val start = Instant.now()
        val end = start.minus(1, ChronoUnit.MINUTES)
        assertFailsWith<Exception> { TimeFrame(start, end) }
    }

    @Test
    fun `DataLoader Test`() {
        every { csvReader.readCSVFile("test-path.csv") } returns entryList
        every { dataAccess.bulkInsert(entryList) } returns true
        val result = DataLoader(dataAccess,csvReader).loadFromFilePath("test-path.csv")
        assert(result)
    }

    @Test
    fun `DataLoader Insert not acknowledged`() {
        every { csvReader.readCSVFile("test-path.csv") } returns entryList
        every { dataAccess.bulkInsert(entryList) } returns false
        val result = DataLoader(dataAccess,csvReader).loadFromFilePath("test-path.csv")
        assert(!result)
    }

    @Test
    fun `DataLoader Insert Exception`() {
        every { csvReader.readCSVFile("test-path.csv") } returns entryList
        every { dataAccess.bulkInsert(entryList) } throws MongoException(0, "Fake Mongo Error")
        val result = DataLoader(dataAccess,csvReader).loadFromFilePath("test-path.csv")
        assert(!result)
    }
}
