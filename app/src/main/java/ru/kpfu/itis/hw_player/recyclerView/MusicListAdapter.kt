package ru.kpfu.itis.hw_player.recyclerView

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.kpfu.itis.hw_player.PlayerSongModel

class MusicListAdapter(private val songActionCallback: SongActionCallback) :
    ListAdapter<PlayerSongModel, SongViewHolder>(SongDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SongViewHolder.create(parent, songActionCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) =
        holder.onBind(currentList[position])

    override fun onBindViewHolder(
        holder: SongViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }.let {
                holder.updateField(it as Bundle)
            }
        }
    }




    object SongDiffer : DiffUtil.ItemCallback<PlayerSongModel>() {
        override fun areItemsTheSame(oldItem: PlayerSongModel, newItem: PlayerSongModel) =
            oldItem.song.id == newItem.song.id

        override fun areContentsTheSame(oldItem: PlayerSongModel, newItem: PlayerSongModel) =
            oldItem == newItem

        override fun getChangePayload(oldItem: PlayerSongModel, newItem: PlayerSongModel): Any? {
            return if (oldItem.isPLaying == newItem.isPLaying) {
                super.getChangePayload(oldItem, newItem)
            } else {
                Bundle().apply {
                    putBoolean(DIFF_BUNDLE_KEY, newItem.isPLaying)
                }
            }
        }
    }

    companion object {
        const val DIFF_BUNDLE_KEY = "DIFF_BUNDLE_KEY"
    }
}
