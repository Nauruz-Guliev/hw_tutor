package ru.kpfu.itis.hw_player.util

import android.media.MediaPlayer
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import ru.kpfu.itis.hw_player.MusicService

class PlayerMediaSessionCallback(private val musicService: MusicService) : MediaSessionCompat.Callback() {
    override fun onPause() {
        musicService.pause()
        musicService.baseContext.showToast("paused")
    }
    override fun onSeekTo(pos: Long) {
        musicService.currentPlayer?.seekTo(pos.toInt())
    }
}