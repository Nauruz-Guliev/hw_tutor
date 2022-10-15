package ru.kpfu.itis.hwrecyclerview


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.kpfu.itis.hwrecyclerview.databinding.PlanetItemBinding
import ru.kpfu.itis.hwrecyclerview.repository.Planet


class PlanetAdapter(
    val listOfPlanets: List<Planet>,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder =
        PlanetViewHolder(
            PlanetItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClick = onItemClick,
            glide = glide
        )


    override fun getItemCount(): Int = listOfPlanets.size

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.bind(listOfPlanets[position])
    }


















    inner class PlanetViewHolder(
        private val binding: PlanetItemBinding,
        val onItemClick: (Int) -> Unit,
        private val glide: RequestManager,
    ) : RecyclerView.ViewHolder(binding.layoutItem) {

        init {
            with(binding) {
                root.setOnClickListener {

                    onItemClick(adapterPosition)
                 //   notifyItemChanged(adapterPosition)
                }
            }
        }

        fun bind(planet: Planet) {

            with(binding) {
                tvPlanetName.text = planet.name
                this.planet = planet

                if (planet.colorID != 0) {
                    layoutItem.setBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            planet.colorID
                        )
                    )
                }


                glide
                    .load(planet.imageUrl)
                    .listener(object:RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {

                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressCircular.visibility = ViewGroup.INVISIBLE
                            return false
                        }

                    } )

                    .into(ivPlanet)
            }
        }
    }
}
