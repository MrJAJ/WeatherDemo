package com.example.weatherdemo.database

import com.example.weatherdemo.domain.Forecast
import com.example.weatherdemo.domain.ForecastDataSource
import com.example.weatherdemo.domain.ForecastList
import com.example.weatherdemo.util.clear
import com.example.weatherdemo.util.parseList
import com.example.weatherdemo.util.parseOpt
import com.example.weatherdemo.util.toVarargArray
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.insert

class ForecastDb(
    private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
    private val dataMapper: DataMapper = DataMapper()): ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? " + "AND ${DayForecastTable.DATE}>= ?"
        val dailyForecast = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString()).parseList { DayForecast(HashMap(it)) }
        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID}=?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecast) }
        if (city != null) dataMapper.convertToDomain(city) else null
    }

    fun saveForecast(forecast: ForecastList)=forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)
        with(dataMapper.convertFromDomain(forecast)){
            insert(CityForecastTable.NAME,*map.toVarargArray())
            dailyForecast.forEach{insert(DayForecastTable.NAME,*it.map.toVarargArray())}
        }
    }

    fun requestDayForecast(id: Long): Forecast? =forecastDbHelper.use {
        val forecast=select(DayForecastTable.NAME).whereSimple("_id = ?",id.toString()).parseOpt{DayForecast(HashMap(it))}
        forecast?.let { dataMapper.convertDayToDomain(it) }
    }
}