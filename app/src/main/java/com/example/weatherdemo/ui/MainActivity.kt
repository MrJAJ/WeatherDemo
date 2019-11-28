package com.example.weatherdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdemo.Manager.ToolbarManager
import com.example.weatherdemo.adapter.ForecastListAdapter
import com.example.weatherdemo.R
import com.example.weatherdemo.domain.RequestForecastCommand
import com.example.weatherdemo.util.DelegatesExt
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity(),ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
        SettingsActivity.DEFAULT_ZIP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }
    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = async {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread{
            forecastList.adapter=ForecastListAdapter(result) {
                startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to result.city)
            }
            toolbarTitle = "${result.city} (${result.country})"
        }
    }

}

