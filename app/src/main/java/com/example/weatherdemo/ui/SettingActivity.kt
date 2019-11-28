package com.example.weatherdemo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherdemo.R
import com.example.weatherdemo.util.DelegatesExt
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        val ZIP_CODE = "zipCode"
        val DEFAULT_ZIP = 94043L
    }
    var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE,DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cityCode.setText(zipCode.toString())
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> { onBackPressed(); true }
        else -> false
    }
    override fun onBackPressed() {
        super.onBackPressed()
        zipCode = cityCode.text.toString().toLong()
    }
}