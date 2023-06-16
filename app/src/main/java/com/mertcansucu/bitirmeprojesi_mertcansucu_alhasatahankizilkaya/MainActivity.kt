package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var dba : SQLiteDatabase
    lateinit var rsa : Cursor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences=this.getSharedPreferences("SInfo", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        var logInButton=findViewById<Button>(R.id.loginPageButtonLogIn)
        var logInEditTextUserNameEmail=findViewById<EditText>(R.id.loginPageEditTextEmailUserName)
        var logInEditTextPassword=findViewById<EditText>(R.id.loginPageEditTextPassword)
        var logInRegisterPageRouteTextView=findViewById<TextView>(R.id.loginPageTextViewRegister)

        var adataBaseHelper = dataBaseHelper(applicationContext)
        dba = adataBaseHelper.readableDatabase

        logInButton.setOnClickListener {

            var args = listOf<String>(logInEditTextUserNameEmail.text.toString(),logInEditTextPassword.text.toString()).toTypedArray()
            rsa = dba.rawQuery("SELECT * FROM DATABASETABLE WHERE USERNAME = ? AND PASSWORD = ? ", args)
            if (rsa.moveToNext()){
                Toast.makeText(applicationContext,"Giriş başarılı,ana sayfaya yönlendiriliyor...",Toast.LENGTH_LONG).show()

                editor.putString("user",logInEditTextUserNameEmail.text.toString().trim())
                editor.commit()

                var intent=Intent(this,home_page::class.java)
                intent.putExtra("user",logInEditTextUserNameEmail.text.toString().trim())
                startActivity(intent)
                logInEditTextUserNameEmail.setText("")
                logInEditTextPassword.setText("")
            }else{
                Toast.makeText(applicationContext,"Giriş başarısız.Lütfen tekrar deneyiniz!",Toast.LENGTH_LONG).show()
            }
        }
        logInRegisterPageRouteTextView.setOnClickListener {
            var intent=Intent(this, register_page::class.java)
            startActivity(intent)
        }

    }

}