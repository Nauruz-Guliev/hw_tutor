package ru.kpfu.itis.hw_player

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import ru.kpfu.itis.hw_player.MusicActionsEnum.*
import ru.kpfu.itis.hw_player.repository.Repository
import ru.kpfu.itis.hw_player.util.PlayerMediaSessionCallback
import ru.kpfu.itis.hw_player.util.showToast


class MusicService : Service(), MusicPlayerInterface {


    private lateinit var playlist: List<SongModel>

    private lateinit var notificationHelper: NotificationHelper
    var currentPosition = 0
    private var prevPosition = -1
    var currentPlayer: MediaPlayer? = null

    private var isFirstStart = true

    private lateinit var sessionCallback: PlayerMediaSessionCallback


    override fun onCreate() {
        super.onCreate()
        if (!Repository.isInitialized) Repository.initRepository(baseContext)
        notificationHelper = NotificationHelper(baseContext)
        playlist = Repository.songs
        sessionCallback =  PlayerMediaSessionCallback(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isFirstStart) {
            val notification = notificationHelper.getNotificationBuilder(
                service = this,
                playPauseDrawableRes = PLAY_ICON
            ).build()
            startForeground(
                NotificationHelper.NOTIFICATION_ID, notification
            )
            isFirstStart = false
        }
        showToast(intent?.action)
        when (intent?.action) {
            PLAY.name -> this@MusicService.play(currentPosition)
            PREV.name -> this@MusicService.prev()
            NEXT.name -> this@MusicService.next()
            STOP.name -> this@MusicService.stop()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = aidlBinder

    private val aidlBinder = object : MusicAIDL.Stub() {
        override fun isPlaying(): Boolean = currentPlayer?.isPlaying ?: true

        override fun play(position: Int, callback: AsyncCallback?) {
            currentPosition = position
            callback?.onSuccess(prevPosition)
            this@MusicService.play(position)
        }

        override fun stop() = this@MusicService.stop()

        override fun currentSongPosition() = prevPosition

    }

    override fun play(position: Int?) {
        Log.d("SYSTEM_MESSAGE", "CALLED  pos:$position prev:$prevPosition curr:$currentPosition")
        val isMusicPlaying = currentPlayer?.isPlaying?: false
        when {
            isMusicPlaying && position == prevPosition -> {
                pause()
            }
            isMusicPlaying && position != prevPosition -> {
                stop()
                playSong()
            }
            !isMusicPlaying && position != prevPosition -> {
                playSong()
            }
            else -> {
                currentPlayer?.apply {
                    currentPlayer?.currentPosition?.let { seekTo(it) }
                    start()
                }
                notificationHelper.updateNotification(sessionCallback, PLAY , currentPlayer?.currentPosition?.toLong()?:0L, currentPlayer?.duration?.toLong() ?: 0L, getCurrentSong(), PAUSE_ICON)
            }
        }
    }

    private fun playSong() {
        currentPlayer = createPlayer(getCurrentSong()).apply {
            prepare()
            start()
        }
        prevPosition = currentPosition
        notificationHelper.updateNotification(sessionCallback, PLAY, currentPlayer?.currentPosition?.toLong()?:0L, currentPlayer?.duration?.toLong() ?: 0L, getCurrentSong(), PAUSE_ICON)
    }


    override fun next() {
        prevPosition = currentPosition
        if(currentPosition == -1) return
        Log.d("SYSTEM_MESSAGE", "NEXT CALLED $currentPosition")
        currentPosition = if (currentPosition < playlist.size - 1) {
            currentPosition + 1
        } else {
            0
        }

        play(currentPosition)
    }

    override fun prev() {
        prevPosition = currentPosition
        if(currentPosition == -1) return
        currentPosition = if (currentPosition > 0) {
            currentPosition - 1
        } else {
            Repository.songs.size - 1
        }
        play(currentPosition)
    }

    override fun pause() {
        currentPlayer?.pause()
        notificationHelper.updateNotification(sessionCallback, PAUSE, currentPlayer?.currentPosition?.toLong()?:0L, currentPlayer?.duration?.toLong() ?: 0L, getCurrentSong(), PLAY_ICON)
    }


    override fun stop() {
        currentPlayer?.stop()
        currentPlayer?.release()
    }

    private fun getCurrentSong() = Repository.getSong(currentPosition)

    override fun isPLaying(): Boolean = currentPlayer?.isPlaying ?:true

    private fun createPlayer(songModel: SongModel?) =
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            songModel?.uri?.let { setDataSource(baseContext, it) }
        }

    companion object {
        private const val PAUSE_ICON = R.drawable.ic_baseline_pause_24
        private const val PLAY_ICON = R.drawable.ic_baseline_play_arrow_24
        val PENDING_INTENT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }
}