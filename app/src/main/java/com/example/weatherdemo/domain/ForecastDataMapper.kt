package com.example.weatherdemo.domain

import com.example.weatherdemo.date.Forecast
import com.example.weatherdemo.date.ForecastResult
import java.text.DateFormat
import java.util.*

import com.example.weatherdemo.domain.Forecast as ModelForecast

public class ForecastDataMapper {
    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list)
        )
    }
        private fun convertForecastListToDomain(list: List<Forecast>) : List<ModelForecast> {
            return list.map { convertForecastItemToDomain(it) }
        }
        private fun convertForecastItemToDomain(forecast: Forecast):ModelForecast {
            return ModelForecast(convertDate(forecast.dt),forecast.weather[0].description, forecast.temp.max.toInt(),forecast.temp.min.toInt(), generateIconUrl(forecast.
                weather[0].icon))
        }
        private fun convertDate(date: Long): String {
            val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
            return df.format(date * 1000)
        }
    }