package ru.netology.nmedia.data

data class Post(
    val postId: Long,
    val author: String,
    val content: String,
    val published: String,
    val repostCount: Int = 1999998,
    var viewCount: Int = 10999,
    val likes: Int = 2999,
    val likedByMe: Boolean = false
)