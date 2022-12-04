package ru.kpfu.itis.hw_player

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.session.PlaybackState.STATE_PLAYING
import android.os.Build
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import ru.kpfu.itis.hw_player.util.PlayerMediaSessionCallback

class NotificationHelper(private val context: Context) {

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var notificationManager: NotificationManager? = null
    private var mediaSession: MediaSessionCompat? = null

    private var prevPendingIntent: PendingIntent? = null
    private var nextPendingIntent: PendingIntent? = null
    private var playPendingIntent: PendingIntent? = null
    private var stopPendingIntent: PendingIntent? = null


    init {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mediaSession = MediaSessionCompat(context, MEDIA_SESSION_TAG)
        prevPendingIntent = createPendingIntent(MusicActionsEnum.PREV)
        nextPendingIntent = createPendingIntent(MusicActionsEnum.NEXT)
        playPendingIntent = createPendingIntent(MusicActionsEnum.PLAY)
        stopPendingIntent = createPendingIntent(MusicActionsEnum.STOP)

    }

    fun getNotificationBuilder(
        service: MusicService,
        @DrawableRes playPauseDrawableRes: Int
    ): NotificationCompat.Builder {
        createChannel()
        val notificationPendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_REQUEST_CODE, Intent(context, MainActivity::class.java), PENDING_INTENT_FLAG
        )
        val stateBuilder = PlaybackStateCompat.Builder()
            .setState(
                PlaybackStateCompat.STATE_PLAYING,
                15, 1f
            )

        mediaSession?.setPlaybackState(stateBuilder.build())

        notificationBuilder = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_baseline_music_video_24)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession?.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setContentIntent(notificationPendingIntent)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "PREV", prevPendingIntent)
            .addAction(playPauseDrawableRes, "PLAY", playPendingIntent)
            .addAction(R.drawable.ic_baseline_skip_next_24, "NEXT", nextPendingIntent)
            .addAction(R.drawable.ic_baseline_stop_24, "STOP", stopPendingIntent)
        return notificationBuilder
    }

    fun updateNotification(sessionCallback: PlayerMediaSessionCallback, action: MusicActionsEnum, currentPos: Long, songDuration: Long, songModel: SongModel?, @DrawableRes playPauseDrawableRes: Int) {
        mediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                getPlaybackState(action),
                currentPos,
                1f,
                SystemClock.elapsedRealtime()
            ).build()
        )
        mediaSession?.setCallback(sessionCallback)
        mediaSession?.setMetadata(
            MediaMetadataCompat.Builder()
            .putString(
                MediaMetadataCompat.METADATA_KEY_ARTIST,
                songModel?.singer
            )
            .putString(
                MediaMetadataCompat.METADATA_KEY_ALBUM,
                songModel?.genre
            )
            .putString(
                MediaMetadataCompat.METADATA_KEY_TITLE,
                songModel?.name
            )
            .putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                songDuration
            )
            .build()
        )

        updateMediaPlaybackState(currentPos)
        notificationBuilder.clearActions()
        notificationBuilder
            .addAction(R.drawable.ic_baseline_skip_previous_24, "PREV", prevPendingIntent)
            .addAction(playPauseDrawableRes, "PLAY", playPendingIntent)
            .addAction(R.drawable.ic_baseline_skip_next_24, "NEXT", nextPendingIntent)
            .addAction(R.drawable.ic_baseline_stop_24, "STOP", stopPendingIntent)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun updateMediaPlaybackState(currentPos: Long) {
        mediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                            or PlaybackStateCompat.ACTION_PAUSE
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                            or PlaybackStateCompat.ACTION_SEEK_TO
                )
                .setState(
                    PlaybackStateCompat.STATE_PLAYING,
                    currentPos,
                    1f,
                    SystemClock.elapsedRealtime()
                )
                .build()
        )
    }

    @PlaybackStateCompat.State
    private fun getPlaybackState(action: MusicActionsEnum): Int {
        return when (action) {
            MusicActionsEnum.PLAY -> PlaybackStateCompat.STATE_PLAYING
            MusicActionsEnum.PAUSE -> PlaybackStateCompat.STATE_PAUSED
            else -> PlaybackStateCompat.STATE_NONE
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun createPendingIntent(action: MusicActionsEnum) = PendingIntent.getService(
        context, NOTIFICATION_REQUEST_CODE,
        Intent(context, MusicService::class.java).setAction(
            action.name
        ), MusicService.PENDING_INTENT_FLAG
    )


    companion object {
        const val NOTIFICATION_REQUEST_CODE = 123
        const val NOTIFICATION_ID = 321
        const val MEDIA_SESSION_TAG = "MEDIA_SESSION_TAG"
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        val PENDING_INTENT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_CANCEL_CURRENT
        }
    }
}