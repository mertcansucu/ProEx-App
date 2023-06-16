package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class register_page : AppCompatActivity() {
    lateinit var dba : SQLiteDatabase
    lateinit var rsaa : Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        var registerPageButton=findViewById<Button>(R.id.registerPageButtonRegister)
        var registerPageRadioGroup=findViewById<RadioGroup>(R.id.registerPageRadioGroup)

        val registerPageEditTextNameSurname = findViewById<EditText>(R.id.registerPageEditTextNameSurname)
        val registerPageEditTextUserName = findViewById<EditText>(R.id.registerPageEditTextUserName)
        val registerPageEditTextEmail= findViewById<EditText>(R.id.registerPageEditTextEmail)
        val registerPageEditTextPassword = findViewById<EditText>(R.id.registerPageEditTextPassword)
        var registerPageEditTextConfirmPassword=findViewById<EditText>(R.id.registerPageEditTextConfirmPassword)

        var adataBaseHelper = dataBaseHelper(applicationContext)
        dba = adataBaseHelper.readableDatabase
        rsaa = dba.rawQuery("SELECT * FROM DATABASETABLE",null)

        var selectedIndex:Int
        var gender:String

        registerPageButton.setOnClickListener {
            selectedIndex=registerPageRadioGroup.checkedRadioButtonId

            if (selectedIndex==2131231217){
                print(selectedIndex)
                gender="Erkek"
            }else if (selectedIndex==2131231219){
                gender="Kadın"
            }else{
                gender="Diğer"
            }

            if (registerPageEditTextPassword.text.toString() == registerPageEditTextConfirmPassword.text.toString()){

                var cv = ContentValues()
                cv.put("NAMESURNAME",registerPageEditTextNameSurname.text.toString())
                cv.put("USERNAME",registerPageEditTextUserName.text.toString())
                cv.put("EMAIL",registerPageEditTextEmail.text.toString())
                cv.put("PASSWORD",registerPageEditTextPassword.text.toString())
                cv.put("GENDER",gender)
                cv.put("POINTS","00-00-0000")
                cv.put("TARGET","1")
                cv.put("DAY","0")
                cv.put("COUNTER","00-00-0000")
                dba.insert("DATABASETABLE",null,cv)
                rsaa.requery()

                var ad = AlertDialog.Builder(this)
                ad.setTitle("Yeni Kayıt")
                ad.setMessage("Kayıt Başarılı")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                })
                ad.show()
            }else{
                Toast.makeText(applicationContext,"Lütfen şifrenizi tekrardan onaylayınız", Toast.LENGTH_LONG).show()
                registerPageEditTextPassword.setText("")
                registerPageEditTextConfirmPassword.setText("")
            }

        }

    }
}