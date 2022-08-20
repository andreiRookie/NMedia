package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.database.PostDao

class PostRepoRoomImpl(
    private val dao: PostDao
) : PostRepository {

//    private var posts = emptyList<Post>()
    private val data = dao.getAll()

//    init {
//        posts = dao.getAll()
//        data.value = posts
//    }

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            Post(it.id, it.author, it.content, it.published, it.video, it.repostCount, it.viewCount, it.likes, it.likedByMe)
        }
    }

    override fun savePost(post: Post) {

        dao.savePost(fromDto(post))
    }
//        val id = post.id
//        val saved = dao.savePost(post)
//
//        posts = if (id == 0L) {
//            listOf(saved) + posts
//        } else {
//            posts.map {
//                if (it.id != id) it else saved
//            }
//        }
//        data.value = posts


    override fun likeById(postId: Long) {
        dao.likeById(postId)
//        posts = posts.map {
//            if (it.id != postId) it else it.copy(
//                likedByMe = !it.likedByMe,
//                likes =  if (it.likedByMe) it.likes - 1 else it.likes + 1
//              // likes = it.likes + if (it.likedByMe) -1 else +1
//            )
//        }
//        data.value = posts
    }

    override fun shareById(postId: Long) {
        dao.shareById(postId)
//        posts = posts.map {
//            if (it.id != postId) it else it.copy(
//                repostCount = it.repostCount + 1
//            )
//        }
//        data.value = posts
    }

    override fun removeById(postId: Long) {
        dao.removeById(postId)
//        posts = posts.filter { it.id != postId }
//        data.value = posts
    }
}