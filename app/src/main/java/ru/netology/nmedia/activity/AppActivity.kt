package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)

        checkGoogleApiAvailability()

//        val intent = Intent()
//        //binding.edit.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
//
//        intent.let {
//            if (it.action != Intent.ACTION_SEND) {
//                return@let
//            }
//
//            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
//
//            if (text.isNullOrBlank()) {
//                Snackbar.make(
//                    binding.root,
//                    R.string.error_empty_content,
//                    Snackbar.LENGTH_INDEFINITE
//                )
//                    .setAction(android.R.string.ok) {
//                        finish()
//                    }.show()
//                return@let
//            } else {
//
//                val fragment =
//                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//               fragment.navController.navigate(
//                   R.id.action_feedFragment_to_newPostFragment,
//                   Bundle().apply { textArg = text })
//
//           /// binding.edit.setText(text)
//
//            }
//        }
    }

    private fun checkGoogleApiAvailability() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@AppActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }
            if (isUserResolvableError(code)) {
                getErrorDialog(this@AppActivity, code, 9000)?.show()
                return
            }
            Toast.makeText(this@AppActivity, "Google API unavailable", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            println("handleIntent: $intent")

            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            val packName = intent.getStringExtra(Intent.EXTRA_PACKAGE_NAME)

            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    R.string.error_empty_content,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            } else {

                val fragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

                fragment.navController.navigate(
                    (if (packName == "SeparatePostFragment") R.id.action_separatePostFragment_to_newPostFragment
                    else R.id.action_feedFragment_to_newPostFragment),
                    Bundle().apply { textArg = text })


//                Можно было еще вернуться на фрагмент назад (на FeedFragment)
//                и после вызывать переход.
//                Тогда Share с фрагмента поста также будет работать.

//                fragment.navController.navigateUp()
//                fragment.navController.navigate(
//                    (if (packName == "SeparatePostFragment") R.id.action_separatePostFragment_to_newPostFragment
//                    else R.id.action_feedFragment_to_newPostFragment),
//                    Bundle().apply { textArg = text })

            }
        }
    }
}