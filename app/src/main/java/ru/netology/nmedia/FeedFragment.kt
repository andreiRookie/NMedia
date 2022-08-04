package ru.netology.nmedia


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.activity.SeparatePostFragment.Companion.longArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
    //rivate val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(inflater, container,false)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
//
//                val intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type= "text/plain"
//                }
//
//                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                startActivity(shareIntent)

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

            override fun onPostContent(post: Post) {
                viewModel.navigateToSeparatePostFragment(post)
            }

        })

        binding.postsRecyclerView.adapter = adapter

        binding.addPostFab.setOnClickListener {
            viewModel.addPost()
        }


        viewModel.navigateToNewPostActivityEvent.observe(viewLifecycleOwner) {
            println("ToNewPostActivityEvent")
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }

        viewModel.navigateToEditPostActivityEvent.observe(viewLifecycleOwner) {
            println("from FeedFRag ToEditPostActivityEvent($it)")
            val fragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            fragment.navController.navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { textArg = it })

        }

        viewModel.playVideoEventViaYoutube.observe(viewLifecycleOwner) {
            println("playVideoEvent.observe: $it")
            val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))


//            val pm = this.packageManager
//            playVideoIntent.resolveActivity(pm)
//            println("${(pm.queryIntentActivities(playVideoIntent, MATCH_ALL))}")


            startActivity(playVideoIntent)
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            println("viewModel.data.observe")
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            println("viewModel.edited.observe: ${it.id == 0L}")

            if (it.id == 0L) return@observe

            viewModel.editPost(it.content)
        }

        viewModel.navigateToSeparatePostFragmentEvent.observe(viewLifecycleOwner) {
            println("ToSeparatePostActivityEvent($it)")
            val fragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            fragment.navController.navigate(
                R.id.action_feedFragment_to_separatePostFragment,
                Bundle().apply { longArg = it.id })
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




        viewModel.sharePostContentEvent.observe(viewLifecycleOwner) {
            println("viewModel.sharePostContent.observe")


            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type= "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
        return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        println("!!!SavedInstanceState: ${savedInstanceState.toString()}")
//
//
//
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        Data Storage via SharedPreferences
//        run {
//            val prefs = getPreferences(Context.MODE_PRIVATE)
//            prefs.edit().apply {
//                putString("key", "value")
//                commit()
//            }
//        }
//
//        run {
//            getPreferences(Context.MODE_PRIVATE)
//                .getString("key", "noValue")?.let {
//                    Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_INDEFINITE)
//                        .show()
//                }
//        }
//
//
//        val adapter = PostsAdapter(object : OnInteractionListener {
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
//        })
//
//        binding.postsRecyclerView.adapter = adapter
//
//
//        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { postContent ->
//            postContent ?: return@registerForActivityResult  //вызовется после
//            viewModel.changeContentAndSave(postContent)      //parseResult
//        }
//
//        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { postContent ->
//            println("editPostLauncher: $postContent")
//            postContent ?: return@registerForActivityResult  //вызовется после
//            viewModel.changeContentAndSave(postContent)      //parseResult
//        }
//
//
//
//        binding.addPostFab.setOnClickListener {
//            viewModel.addPost()
//        }
//
//        viewModel.navigateToNewPostActivityEvent.observe(this) {
//            println("newPostLauncher.launch()")
//            newPostLauncher.launch()
//        }
//
//        viewModel.navigateToEditPostActivityEvent.observe(this) {
//            println("editPostLauncher.launch($it)")
//            editPostLauncher.launch(it)
//        }
//
//        viewModel.playVideoEventViaYoutube.observe(this) {
//            println("playVideoEvent.observe: $it")
//            val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//
//
//            val pm = this.packageManager
//            playVideoIntent.resolveActivity(pm)
//            println("${(pm.queryIntentActivities(playVideoIntent, MATCH_ALL))}")
//
//
//                startActivity(playVideoIntent)
//        }
//
//        viewModel.data.observe(this) { posts ->
//            println("viewModel.data.observe")
//            adapter.submitList(posts)
//        }
//
//        viewModel.edited.observe(this) {
//            println("viewModel.edited.observe: ${it.id == 0L}")
//
//            if (it.id == 0L) return@observe
//
//            viewModel.editPost(it.content)
//        }
//
//        viewModel.sharePostContent.observe(this) {
//            println("viewModel.sharePostContent.observe")
//
//
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, it)
//                type= "text/plain"
//            }
//
//            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)
//        }
//
//    }
}











