package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.PostRepoInMemoryImpl

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepoInMemoryImpl()

    val data = repository.get()

    fun like() = repository.like()

    fun  share() = repository.share()
}