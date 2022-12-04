package ru.kpfu.itis.hw_player.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import ru.kpfu.itis.hw_player.R
import ru.kpfu.itis.hw_player.PlayerSongModel
import ru.kpfu.itis.hw_player.SongModel
import java.io.File

object Repository {
    lateinit var songs: List<SongModel>
        private set
    var isInitialized = false
        private set

    private lateinit var playerSongModels: MutableList<PlayerSongModel>
        private set

    fun initRepository(context: Context) {
        songs = listOf(
            SongModel(
                1,
                "D.R.E",
                "Gangsta rap",
                "Snoop Dog",
                R.drawable.ic_baseline_cruelty_free_24,
                parseToMusicUri(context, R.raw.snoop_dog_still_dre)
            ),
            SongModel(
                2,
                "AGORA",
                "Dance",
                "Mike Mago",
                R.drawable.ic_baseline_cruelty_free_24,
                parseToMusicUri(context, R.raw.mike_mango_agora)
            ),
            SongModel(
                3,
                "Can I exist",
                "Deep house",
                "Ash",
                R.drawable.ic_baseline_cruelty_free_24,
                parseToMusicUri(context, R.raw.ash_can_i_exist)
            )
        )
        playerSongModels = songs.map {
            PlayerSongModel(it, false)
        }.toMutableList()
        isInitialized = true
    }

    fun getSong(position: Int) = songs[position]

    fun setPlaying(position: Int) {
        val model = playerSongModels[position].copy()
        model.isPLaying = true
        playerSongModels[position] = model
    }

    fun setPaused(position: Int) {
        val model = playerSongModels[position].copy()
        model.isPLaying = false
        playerSongModels[position] = model
    }

    fun getPlayerSongModelList() = playerSongModels.toMutableList()

}

fun Repository.parseToMusicUri(context: Context, @RawRes songId: Int): Uri = Uri.parse(
    ContentResolver.SCHEME_ANDROID_RESOURCE +
            File.pathSeparator +
            File.separator +
            File.separator +
            context.packageName +
            File.separator +
            songId
)