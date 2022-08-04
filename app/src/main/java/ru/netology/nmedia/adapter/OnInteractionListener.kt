package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

//CALLBACK INTERFACE

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
    fun onVideo(post: Post) {}
    fun onPostContent(post: Post) {}
}