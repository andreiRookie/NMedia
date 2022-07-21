package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl : PostRepository {

    private var post = Post(
        postId = 1,
        author = "Нетология пост блаблаблаьлаблаблаблаблабла",
        content = "Обратите внимание на блок с количеством просмотров. С ним есть небольшая проблема (поскольку он расположен справа). Если количество просмотров вырастет, например, до 500, то есть целых два варианта:\n" +
                "\n" +
                "Отталкиваться от текста (т.е. установить фиксированное расстояние от текста до границы родителя, а саму иконку приклеить к границе текста)\n" +
                "Оставить достаточное количество места, чтобы уместилось и 500 и 1К, тогда на всех карточках положение этого блока будет одинаковым\n" +
                "Вам нужно провести небольшое исследование и посмотреть, каким образом это реализовано в Vk.",
        published = "18 june 2022"
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = if (post.likedByMe)
            post.copy(likes = post.likes - 1)
        else post.copy(likes = post.likes + 1)

        data.value = post

        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
    }

    override fun share() {
        post = post.copy(repostCount = post.repostCount + 1)
        data.value = post
    }

}