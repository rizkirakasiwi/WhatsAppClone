package com.kuhaku.whatsappclone.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


class db_user{

    companion object{
      const val TABLE_USER:String = "TABLE_USER"
        const val USERNAME:String = "USERNAME"
        const val PASSWORD:String = "PASSWORD"
    }
}

class db_user2{
    companion object{
        const val TABLE_USER2:String = "TABLE_USER2"
        const val  NAMA:String = "NAMA"
        const val EMAIL:String = "EMAIL"
        const val NO_HP:String = "NO_HP"
    }
}


class dbHelper(context: Context):ManagedSQLiteOpenHelper(context,"user.db", null, 1){


    companion object{
        private var instance:dbHelper? = null
        @Synchronized
        fun getInstance(context: Context):dbHelper{
            if(instance==null){
                instance=dbHelper(context.applicationContext)
            }
            return instance as dbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(db_user.TABLE_USER, true,
            db_user.USERNAME to TEXT + PRIMARY_KEY,
            db_user.PASSWORD to TEXT)

        db?.createTable(db_user2.TABLE_USER2, true,
                db_user2.NAMA to TEXT,
                db_user2.EMAIL to TEXT,
                db_user2.NO_HP to TEXT
            )


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.dropTable(db_user.TABLE_USER, true)
        db?.dropTable(db_user2.TABLE_USER2, true)
    }
}

val Context.database:dbHelper
    get() = dbHelper.getInstance(applicationContext)