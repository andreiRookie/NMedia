package ru.netology.nmedia.data



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class PostRepoInMemoryImpl : PostRepository {


    private var posts = listOf<Post>()


    private var uniqueId = 0L



    init {
        repeat(30) {
            val post = Post(
                id = uniqueId + 1L,
                author = "Нетология пост блаблаблаьлаблаблаблаблабла",
                content = "Пост #${uniqueId + 1L}: Необходимо в ваш проект добавить" +
                        " реализацию отображения списков на базе RecyclerView и ListAdapter.",
                published = "08 july 2022"
            )
            uniqueId++
            posts += post
        }
    }

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(postId: Long) {
        println("like button clicked")


        posts = posts.map {
            if (it.id != postId) it else
                run {
                    if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                    else it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
                }
        }
        data.value = posts
    }

    override fun shareById(postId: Long) {
        println("share button clicked")
        posts = posts.map {
            if (it.id != postId) it else it.copy(repostCount = it.repostCount + 1)
        }
        data.value = posts
    }

    override fun removeById(postId: Long) {
        println("remove button clicked")
        posts = posts.filter { it.id != postId }
        data.value = posts
    }

    override fun savePost(post: Post) {

        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = ++uniqueId,
                    author = "Me posting",
                    viewCount = 0,
                    published = "Now"
                )
            ) + posts

            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
    }

}