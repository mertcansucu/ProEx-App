package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.navigation_title.view.*

class home_page : AppCompatActivity() {
    lateinit var dbogr : SQLiteDatabase
    lateinit var rsogr: Cursor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        sharedPreferences = applicationContext.getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        val user=sharedPreferences.getString("user","")
        var arg1 = listOf<String>(user.toString()).toTypedArray()

        val adataBaseHelper = dataBaseHelper(applicationContext)
        dbogr = adataBaseHelper.readableDatabase
        rsogr = dbogr.rawQuery("SELECT NAMESURNAME FROM DATABASETABLE WHERE USERNAME = ?",arg1)
        var atext=""
        while (rsogr.moveToNext()){
            atext=rsogr.getString(0)
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(navigationView,navHostFragment.navController)
        toolbar.title = "ProEx App"
        val toggle1 = ActionBarDrawerToggle(this, drawer, toolbar, 0, 0)
        drawer.addDrawerListener(toggle1)
        toggle1.syncState()

        var baslik = navigationView.inflateHeaderView(R.layout.navigation_title)
        baslik.textViewBaslikOgretim.text=atext
    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}