package ru.netology.nmedia

data class Post(
    val postId: Long,
    val author: String,
    val content: String,
    val published: String,
    var repostCount: Int = 1999998,
    var viewCount: Int = 10999,
    var likes: Int = 2999,
    var likedByMe: Boolean = false
)