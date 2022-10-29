package ru.kpfu.hw_rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.kpfu.hw_rv.databinding.AdvertisementLayoutBinding

class AdvertisementViewHolder(val binding: AdvertisementLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: MyModel.Advertisement) {
        with(binding) {
            ivAd.load(item.imageUrl)
            tvTitle.text = item.title
        }
    }

    companion object {
        fun create(parent: ViewGroup): AdvertisementViewHolder = AdvertisementViewHolder(
            AdvertisementLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}
