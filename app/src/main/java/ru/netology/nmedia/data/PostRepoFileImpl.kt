package ru.netology.nmedia.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepoFileImpl(private val context: Context) : PostRepository {


    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val fileName = "posts.json"


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


    init {
        // В данном примере мы не обрабатываем исключения.
        // Вполне возможно, что по каким-то причинам файл окажется « битым»
        // и GSON не сможет его распарсить.Поэтому попытку чтения нужно
        // завернуть в try-catch и выбрать стратегию для обработки исключения
        // (например, перезатирать файл пустыми данными — тогда пользователь потеряет все свои посты).
        val file = context.filesDir.resolve((fileName))
        if (file.exists()) {
            //если есть файл - читаем
            context.openFileInput(fileName).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            //если нет файла - записываем пустой массив
            sync()
        }
    }


    init {
        println("${posts.maxOf { post -> post.id }}")
        uniqueId = posts.maxOf { post -> post.id }
    }


    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }


    }

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
        sync()
    }

    override fun shareById(postId: Long) {
        println("share button clicked")
        posts = posts.map {
            if (it.id != postId) it else it.copy(repostCount = it.repostCount + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(postId: Long) {
        println("remove button clicked")
        posts = posts.filter { it.id != postId }
        data.value = posts
        sync()
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
            sync()
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }
}