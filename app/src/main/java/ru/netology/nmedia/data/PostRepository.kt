package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    fun get(): LiveData<Post>

    fun like()

    fun share()
}