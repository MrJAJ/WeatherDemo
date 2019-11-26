package com.example.weatherdemo.ui

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weatherdemo.R
import com.example.weatherdemo.domain.Command
import com.example.weatherdemo.domain.Forecast
import com.example.weatherdemo.domain.ForecastProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.ctx
import org.jetbrains.anko.uiThread
import com.example.weatherdemo.util.toDateString
import java.text.DateFormat
import kotlinx.android.synthetic.main.activity_detail.*

public class DetailActivity : AppCompatActivity() {
    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }
    class RequestDayForecastCommand(
        val id: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {
        override fun execute() = forecastProvider.requestForecast(id)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = intent.getStringExtra(CITY_NAME)
        async {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread { bindForecast(result) }
        }
    }
    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toLong().toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature
        )
    }
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first.toString()}"
        it.second.setTextColor(color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        }))
    }
    private fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)
}