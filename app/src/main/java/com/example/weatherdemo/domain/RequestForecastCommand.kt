package com.example.weatherdemo.domain

import com.example.weatherdemo.date.ForecastRequest

class RequestForecastCommand(val zipCode: String) :
    Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(
            forecastRequest.execute())
    }
}