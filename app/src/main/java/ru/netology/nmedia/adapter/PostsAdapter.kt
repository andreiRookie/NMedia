package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
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
    private val interactionListener: OnInteractionListener

): ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallBack()) {

    // внутри листАдаптера уже есть список
//    private var posts = emptyList<Post>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }



    inner class PostViewHolder(

        private val binding: PostItemBinding,
        private val listener: OnInteractionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            author.text = post.author
            postText.text = post.content
            published.text = post.published

            videoPreviewGroup.visibility = if (post.video.isNullOrBlank()) View.GONE else View.VISIBLE

//            videoPreviewGroup.setOnClickListener
            textVideoPreview.setOnClickListener {
                println("textVideoPreview.setOnClickListener")
                if (post.video.isNullOrBlank()) return@setOnClickListener else listener.onVideo(post)
            }

            playVideoButton.setOnClickListener {
                println("playVideoButton.setOnClickListener")
                if (post.video.isNullOrBlank()) return@setOnClickListener else listener.onVideo(post)
            }

            imageVideoPreview.setOnClickListener {
                println("imageVideoPreview.setOnClickListener")
                if (post.video.isNullOrBlank()) return@setOnClickListener else listener.onVideo(post)
            }

//            likeCounter.text = PostService.countToString(post.likes)
            likeButton.text = PostService.countToString(post.likes)
            shareButton.text = PostService.countToString(post.repostCount)

            seenCounter.text = PostService.countToString(post.viewCount)


          //  likeButton.setButtonDrawable(getLikeIconResId(post.likedByMe))
            likeButton.isChecked = post.likedByMe

            likeButton.setOnClickListener {
               listener.onLike(post)
            }

            shareButton.setOnClickListener {
                listener.onShare(post)
            }

            menuButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_button_popup)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

//        @DrawableRes
//        private fun getLikeIconResId(isLiked: Boolean) =
//            if (isLiked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.d("PostsAdapter", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)

        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        Log.d("PostsAdapter", "onBindViewHolder: $position")
        val post = getItem(position) //val post = posts[position], getItem - метод ListAdapter
        holder.bind(post)
    }

}