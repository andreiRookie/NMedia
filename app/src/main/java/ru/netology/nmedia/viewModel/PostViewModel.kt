package ru.netology.nmedia.viewModel


import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.AndroidUtils
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.PostRepoInMemoryImpl


class PostViewModel : ViewModel() {


    private val emptyPost = Post(
        id = 0L,
        author = "",
        published = "09 july 2022"
    )

    private val repository: PostRepository = PostRepoInMemoryImpl()

    val data = repository.getAll()

    var edited = MutableLiveData(emptyPost)

    fun like(postId: Long) = repository.likeById(postId)

    fun  share(postId: Long) = repository.shareById(postId)

    fun remove(postId: Long) = repository.removeById(postId)


    fun edit(post: Post) {
        println("fun edit $post")
        edited.value = post
    }

    fun editTextCancel(view: TextView) {
        println("Edit canceled")
        view.text = ""
        view.clearFocus()
        AndroidUtils.hideKeyboard(view)
    }

    fun save() {
        edited.value?.let {
            println("${edited.value}, save() -> repository.savePost(it)")
            repository.savePost(it)
        }
        edited.value = emptyPost
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

}