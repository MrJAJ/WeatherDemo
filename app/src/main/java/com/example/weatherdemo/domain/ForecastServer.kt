package com.example.weatherdemo.domain

import com.example.weatherdemo.database.ForecastDb
import com.example.weatherdemo.date.ForecastRequest

class ForecastServer(val dataMapper: ForecastDataMapper = ForecastDataMapper(),
                     val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastRequest(zipCode.toString()).execute()
        val converted = dataMapper.convertFromDataModel(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long): Forecast?
            = throw UnsupportedOperationException()
}