package ru.kpfu.itis.hw_player.recyclerView

interface SongActionCallback {
    fun play(position: Int)
    fun onSongClicked(position: Int)
}