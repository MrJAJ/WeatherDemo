package com.example.weatherdemo.domain

class RequestForecastCommand(val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()) :
    Command<ForecastList> {
    companion object {
        val DAYS = 7
    }
    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}