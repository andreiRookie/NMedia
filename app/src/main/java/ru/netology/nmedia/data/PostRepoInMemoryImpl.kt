package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl : PostRepository {

//      сразу список в data
//    private var data = MutableLiveData(
//        List(30) { index ->
//            Post(
//                id = index + 1L,
//                author = "Нетология пост блаблаблаьлаблаблаблаблабла",
//                content = "Пост #${index + 1L}: Необходимо в ваш проект добавить реализацию отображения списков на базе RecyclerView и ListAdapter.\n" +
//                        "В примерах на лекции у нас был только OnLikeListener, вам же, по понятным причинам, нужно сделать ещё OnShareListener.",
//                published = "30 june 2022"
//            )
//        }
//    )

    private var posts = listOf<Post>()

    init {
        repeat(3333) { index ->
            val post = Post(
                id = index + 1L,
                author = "Нетология пост блаблаблаьлаблаблаблаблабла",
                content = "Пост #${index + 1L}: Необходимо в ваш проект добавить реализацию отображения списков на базе RecyclerView и ListAdapter.\n" +
                        "В примерах на лекции у нас был только OnLikeListener, вам же, по понятным причинам, нужно сделать ещё OnShareListener.",
                published = "30 june 2022"
            )
            posts += post
        }
    }

//    init {
//        for (i in 0 until 30) {
//            val post = Post(
//                id = i + 1L,
//                author = "Нетология пост блаблаблаьлаблаблаблаблабла",
//                content = "Пост #${i + 1L}: Необходимо в ваш проект добавить реализацию отображения списков на базе RecyclerView и ListAdapter.\n" +
//                        "В примерах на лекции у нас был только OnLikeListener, вам же, по понятным причинам, нужно сделать ещё OnShareListener.",
//                published = "30 june 2022"
//            )
//            posts += post
//        }
//    }


    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(postId: Long) {
        println("like button clicked")
//        posts.map {
//
//            if (it.id != postId) it else
//
//                run {
//                    if (it.likedByMe) it.copy(likes = it.likes - 1)
//                    else it.copy(likes = it.likes + 1)
//                }
//
//        }
//
//
//
//        data.value = posts.map {
//            if (it.id != postId) it else it.copy(likedByMe = !it.likedByMe)
//        }

        posts = posts.map {
            if (it.id != postId) it else
                run {
                    if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                    else it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
                }
        }
        data.value = posts

//
//        post = if (post.likedByMe)
//            post.copy(likes = post.likes - 1)
//        else post.copy(likes = post.likes + 1)
//
//        data.value = post
//
//        post = post.copy(likedByMe = !post.likedByMe)
//        data.value = post
    }

    override fun shareById(postId: Long) {
        println("share button clicked")
        posts = posts.map {
            if (it.id != postId) it else it.copy(repostCount = it.repostCount + 1)
        }
        data.value = posts
    }

}