package ru.kpfu.itis.hw_android_2022.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.IBinder
import androidx.annotation.StringRes
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.models.CatServiceActionsEnum.*
import ru.kpfu.itis.hw_android_2022.util.NotificationHelper

class CatService : Service() {

    private var notificationHelper: NotificationHelper? = null
    private var imageBitmap: Bitmap? = null
    private val glide by lazy {
        Glide.with(baseContext)
    }
    private var imageUrl: String? = null

    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(baseContext)
    }

    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        imageUrl = intent?.getStringExtra(IMAGE_URL_KEY)
        when (intent?.action) {
            START_SERVICE.name -> startCatService()
            PRELOAD_IMAGE.name -> preloadImage()
            DISPLAY_IMAGE.name -> getImage()
            STOP_SERVICE.name -> stopCatService()
        }
        return START_NOT_STICKY
    }

    private fun startCatService() {
        val notification = notificationHelper?.getNotificationBuilder(
            service = this,
            title = packageName,
            content = getString(R.string.content)
        )?.build()
        startForeground(NotificationHelper.NOTIFICATION_ID, notification)
    }

    private fun preloadImage() {
        glide.asBitmap()
            .load(imageUrl)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //можно и здесь тосты показывать, но это не задача сервиса
                    //мы нашему фрагменту сообщаем, что загрузить удалось/не удалось
                    //пусть он там решает, что делать
                    startBroadcast(
                        createIntent(R.string.download_fail)
                    )
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //аналогично
                    startBroadcast(
                        createIntent(R.string.download_success)
                    )
                    return false
                }

            })
            .into(object : CustomTarget<Bitmap>() {
                //преобразуем ссылку в bitmap
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageBitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        //обновляем уведомление
        notificationHelper?.updateNotificationContent(getString(R.string.image_url, imageUrl))
    }

    //отправляем bitmap фрагменту, если она не пуста
    private fun getImage() {
        val imageIntent = if (imageBitmap != null) {
            createIntent(null).apply {
                putExtra(IMAGE_KEY, imageBitmap)
            }
        } else {
            createIntent(R.string.download_error)
        }
        startBroadcast(imageIntent)
    }

    private fun stopCatService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        startBroadcast(
            createIntent(R.string.cancel_service_success)
        )
        stopSelf()
    }

    //также есть стандартный метод sendBroadcast(intent), но с ним работать не будет
    private fun startBroadcast(intent: Intent) {
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)
    }

    private fun createIntent(@StringRes messageId: Int?) = Intent(IMAGE_INTENT_KEY).apply {
        putExtra(MESSAGE_KEY, messageId?.let { getString(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHelper = null
    }

    companion object {
        const val IMAGE_INTENT_KEY = "IMAGE_INTENT_KEY"
        const val IMAGE_KEY = "IMAGE_KEY"
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        const val MESSAGE_KEY = "MESSAGE_KEY"
    }

}