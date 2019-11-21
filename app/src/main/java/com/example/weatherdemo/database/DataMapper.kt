package com.example.weatherdemo.database

import com.example.weatherdemo.domain.Forecast
import com.example.weatherdemo.domain.ForecastList


class DataMapper {
    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id,city, country, daily)
    }
    fun convertDayToDomain(dayForecast: DayForecast) =
        with(dayForecast) {
        Forecast(_id,date.toString(), description, high, low, iconUrl)
    }
    fun convertFromDomain(forecast:ForecastList)= with(forecast){
        val daily=dailyForecast.map { convertDayFromDomain(id,it) }
        CityForecast(id,city,country,daily)
    }

    fun convertDayFromDomain(cityId:Long,forecast:Forecast)= with(forecast){
        DayForecast(date.toLong(),description,high,low,iconUrl,cityId)
    }
}