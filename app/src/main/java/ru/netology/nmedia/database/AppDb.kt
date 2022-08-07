package ru.netology.nmedia.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase


//singleton class, потому приватный конструктор
class AppDb private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {


        @Volatile
        private var instance: AppDb? = null

        //безопасное создание экземпляра через статич метод
        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase(context, arrayOf(*PostDaoImpl.PostColumns.ALL_COLUMNS))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDls: Array<String>) = DbHelper(
            context, 1, "app.db", DDls,
        ).writableDatabase
    }
}