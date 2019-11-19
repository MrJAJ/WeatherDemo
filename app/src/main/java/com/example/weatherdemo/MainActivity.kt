package com.example.weatherdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdemo.domain.Forecast
import com.example.weatherdemo.domain.RequestForecastCommand
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val forecastList : RecyclerView = find(R.id.forecast_list)
        forecastList?.layoutManager = LinearLayoutManager(this)
        async() {
            val result = RequestForecastCommand("94043").execute()
            uiThread{
                forecastList.adapter = ForecastListAdapter(result, object : ForecastListAdapter.OnItemClickListener{
                        override fun invoke(forecast: Forecast) {
                            toast(forecast.date)
                        }
                    })
            }
        }
    }

}

