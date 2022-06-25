package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.PostItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            postId = 1,
            author = "Нетология пост блаблаблаьлаблаблаблаблабла",
            content = "Обратите внимание на блок с количеством просмотров. С ним есть небольшая проблема (поскольку он расположен справа). Если количество просмотров вырастет, например, до 500, то есть целых два варианта:\n" +
                    "\n" +
                    "Отталкиваться от текста (т.е. установить фиксированное расстояние от текста до границы родителя, а саму иконку приклеить к границе текста)\n" +
                    "Оставить достаточное количество места, чтобы уместилось и 500 и 1К, тогда на всех карточках положение этого блока будет одинаковым\n" +
                    "Вам нужно провести небольшое исследование и посмотреть, каким образом это реализовано в Vk.",
            published = "18 june 2022"
        )

        binding.render(post)

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
        with(binding) {
            if (post.likedByMe) {
                likeButton.setImageResource(getLikeIconResId(post.likedByMe))
            }



            likeButton.setOnClickListener {
                if (post.likedByMe) post.likes -= 1 else post.likes += 1
                likeCounter.text = PostService.countToString(post.likes)
                post.likedByMe = !post.likedByMe
                likeButton.setImageResource(
                    getLikeIconResId(post.likedByMe)
                )
            }

            shareButton.setOnClickListener {
                post.repostCount += 1
                shareCounter.text = PostService.countToString(post.repostCount)
            }
        }



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
