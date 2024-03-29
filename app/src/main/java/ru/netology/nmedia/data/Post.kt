package ru.netology.nmedia.data

data class Post(
    val id: Long,
    val author: String,
    val content: String = "",
    val published: String,
    val video: String? = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
    val repostCount: Int = 1999998,
    var viewCount: Int = 10999,
    val likes: Int = 2999,
    val likedByMe: Boolean = false
)