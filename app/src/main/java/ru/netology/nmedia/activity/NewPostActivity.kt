package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.AndroidUtils.setCursorAtEndWithFocusAndShowKeyboard
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.NewPostActivityBinding

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = NewPostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent ?: return
        intent.let {
            if (it.action != Intent.ACTION_SEND) {
                println("${it.action}")
                return@let
            }

            val text = intent.getStringExtra(Intent.EXTRA_TEXT)

            println("NewPostActivity extratext: ${intent.getStringExtra(Intent.EXTRA_TEXT)}")
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

        binding.edit.setCursorAtEndWithFocusAndShowKeyboard()
        binding.floatingButtonOk.setOnClickListener {

            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

    }

}