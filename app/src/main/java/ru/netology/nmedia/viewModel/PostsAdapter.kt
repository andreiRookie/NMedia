package ru.netology.nmedia.viewModel

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostDiffCallBack
import ru.netology.nmedia.data.PostService
import ru.netology.nmedia.databinding.PostItemBinding

//typealias  OnLikeListener = (Post) -> Unit
//typealias  OnShareListener = (Post) -> Unit

internal class PostsAdapter(
    private val onLikeListener: (post: Post) -> Unit,
    private val onShareListener: (post: Post) -> Unit

): ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallBack()) {

//    private var posts = emptyList<Post>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }


    inner class PostViewHolder(

        private val binding: PostItemBinding,
        private val onLikeListener: (post: Post) -> Unit,
        private val onShareListener: (post: Post) -> Unit

    ) : RecyclerView.ViewHolder(binding.root) {

//        private lateinit var post: Post
//
//        init {
//            binding.likeButton.setOnClickListener {
//                likeButton.setImageResource(getLikeIconResId(post.likedByMe))
//
//                onLikeListener(post)
//            }
//        }

        fun bind(post: Post) = with(binding) {
            author.text = post.author
            postText.text = post.content
            published.text = post.published

            likeCounter.text = PostService.countToString(post.likes)
            seenCounter.text = PostService.countToString(post.viewCount)
            shareCounter.text = PostService.countToString(post.repostCount)

            likeButton.setImageResource(getLikeIconResId(post.likedByMe))

            likeButton.setOnClickListener {
               onLikeListener(post)
            }

            shareButton.setOnClickListener {
                onShareListener(post)
            }
        }

        @DrawableRes
        private fun getLikeIconResId(isLiked: Boolean) =
            if (isLiked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

    }

    //создает вью(через байндинг), кладет ее в во вьюхолдер и возвращает вьюхолдер адаптеру
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.d("PostsAdapter", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)

        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    // подставляет следующую вью во вьюхолдер
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        Log.d("PostsAdapter", "onBindViewHolder: $position")
        val post = getItem(position) //val post = posts[position], getItem - метод ListAdaptera
        holder.bind(post)
    }

//    override fun getItemCount() = posts.size

}