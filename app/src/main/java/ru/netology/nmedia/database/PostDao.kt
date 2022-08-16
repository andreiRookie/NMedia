package ru.netology.nmedia.database

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.Post

interface PostDao {

    fun getAll(): List<Post>

    fun likeById(postId: Long)

    fun shareById(postId: Long)

    fun removeById(postId: Long)

    fun savePost(post: Post): Post
}