package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.navigation_title.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

class MainFragment : Fragment() {
    lateinit var dbogr : SQLiteDatabase
    lateinit var rsogr: Cursor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences1: SharedPreferences
    private lateinit var editor1: SharedPreferences.Editor
    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var editor2: SharedPreferences.Editor
    private lateinit var homePageTitleTextView:TextView
    private lateinit var homePageStartButton:Button
    private lateinit var homePageExerciseProgramRectangleSubTitleTextView:TextView
    private lateinit var intent:Intent
    private lateinit var homePageExerciseProgramRectangleProgressBar:ProgressBar
    private lateinit var calendarView: CalendarView

    private lateinit var tarih : LocalDateTime
    var gun by Delegates.notNull<Int>()
    var ay by Delegates.notNull<Int>()
    var yil by Delegates.notNull<Int>()

    var a : String=""
    var b : String=""
    var atext1 : String=""
    var atarget : String=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        homePageTitleTextView=view.findViewById(R.id.homePageTitleTextView)

        homePageStartButton=view.findViewById(R.id.homePageStartButton)

        homePageExerciseProgramRectangleProgressBar=view.findViewById(R.id.homePageExerciseProgramRectangleProgressBar)

        homePageExerciseProgramRectangleSubTitleTextView=view.findViewById(R.id.homePageExerciseProgramRectangleSubTitleTextView)

        calendarView=view.findViewById(R.id.calendarView1)
        calendarView.dateTextAppearance = R.style.SelectedDateStyle
        sharedPreferences = requireContext().getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        val user=sharedPreferences.getString("user","")
        var arg1 = listOf<String>(user.toString()).toTypedArray()

        val adataBaseHelper = dataBaseHelper(requireContext())
        dbogr = adataBaseHelper.readableDatabase
        rsogr = dbogr.rawQuery("SELECT NAMESURNAME FROM DATABASETABLE WHERE USERNAME = ?",arg1)
        var atext=""
        while (rsogr.moveToNext()){
            atext=rsogr.getString(0)
        }

        homePageTitleTextView.text="Hoşgeldiniz,$atext"


        sharedPreferences1 = requireContext().getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor1=sharedPreferences1.edit()
        val user1=sharedPreferences1.getString("user","")
        var arg2 = listOf<String>(user1.toString()).toTypedArray()

        val adataBaseHelper1 = dataBaseHelper(requireContext())
        dbogr = adataBaseHelper1.readableDatabase
        rsogr = dbogr.rawQuery("SELECT DAY FROM DATABASETABLE WHERE USERNAME = ?",arg2)
        rsogr.moveToFirst() // İlk kayda git
        atext1=rsogr.getString(0)



        sharedPreferences2 = requireContext().getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor2=sharedPreferences2.edit()
        val user2=sharedPreferences2.getString("user","")
        var arg3 = listOf<String>(user2.toString()).toTypedArray()

        val adataBaseHelper2 = dataBaseHelper(requireContext())
        dbogr = adataBaseHelper2.readableDatabase
        rsogr = dbogr.rawQuery("SELECT TARGET FROM DATABASETABLE WHERE USERNAME = ?",arg3)
        rsogr.moveToFirst() // İlk kayda git
        atarget=rsogr.getString(0)



        if(atext1=="30"){
            var cv = ContentValues()
            cv.put("DAY","0")
            dbogr.update("DATABASETABLE",cv,"USERNAME = ?", arg1)
            rsogr.requery()
            var ad = AlertDialog.Builder(requireContext())
            ad.setTitle("Egzersiz Mesajı")
            ad.setMessage("Egzersiz programını başarıyla tamamladınız.Sağlıklı günler dileriz :)")
            ad.setPositiveButton("Tamam", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            ad.show()
        }
        rsogr.requery()
        homePageExerciseProgramRectangleSubTitleTextView.text="Gün: $atext1 / 30"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tarih = LocalDateTime.now()

            gun = tarih.dayOfMonth
            ay = tarih.monthValue
            yil = tarih.year
        }

        homePageStartButton.setOnClickListener {
            val userID = sharedPreferences.getString("user", "")
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = dateFormatter.format(Date())
            val sharedPreferences = requireContext().getSharedPreferences("ButtonClicked_$userID", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val buttonClickedToday = sharedPreferences.getString("ButtonClickedDate", "")
            if (buttonClickedToday != currentDate || atarget == "1") {
                editor.putString("ButtonClickedDate", currentDate)
                editor.apply()
                val intent = Intent(requireContext(), ExerciseActivity::class.java)
                startActivity(intent)
            }else {
                val ad = AlertDialog.Builder(requireContext())
                ad.setTitle("Egzersiz Mesajı")
                ad.setMessage("Egzersiz başarıyla tamamlandı. Lütfen yarın tekrar gelip egzersizinizi tamamlayınız.")
                ad.setPositiveButton("Tamam") { _, _ ->
                }
                ad.show()
            }
        }
        return view
    }

    override fun onResume() {
        simulateProgress()
        super.onResume()
    }

    private fun simulateProgress() {
        val progressMax = 30
        val progressIncrement = atext1.toInt()
        println("$atext1")
        homePageExerciseProgramRectangleProgressBar.max = progressMax
        var currentProgress = 0
        currentProgress += progressIncrement
        homePageExerciseProgramRectangleProgressBar.progress = currentProgress
    }


}