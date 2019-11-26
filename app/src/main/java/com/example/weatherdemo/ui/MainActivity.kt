package com.example.weatherdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdemo.adapter.ForecastListAdapter
import com.example.weatherdemo.R
import com.example.weatherdemo.domain.RequestForecastCommand
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecastList.layoutManager = LinearLayoutManager(this)
        async() {
            val result = RequestForecastCommand(94043).execute()
            uiThread{
               forecastList.adapter=
                   ForecastListAdapter(result) {
                       startActivity<DetailActivity>(DetailActivity.ID to it.id,
                           DetailActivity.CITY_NAME to result.city)
                   }
            }
        }
    }
}

