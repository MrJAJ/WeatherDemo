package com.example.weatherdemo

import com.example.weatherdemo.domain.Forecast
import com.example.weatherdemo.domain.ForecastDataSource
import com.example.weatherdemo.domain.ForecastList
import com.example.weatherdemo.domain.ForecastProvider
import com.example.weatherdemo.util.toDateString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.text.DateFormat

class ExtensionsTest {
    @Test
    fun testLongToDateString() {
        assertEquals("Oct 19, 2015", 1445275635000L.toDateString())
    }

    @Test
    fun testDateStringFullFormat() {
        assertEquals("Monday, October 19, 2015", 1445275635000L.toDateString(DateFormat.SHORT))
    }

    @Test
    fun testDataSourceReturnsValue() {
        val ds = mock(ForecastDataSource::class.java)
        `when`(ds.requestDayForecast(0)).then {
            Forecast(0, "0", "desc", 20, 0, "url")
        }
        val provider = ForecastProvider(listOf(ds))
        assertNotNull(provider.requestForecast(0))
    }

    @Test
    fun emptyDatabaseReturnsServerValue() {
        val db = mock(ForecastDataSource::class.java)
        val server = mock(ForecastDataSource::class.java)
        `when`(
            server.requestForecastByZipCode(
                any(Long::class.java), any(Long::class.java)
            )
        )
            .then {
                ForecastList(0, "city", "country", listOf())
                val provider = ForecastProvider(listOf(db, server))
                assertNotNull(provider.requestByZipCode(0, 0))
            }
    }
}