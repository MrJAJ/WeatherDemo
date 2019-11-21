package com.example.weatherdemo.domain

import com.example.weatherdemo.date.Forecast
import com.example.weatherdemo.date.ForecastResult
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

import com.example.weatherdemo.domain.Forecast as ModelForecast

public class ForecastDataMapper {
    fun convertFromDataModel(zipCode:Long,forecastResult: ForecastResult): ForecastList {
        return ForecastList(zipCode,
            forecastResult.city.name, forecastResult.city.country,
            convertForecastListToDomain(forecastResult.list)
        )
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ModelForecast(-1, dt.toString(), weather[0].description, temp.max.toInt(), temp.min.toInt(),
            generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
    }