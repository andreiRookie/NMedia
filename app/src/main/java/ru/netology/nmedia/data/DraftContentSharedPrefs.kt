package ru.netology.nmedia.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DraftContentSharedPrefs(context: Context) {

    private val gson = Gson()

    private val prefs = context.getSharedPreferences("draft", Context.MODE_PRIVATE)


//    возвращает ссылка на класс “список из постов”, то есть на сложную коллекцию.
    //    Потому что методу fromJson нужно указать данные какого типа мы ожидаем прочитать,
//    это как раз делается через указание класса по ссылке.
//    Но в черновике Json не нужен, ведь сам черновик - это строка. Ее можно сохранять в
//    sharedprefs методами putString и getString, преобразования через Json не нужно делать.
    private val type = TypeToken.getParameterized(String::class.java).type

    private val key = "draft"

    private var draft = ""

    init {
        prefs.getString(key, null)?.let {
            draft = gson.fromJson(it, type)
        }
    }


    fun saveDraft(str: String) {
        draft = str
        sync()
    }


    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(draft))
            apply()
        }
    }

    fun getDraft(): String { return draft }



}