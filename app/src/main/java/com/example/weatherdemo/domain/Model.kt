package com.example.weatherdemo.domain

data class ForecastList(val id: Long,val city: String, val country: String,val dailyForecast:List<Forecast>){
    operator fun get(position: Int): Forecast = dailyForecast[position]
    fun size(): Int = dailyForecast.size
}
data class Forecast(val id: Long,val date: String, val description: String, val high: Int,val low: Int,val iconUrl:String)