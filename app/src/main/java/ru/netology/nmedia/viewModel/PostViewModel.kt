package ru.netology.nmedia.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.SingleLiveEvent
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepoFileImpl
import ru.netology.nmedia.data.PostRepoSQLiteImpl
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.database.AppDb


class PostViewModel(application: Application) : AndroidViewModel(application) {


    private val emptyPost = Post(
        id = 0L,
        author = "",
        published = "09 july 2022"
    )

    private val repository: PostRepository = PostRepoSQLiteImpl(
        AppDb.getInstance(context = application).postDao
    )

    val data = repository.getAll()

    var edited = MutableLiveData(emptyPost)

    val navigateToNewPostActivityEvent = SingleLiveEvent<Unit>()

    val navigateToEditPostActivityEvent = SingleLiveEvent<String?>()  //<Post>?

    val playVideoEventViaYoutube = SingleLiveEvent<String?>()

    val sharePostContentEvent = SingleLiveEvent<String>()

    val navigateToSeparatePostFragmentEvent = SingleLiveEvent<Post>()

    fun like(postId: Long) = repository.likeById(postId)

    fun share(post: Post) {
        repository.shareById(post.id)
        sharePostContentEvent.value = post.content
    }

    fun remove(postId: Long) = repository.removeById(postId)


    fun edit(post: Post) {
        println("fun edit $post")
        println("${edited.value}")
        edited.value = post
        println("${edited.value}")

    }

//    fun editTextCancel(view: TextView) {
//        println("Edit canceled")
//        edited.value = emptyPost
//        view.text = ""
//        view.clearFocus()
//        AndroidUtils.hideKeyboard(view)
//    }

//    fun save() {
//        edited.value?.let {
//            println("${edited.value}, save() -> repository.savePost(it)")
//            repository.savePost(it)
//        }
//        edited.value = emptyPost
//    }

    fun changeContentAndSave(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
          repository.savePost(it.copy(content = content))

//           edited.value = it.copy(content = text)
       }
//        edited.value?.let {
//            repository.savePost(it)
//        }
        edited.value = emptyPost
    }

    fun addPost() {
        navigateToNewPostActivityEvent.call()

    }

    fun editPost(postContent: String?) { //(post: Post)
        navigateToEditPostActivityEvent.value = postContent
    }

    fun playVideo(videoUrl: String?) {
        println("fun playVideo($videoUrl")
        playVideoEventViaYoutube.value = videoUrl
    }

    fun navigateToSeparatePostFragment(post: Post) {
        navigateToSeparatePostFragmentEvent.value = post
    }

}