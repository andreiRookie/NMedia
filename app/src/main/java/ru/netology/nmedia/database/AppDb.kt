package ru.netology.nmedia.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.nmedia.data.PostEntity


//singleton class, потому приватный конструктор

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var instance: AppDb? = null

        //безопасное создание экземпляра через статик метод
        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it}

            //    instance ?: AppDb(
            //        buildDatabase(context, arrayOf(PostDaoImpl.PostColumns.DDL))
            //    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, AppDb::class.java, "appRoom.db"
            ).allowMainThreadQueries().build()
    }
}