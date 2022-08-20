package ru.netology.nmedia.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postId")
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

fun fromDto(post: Post) =
    PostEntity(post.id, post.author, post.content, post.published, post.video,
        post.repostCount, post.viewCount, post.likes, post.likedByMe)