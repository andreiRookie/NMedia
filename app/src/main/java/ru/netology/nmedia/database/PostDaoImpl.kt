package ru.netology.nmedia.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import ru.netology.nmedia.data.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {





    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "postId"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_REPOST_COUNT = "repostCount"
        const val COLUMN_VIEW_COUNT = "viewCount"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_LIKED_BY_ME = "likedByMe"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_VIDEO,
            COLUMN_REPOST_COUNT,
            COLUMN_VIEW_COUNT,
            COLUMN_LIKES,
            COLUMN_LIKED_BY_ME
        )

//        const val NAME = "posts"
//
        val DDL = """
            CREATE TABLE $TABLE (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_AUTHOR TEXT NOT NULL,
            $COLUMN_CONTENT TEXT NOT NULL,
            $COLUMN_PUBLISHED TEXT NOT NULL,
            $COLUMN_VIDEO TEXT,
            $COLUMN_REPOST_COUNT INTEGER NOT NULL DEFAULT 0,
            $COLUMN_VIEW_COUNT INTEGER NOT NULL DEFAULT 0,
            $COLUMN_LIKES INTEGER NOT NULL DEFAULT 0,
            $COLUMN_LIKED_BY_ME BOOLEAN NOT NULL DEFAULT false
            );
        """.trimIndent()
//
//        val ALL_COLUMNS_NAMES = Column.values().map {
//            it.columnName
//        }.toTypedArray()
//
//        enum class Column(val columnName: String) {
//            ID("id"),
//            AUTHOR("author"),
//            CONTENT("content"),
//            PUBLISHED("published"),
//            VIDEO("video"),
//            REPOST_COUNT("repostCount"),
//            VIEW_COUNT("viewCount"),
//            LIKES("likes"),
//            LIKED_BY_ME("likedByMe")
//        }
    }


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
                    repostCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_REPOST_COUNT)),
            viewCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEW_COUNT)),
            likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
            likedByMe =  getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0
            //TODO extension fun to extract boolean
            )
        }
    }

    override fun likeById(postId: Long) {
        db.execSQL("""
            UPDATE posts SET
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe =CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE postId = ?;
            """.trimIndent(), arrayOf(postId))
    }

    override fun shareById(postId: Long) {
        db.execSQL("""
            UPDATE posts SET
                repostCount = repostCount + 1
            WHERE postId = ?;
            """.trimIndent(), arrayOf(postId))
    }

    override fun removeById(postId: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(postId.toString())
        )
    }

    override fun savePost(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "Today")

            put(PostColumns.COLUMN_VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
        }

        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

}