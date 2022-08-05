package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FragmentSeparatePostBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.viewModel.PostViewModel

class SeparatePostFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val postId = arguments?.longArg ?: -1


        val binding = FragmentSeparatePostBinding.inflate(inflater, container, false)
//        val str = List(100) {it.toShort()}.joinToString("\n")
//        binding.postItemLayout.postText.text =str


        val viewHolder =
            PostsAdapter.PostViewHolder(binding.postItemLayout, object : OnInteractionListener {
                override fun onLike(post: Post) {
                    viewModel.like(post.id)
                }

                override fun onShare(post: Post) {

                    viewModel.share(post)
                }


                override fun onVideo(post: Post) {
                    viewModel.playVideo(post.video)
                }


                override fun onRemove(post: Post) {
                    viewModel.remove(post.id)
                    findNavController().navigateUp()
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                }

                override fun onBindingRoot(post: Post) {
                }

            })

        viewModel.sharePostContentEvent.observe(viewLifecycleOwner) {
            println("viewModel.sharePostContent.observe")


            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                putExtra(Intent.EXTRA_PACKAGE_NAME, "SeparatePostFragment")
                type= "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            println("SEPAR FRAG viewModel.edited.observe: ${it.id == 0L}")

            if (it.id == 0L) return@observe

            viewModel.editPost(it.content)
        }

        viewModel.navigateToEditPostActivityEvent.observe(viewLifecycleOwner) {
            println("from FeedFRag ToEditPostActivityEvent($it)")
//         Во фрагменте не нужно искать контроллер навигации
//         функция нужна для активити, а у фрагментов есть findNavController()
//            val fragment =
//                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//            fragment.navController.navigate(

            findNavController().navigate(
                R.id.action_separatePostFragment_to_newPostFragment,
                Bundle().apply { textArg = it })

        }


        viewModel.data.observe(viewLifecycleOwner) {
            val post = it.find { post -> post.id == postId }
            if (post != null) {
                viewHolder.bind(post)
            }
        }





        //   Если оставляем PostViewHolder inner классом в PostAdapter:
//        val viewHolder = PostsAdapter(object : OnInteractionListener{
//            override fun onLike(post: Post) {
//                viewModel.like(post.id)
//            }
//
//            override fun onShare(post: Post) {
//
//                val intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type= "text/plain"
//                }
//
//                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                startActivity(shareIntent)
//
//                viewModel.share(post)
//            }
//
//            override fun onVideo(post: Post) {
//                viewModel.playVideo(post.video)
//            }
//
//
//            override fun onRemove(post: Post) {
//                viewModel.remove(post.id)
//            }
//
//            override fun onEdit(post: Post) {
//                viewModel.edit(post)
//            }
//
//            override fun onPostContent(post: Post) {
//                viewModel.navigateToSeparatePostFragment(post)
//            }
//
//        }).PostViewHolder(binding.postItemLayout, object : OnInteractionListener {
//            override fun onLike(post: Post) {
//                viewModel.like(post.id)
//            }
//
//            override fun onShare(post: Post) {
//
//                val intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type= "text/plain"
//                }
//
//                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                startActivity(shareIntent)
//
//                viewModel.share(post)
//            }
//
//            override fun onVideo(post: Post) {
//                viewModel.playVideo(post.video)
//            }
//
//
//            override fun onRemove(post: Post) {
//                viewModel.remove(post.id)
//            }
//
//            override fun onEdit(post: Post) {
//                viewModel.edit(post)
//            }
//
//            override fun onPostContent(post: Post) {
//                viewModel.navigateToSeparatePostFragment(post)
//            }
//
//        })

      //  viewHolder.bind(post)


//        val text = arguments?.textArg
//        text?.let { binding.edit.setText(it) }
//        //либо так:
////        arguments?.textArg?.let(binding.edit::setText)
//
//        binding.edit.setCursorAtEndWithFocusAndShowKeyboard()
//        binding.floatingButtonOk.setOnClickListener {
//
//            if (!binding.edit.text.isNullOrBlank()) {
//                val content = binding.edit.text.toString()
//                viewModel.changeContentAndSave(content)
//            }
//            findNavController().navigateUp()
//        }
        return binding.root
    }

    //аналог static в Java
    companion object {
        var Bundle.longArg: Long by LongArg
    }

}