package ru.kpfu.itis.hw_player.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.hw_player.R
import ru.kpfu.itis.hw_player.databinding.MusicItemBinding
import ru.kpfu.itis.hw_player.PlayerSongModel

class SongViewHolder(val binding: MusicItemBinding, private val songActionCallback: SongActionCallback) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            btnPlay.setOnClickListener {
                songActionCallback.play(adapterPosition)
            }
            root.setOnClickListener {
                songActionCallback.onSongClicked(adapterPosition)
            }
        }
    }

    fun onBind(item: PlayerSongModel) {
        with(binding) {
            playerSong = item
            imageView.setImageResource(item.song.cover)
        }
    }

    fun updateField(bundle: Bundle?) {
        if(bundle?.containsKey(MusicListAdapter.DIFF_BUNDLE_KEY) == true) {
            bundle.getBoolean(MusicListAdapter.DIFF_BUNDLE_KEY).also {
                when(it) {
                    false -> binding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    true -> binding.btnPlay.setImageResource(R.drawable.ic_baseline_pause_24)
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, songActionCallback: SongActionCallback) =
            SongViewHolder(
                MusicItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                songActionCallback
            )
    }
}