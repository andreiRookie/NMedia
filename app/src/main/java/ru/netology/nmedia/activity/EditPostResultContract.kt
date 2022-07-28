package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.data.Post

class EditPostResultContract : ActivityResultContract<String?, String?>() {


    //создает явный интент
    override fun createIntent(context: Context, input: String?): Intent =
        Intent(context, IntentHandlerActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, input)
        }



    // resultCode: result_canceled = 0; result_ok = -1
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(
                Intent
                .EXTRA_TEXT)
        } else {
            null
        }






}