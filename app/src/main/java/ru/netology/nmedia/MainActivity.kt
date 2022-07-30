package ru.netology.nmedia


import android.content.Intent
import android.content.pm.PackageManager.MATCH_ALL
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels

import ru.netology.nmedia.activity.EditPostResultContract
import ru.netology.nmedia.activity.NewPostResultContract
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.data.Post


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("!!!SavedInstanceState: ${savedInstanceState.toString()}")



        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type= "text/plain"
                }

                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

                viewModel.share(post)
            }

            override fun onVideo(post: Post) {
                viewModel.playVideo(post.video)
            }


            override fun onRemove(post: Post) {
                viewModel.remove(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

        })

        binding.postsRecyclerView.adapter = adapter


        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { postContent ->
            postContent ?: return@registerForActivityResult  //вызовется после
            viewModel.changeContentAndSave(postContent)      //parseResult
        }

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { postContent ->
            println("editPostLauncher: $postContent")
            postContent ?: return@registerForActivityResult  //вызовется после
            viewModel.changeContentAndSave(postContent)      //parseResult
        }



        binding.addPostFab.setOnClickListener {
            viewModel.addPost()
        }

        viewModel.navigateToNewPostActivityEvent.observe(this) {
            println("newPostLauncher.launch()")
            newPostLauncher.launch()
        }

        viewModel.navigateToEditPostActivityEvent.observe(this) {
            println("editPostLauncher.launch($it)")
            editPostLauncher.launch(it)
        }

        viewModel.playVideoEventViaYoutube.observe(this) {
            println("playVideoEvent.observe: $it")
            val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))


            val pm = this.packageManager
            playVideoIntent.resolveActivity(pm)
            println("${(pm.queryIntentActivities(playVideoIntent, MATCH_ALL))}")


                startActivity(playVideoIntent)
        }

        viewModel.data.observe(this) { posts ->
            println("viewModel.data.observe")
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) {
            println("viewModel.edited.observe: ${it.id == 0L}")

            if (it.id == 0L) return@observe

            viewModel.editPost(it.content)
        }

//            val binding= NewPostActivityBinding.inflate(layoutInflater)
//            setContentView(binding.root)
//            binding.edit.setText(it.content)
//
//
//
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT,it.content)
//                type= "text/plain"
//                setResult(Activity.RESULT_OK, intent)
//
//            }




        viewModel.sharePostContent.observe(this) {
            println("viewModel.sharePostContent.observe")


            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type= "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

    }

}

//        binding.editCancel.setOnClickListener {
//            with(binding.contentTextEdit) {
//                viewModel.editTextCancel(this)
//                editGroupBinding.visibility = View.GONE
//            }
//        }
//
//        binding.saveContentButton.setOnClickListener {
//            with(binding.contentTextEdit) {
//
//
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Can't be empty",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                // в один метод свести чендж и сейв надо ли??:
//                viewModel.changeContentAndSave(text.toString())
//                viewModel.save()
//
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//            editGroupBinding.visibility = View.GONE
//        }










