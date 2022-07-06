package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.viewModel.PostsAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(
            onLikeListener = {post -> viewModel.like(post.id)},
            onShareListener = {post -> viewModel.share(post.id)}
        )
        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

//            adapter.list = posts
//            binding.postsRecyclerView.adapter = PostsAdapter(viewModel.like(post.id))
//            binding.render(posts)

//            with(binding) {
//
//                if (post.likedByMe) {
//                    likeButton.setImageResource(getLikeIconResId(post.likedByMe))
//                }
//
//            }
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

//    private fun ActivityMainBinding.render(posts: List<Post>) {
//        for (post in posts) {
//            PostItemBinding.inflate(
//                layoutInflater, root, true
//            ).render(post)
//        }

//    }

//    private fun PostItemBinding.render(post: Post) {
//        author.text = post.author
//        postText.text = post.content
//        published.text = post.published
//        likeCounter.text = PostService.countToString(post.likes)
//        seenCounter.text = PostService.countToString(post.viewCount)
//        shareCounter.text = PostService.countToString(post.repostCount)
//        likeButton.setImageResource(getLikeIconResId(post.likedByMe))
//
//        likeButton.setOnClickListener {
//            likeButton.setImageResource(getLikeIconResId(post.likedByMe))
//            viewModel.like(post.id)
//        }
//
//        shareButton.setOnClickListener {
//            viewModel.share(post.id)
//        }
//    }

//    @DrawableRes
//    private fun getLikeIconResId(isLiked: Boolean) =
//        if (isLiked) R.drawable.ic_liked_24 else R.drawable.ic_like_24



}
