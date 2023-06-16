package com.mertcansucu.bitirmeprojesi_mertcansucu_alhasatahankizilkaya

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class dataBaseHelper(context: Context) : SQLiteOpenHelper(context,"DTBSHLPR",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE DATABASETABLE(_id integer primary key autoincrement,NAMESURNAME TEXT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,GENDER TEXT,POINTS TEXT,TARGET TEXT,DAY TEXT,COUNTER TEXT)")
        db?.execSQL("INSERT INTO DATABASETABLE(NAMESURNAME,USERNAME,EMAIL,PASSWORD,GENDER,POINTS,TARGET,DAY,COUNTER)VALUES('Atahan Kızılkaya','atahan07','atahan@gmail.com','1234','Erkek','00-00-0000','1','0','00-00-0000')")


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}