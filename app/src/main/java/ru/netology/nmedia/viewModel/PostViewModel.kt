package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.PostRepoInMemoryImpl

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepoInMemoryImpl()

    val data = repository.getAll()

    fun like(postId: Long) = repository.likeById(postId)

    fun  share(postId: Long) = repository.shareById(postId)


//    fun like(post: Post) = repository.likeById(post.id)
//
//    fun  share(post: Post) = repository.shareById(post.id)

}