package ru.netology.nmedia.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DraftContentSharedPrefs(context: Context) {

    private val gson = Gson()

    private val prefs = context.getSharedPreferences("draft", Context.MODE_PRIVATE)

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