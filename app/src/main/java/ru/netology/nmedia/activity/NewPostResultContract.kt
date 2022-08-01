package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class NewPostResultContract : ActivityResultContract<Unit, String?>() {


    //создает явный интент
    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(context, IntentHandlerActivity::class.java)



    // resultCode: result_canceled = 0; result_ok = -1
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent
                .EXTRA_TEXT)
        } else {
            null
        }






}