package ru.kpfu.itis

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import ru.kpfu.itis.databinding.FirstTypeViewBinding
import ru.kpfu.itis.databinding.SecondTypeViewBinding
import ru.kpfu.itis.databinding.ThirdTypeViewBinding

class MyAdapter(
    val onThirdItemClick: ((Int) -> Unit)?,
    val onFirstItemClick: ((Int) -> Unit)?
) : RecyclerView.Adapter<ViewHolder>() {
    //  private var differ.currentList: List<DataModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            RecyclerViewTypes.FIRST_TYPE.typeValue -> {
                FirstTypeViewHolder(
                    binding = FirstTypeViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            RecyclerViewTypes.SECOND_TYPE.typeValue -> {
                SecondTypeViewHolder(
                    binding = SecondTypeViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                ThirdTypeViewHolder(
                    binding = ThirdTypeViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is FirstTypeViewHolder -> {
                holder.bind(differ.currentList[position] as DataModel.FirstType)
            }
            is SecondTypeViewHolder -> {
                holder.bind(differ.currentList[position] as DataModel.SecondType)
            }
            is ThirdTypeViewHolder -> {
                holder.bind(differ.currentList[position] as DataModel.ThirdType, position)
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach {
                when(it){
                    is Bundle -> {
                        if(holder is ThirdTypeViewHolder) {
                            holder.binding.tvHeader.visibility = ViewGroup.GONE
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position]) {
            is DataModel.FirstType -> RecyclerViewTypes.FIRST_TYPE.typeValue
            is DataModel.SecondType -> RecyclerViewTypes.SECOND_TYPE.typeValue
            is DataModel.ThirdType -> RecyclerViewTypes.THIRD_TYPE.typeValue
        }

    override fun getItemCount(): Int = differ.currentList.size


    inner class FirstTypeViewHolder(private val binding: FirstTypeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(firstItem: DataModel.FirstType) {
            with(binding) {
                root.setOnClickListener {
                    onFirstItemClick?.invoke(adapterPosition)
                }
                tvPrimary.text = firstItem.primaryName
                tvSecondary.text = firstItem.secondaryName
                ivFirstType.load(firstItem.url)
            }
        }
    }

    inner class SecondTypeViewHolder(private val binding: SecondTypeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(secondItem: DataModel.SecondType) {
            with(binding) {
                tvPrimary.text = secondItem.name
                ivSecondType.setImageDrawable(
                    root.context.resources.getDrawable(
                        secondItem.drawableID,
                        null
                    )
                )
            }
        }
    }

    inner class ThirdTypeViewHolder(val binding: ThirdTypeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onThirdItemClick?.invoke(position)
            }
        }

        fun bind(thirdItem: DataModel.ThirdType, position: Int) {
            with(binding) {
                tvPrimary.text = thirdItem.primaryName
                tvSecondary.text = thirdItem.secondaryName
                ivThirdType.load(thirdItem.url)
                if (position == Repository.secondCursor) {
                    thirdItem.isFirstItem = true
                    tvHeader.visibility = ViewGroup.VISIBLE
                    (differ.currentList[position + 1] as? DataModel.ThirdType)?.isFirstItem = false
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataModel>() {
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            val isSameFirst = oldItem is DataModel.FirstType && newItem is DataModel.FirstType
            val isSameSecond = oldItem is DataModel.SecondType && newItem is DataModel.SecondType
            val isSameThird = oldItem is DataModel.ThirdType && newItem is DataModel.ThirdType
            Log.d("value1", newItem.id.toString())
            Log.d("value2", oldItem.id.toString())
            return isSameFirst || isSameSecond || isSameThird
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: DataModel, newItem: DataModel): Any? {
            val diffBundle = Bundle()
            diffBundle.putInt(Companion.BUNDLE_ID, newItem.id)
            return diffBundle
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    /*
        fun setData(newdiffer.currentList: List<DataModel>) {
            val diffUtil = MyDiffUtil(differ.currentList, newdiffer.currentList)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            differ.currentList = newdiffer.currentList
            diffResults.dispatchUpdatesTo(this)
        }

     */
    companion object {
        private const val BUNDLE_ID = "bundle_id"
    }

}