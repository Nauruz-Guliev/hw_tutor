package ru.kpfu.itis.hw_player

interface MusicPlayerInterface {
    fun play(position: Int?)
    fun next()
    fun prev()
    fun pause()
    fun stop()
    fun isPLaying(): Boolean
}