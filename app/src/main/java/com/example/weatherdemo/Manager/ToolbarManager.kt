package com.example.weatherdemo.Manager

import android.view.View
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdemo.App
import com.example.weatherdemo.R
import com.example.weatherdemo.date.ctx
import com.example.weatherdemo.ui.SettingsActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.startActivity

interface ToolbarManager {
    val toolbar: Toolbar
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar(){
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_setting -> toolbar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }
    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }
    private fun createUpDrawable() = with (DrawerArrowDrawable(toolbar.ctx)){
        progress = 1f
        this
    }
    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx:Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
    fun View.slideExit() {
        if (translationY == 0f) animate().translationY(-height.toFloat())
    }
    fun View.slideEnter() {
        if (translationY < 0f) animate().translationY(0f)
    }
}