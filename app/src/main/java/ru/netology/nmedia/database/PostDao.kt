package ru.netology.nmedia.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.data.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY postId DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content WHERE postId = :postId")
    fun updateContentByPostId(postId: Long, content: String)

    fun savePost(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentByPostId(post.id, post.content)


    @Query("""
       UPDATE posts SET
       likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
       likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
       WHERE postId = :postId
    """)
    fun likeById(postId: Long)

    @Query("""
        UPDATE posts SET
        repostCount = repostCount + 1
        WHERE postId = :postId
    """)
    fun shareById(postId: Long)

    @Query("DELETE FROM posts WHERE postId = :postId")
    fun removeById(postId: Long)
}