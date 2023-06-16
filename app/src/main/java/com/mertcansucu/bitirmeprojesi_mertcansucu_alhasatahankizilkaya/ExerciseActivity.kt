package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import java.time.LocalDateTime
import kotlin.properties.Delegates

class ExerciseActivity : AppCompatActivity() {

    lateinit var dba : SQLiteDatabase
    lateinit var rsa : Cursor
    lateinit var rs : Cursor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var sayac=-1
    var aday : String=""
    var atarget : String=""
    private lateinit var sharedPreferences1: SharedPreferences
    private lateinit var editor1: SharedPreferences.Editor
    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var editor2: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        var baslatdurdur_durdurbutton=findViewById<Button>(R.id.baslatdurdur_durdurbutton)
        var back_button=findViewById<ImageView>(R.id.back_button)
        var homePageTitleTextView2 = findViewById<TextView>(R.id.homePageTitleTextView2)
        var gifImageView = findViewById<ImageView>(R.id.gifImageView)
        var gifImageView2 = findViewById<ImageView>(R.id.gifImageView2)
        var gifImageView3 = findViewById<ImageView>(R.id.gifImageView3)
        var gifImageView4 = findViewById<ImageView>(R.id.gifImageView4)
        var gifImageView5 = findViewById<ImageView>(R.id.gifImageView5)
        var gifImageView6 = findViewById<ImageView>(R.id.gifImageView6)

        sharedPreferences = this.getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        val user=sharedPreferences.getString("user","")
        var arg1 = listOf<String>(user.toString()).toTypedArray()

        val adataBaseHelper = dataBaseHelper(applicationContext)
        dba = adataBaseHelper.readableDatabase
        rsa = dba.rawQuery("SELECT NAMESURNAME FROM DATABASETABLE WHERE USERNAME = ?",arg1)
        var aatext=""
        while (rsa.moveToNext()){
            aatext=rsa.getString(0)
        }

        homePageTitleTextView2.text="Başarılar, $aatext"

        sharedPreferences1 = this.getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor1=sharedPreferences1.edit()
        val user1=sharedPreferences1.getString("user","")
        var arg2 = listOf<String>(user1.toString()).toTypedArray()

        val adataBaseHelper1 = dataBaseHelper(applicationContext)
        dba = adataBaseHelper1.readableDatabase
        rsa = dba.rawQuery("SELECT DAY FROM DATABASETABLE WHERE USERNAME = ?",arg2)
        rsa.moveToFirst() // İlk kayda git
        aday=rsa.getString(0)


        sharedPreferences2 = this.getSharedPreferences("SInfo", AppCompatActivity.MODE_PRIVATE)
        editor2=sharedPreferences2.edit()
        val user2=sharedPreferences2.getString("user","")
        var arg3 = listOf<String>(user2.toString()).toTypedArray()
        val adataBaseHelper2 = dataBaseHelper(applicationContext)

        dba = adataBaseHelper2.readableDatabase
        rsa = dba.rawQuery("SELECT TARGET FROM DATABASETABLE WHERE USERNAME = ?",arg3)
        rsa.moveToFirst() // İlk kayda git
        atarget=rsa.getString(0)

        back_button.setOnClickListener {

            var intent= Intent(this,home_page::class.java)
            startActivity(intent)
        }

        var cv2 = ContentValues()
        cv2.put("TARGET","1")
        dba.update("DATABASETABLE",cv2,"USERNAME = ?", arg3)


        baslatdurdur_durdurbutton.setOnClickListener {


            baslatdurdur_durdurbutton.text="Devam Et"
            println("sayac değeri: $sayac")
            sayac++

            println("if den önceki sayac: $sayac")

            if(sayac==0){
                gifImageView.visibility=View.VISIBLE
                gifImageView2.visibility=View.INVISIBLE
                gifImageView3.visibility=View.INVISIBLE
                gifImageView4.visibility=View.INVISIBLE
                gifImageView5.visibility=View.INVISIBLE
                gifImageView6.visibility=View.INVISIBLE
                println("sayac 0 kontrol")
            }else if(sayac==1){
                gifImageView.visibility=View.INVISIBLE
                gifImageView2.visibility=View.VISIBLE
                println("sayac 1 kontrol")
            }else if(sayac==2){
                gifImageView.visibility=View.INVISIBLE
                gifImageView2.visibility=View.INVISIBLE
                gifImageView3.visibility=View.VISIBLE
                println("sayac 2 kontrol")
            }else if(sayac==3){
                gifImageView.visibility=View.INVISIBLE
                gifImageView2.visibility=View.INVISIBLE
                gifImageView3.visibility=View.INVISIBLE
                gifImageView4.visibility=View.VISIBLE
                println("sayac 3 kontrol")
            }else if(sayac==4){
                gifImageView.visibility=View.INVISIBLE
                gifImageView2.visibility=View.INVISIBLE
                gifImageView3.visibility=View.INVISIBLE
                gifImageView4.visibility=View.INVISIBLE
                gifImageView5.visibility=View.VISIBLE
                println("sayac 4 kontrol")
            }else if(sayac==5){
                gifImageView.visibility=View.INVISIBLE
                gifImageView2.visibility=View.INVISIBLE
                gifImageView3.visibility=View.INVISIBLE
                gifImageView4.visibility=View.INVISIBLE
                gifImageView5.visibility=View.INVISIBLE
                gifImageView6.visibility=View.VISIBLE
                println("sayac 5 kontrol")
            }else{

                var cv1 = ContentValues()
                cv1.put("TARGET","0")
                dba.update("DATABASETABLE",cv1,"USERNAME = ?", arg3)

                var b:Int=aday.toInt()
                println("b verisi güncel olmadan : $b")
                b+=1

                println("b verisi: $b")

                var yazdir:String=b.toString()

                var cv = ContentValues()
                cv.put("DAY",yazdir.toString())
                dba.update("DATABASETABLE",cv,"USERNAME = ?", arg1)


                var ad = AlertDialog.Builder(this)
                ad.setTitle("Egzersiz Mesajı")
                ad.setMessage("Egzersiz başarıyla tamamlandı.Lütfen egzersiz programına 30 gün boyunca aralıksız uyun.")
                ad.setPositiveButton("Tamam", DialogInterface.OnClickListener { dialogInterface, i ->
                    intent= Intent(this,home_page::class.java)
                    startActivity(intent)
                })
                ad.show()

            }
        }


    }
}