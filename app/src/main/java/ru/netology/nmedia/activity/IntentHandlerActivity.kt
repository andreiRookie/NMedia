package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.*
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.AndroidUtils.setCursorAtEndWithFocusAndShowKeyboard
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.IntentHandlerActivityBinding
import ru.netology.nmedia.databinding.NewPostActivityBinding

class IntentHandlerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val binding = IntentHandlerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent ?: return

        binding.edit.setText(intent.getStringExtra(Intent.EXTRA_TEXT))

        intent.let {
            if (it.action != Intent.ACTION_SEND) {
                println("${it.action}")
                return@let
            }

            val text = intent.getStringExtra(Intent.EXTRA_TEXT)

            println("IntentHandlerActivity extratext: ${intent.getStringExtra(Intent.EXTRA_TEXT)}")
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
                binding.edit.setText(text)
                println("else.....${binding.edit.text}")
            }
        }
       // binding.edit.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
        binding.edit.setCursorAtEndWithFocusAndShowKeyboard()
        binding.floatingButtonOk.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                println("сcanceled ${binding.edit.text}")
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                println("OK ${binding.edit.text}")
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
        }



    }
}