package com.example.weatherdemo.domain

import android.util.Log

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