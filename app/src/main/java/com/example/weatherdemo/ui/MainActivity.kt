package com.example.weatherdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdemo.adapter.ForecastListAdapter
import com.example.weatherdemo.R
import com.example.weatherdemo.domain.RequestForecastCommand
import com.example.weatherdemo.util.toDateString
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecastList.layoutManager = LinearLayoutManager(this)
        async() {
            val result = RequestForecastCommand(94043).execute()
            Log.d("end",result.toString())
            uiThread{
               forecastList.adapter=
                   ForecastListAdapter(result) {
                       toast(
                           it.date.toLong().toDateString()
                       )
                   }
            }
        }
    }

}

