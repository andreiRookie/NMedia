package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_item)
        println(R.string.greeting)
        println(getString(R.string.greeting))
        println(getString(R.string.app_name))
        println(getString(R.string.avatar_description))
    }
}