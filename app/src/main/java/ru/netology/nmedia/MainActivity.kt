package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.data.Post

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editGroupBinding = binding.editGroup



        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.remove(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

        })

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

        }

        binding.editCancel.setOnClickListener {
            with(binding.contentTextEdit) {
                viewModel.editTextCancel(this)
                editGroupBinding.visibility = View.GONE
            }
        }

        binding.saveContentButton.setOnClickListener {
            with(binding.contentTextEdit) {


                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // в один метод свести чендж и сейв надо ли??:
                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            editGroupBinding.visibility = View.GONE
        }

        viewModel.edited.observe(this) {
            println("viewModel.edited.observe")
            editGroupBinding.visibility = View.VISIBLE
            if(it.id == 0L) {
                editGroupBinding.visibility = View.GONE
                return@observe
            }
            with(binding.contentTextEdit){


                requestFocus()
                AndroidUtils.showKeyboard(this)
                setText(it.content)
            }
        }

    }

}
