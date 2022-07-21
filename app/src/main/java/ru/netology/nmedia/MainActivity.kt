package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostService
import ru.netology.nmedia.databinding.PostItemBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)

            with(binding) {

                if (post.likedByMe) {
                    likeButton.setImageResource(getLikeIconResId(post.likedByMe))
                }

            }
        }
        binding.likeButton.setOnClickListener {
            viewModel.like()
        }

        binding.shareButton.setOnClickListener {
            viewModel.share()
        }

        // without scope function "with(T)"
//        if (post.likedByMe) {
//            binding.likeButton.setImageResource(R.drawable.ic_liked_24)
//        }
//        binding.likeButton.setOnClickListener {
//            post.likedByMe = !post.likedByMe
//            binding.likeButton.setImageResource(
//                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//            )
//        }


        // with scope function "with(T)"




        // without ViewBinding
//        setContentView(R.layout.post_item)
//
//        val likeButton = findViewById<TextView>(R.id.author)
//
//        likeButton.setOnClickListener(){
//            if (it !is ImageButton) {
//                return@setOnClickListener
//            }
//            it.setImageResource(R.drawable.ic_liked_24)


        //  (it as ImageButton).setImageResource(R.drawable.ic_liked_24)
        //likeButton.playSoundEffect(SoundEffectConstants.CLICK)


    }

    private fun PostItemBinding.render(post: Post) {
        author.text = post.author
        postText.text = post.content
        published.text = post.published
        likeCounter.text = PostService.countToString(post.likes)
        seenCounter.text = PostService.countToString(post.viewCount)
        shareCounter.text = PostService.countToString(post.repostCount)
        likeButton.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(isLiked: Boolean) =
        if (isLiked) R.drawable.ic_liked_24 else R.drawable.ic_like_24



}
