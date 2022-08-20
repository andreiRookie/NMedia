package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {

    private val action = "action"
    private val content = "content"
    private val gson = Gson()
    private val channelId = "nmedia2022"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService((Context.NOTIFICATION_SERVICE)) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("\nonMessageReceived", gson.toJson(message))
        println(gson.toJson(message))
        try {
            message.data[action]?.let {
                when (Action.valueOf(it)) {
                    Action.LIKE -> handleLike(
                        gson.fromJson(
                            message.data[content],
                            Like::class.java
                        )
                    )
                    Action.NEW_POST -> handleNewPost(
                        gson.fromJson(
                            message.data[content],
                            NewPost::class.java

                        )
                    )
                }
            }
        } catch (e: IllegalArgumentException) {
            println("Such action is absent")
        }
//        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        println(token)
//        super.onNewToken(token)
    }

    private fun handleLike(content: Like) {
        val sb =SpannableString(getString(R.string.notification_user_like,
            content.userName,
            content.postAuthor
        ))
        sb.setSpan(StyleSpan(Typeface.BOLD), 0, content.userName.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(sb
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleNewPost(content: NewPost) {
        val sb = SpannableString(getString(R.string.notification_new_post, content.postAuthor))
        sb.setSpan(StyleSpan(Typeface.BOLD), 0, content.postAuthor.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(sb
            )
            .setContentText(content.postContent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(sb))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }


}


