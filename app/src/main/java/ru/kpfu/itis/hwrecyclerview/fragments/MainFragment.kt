package ru.kpfu.itis.hwrecyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import ru.kpfu.itis.hwrecyclerview.CustomItemDecoration
import ru.kpfu.itis.hwrecyclerview.PlanetAdapter
import ru.kpfu.itis.hwrecyclerview.R
import ru.kpfu.itis.hwrecyclerview.convertDpToPx
import ru.kpfu.itis.hwrecyclerview.databinding.FragmentMainBinding
import ru.kpfu.itis.hwrecyclerview.repository.Planets


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding by lazy { _binding!! }

    private val containerID = R.id.container

    private lateinit var adapter: PlanetAdapter


    private lateinit var customItemDecoration: CustomItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentMainBinding.inflate(layoutInflater)
        initRecyclerView()

        // dividerItemDecoration = DividerItemDecoration(root.context, RecyclerView.VERTICAL)
        // dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, null))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initRecyclerView() {
        with(binding) {
            adapter = PlanetAdapter(
                listOfPlanets = Planets.listOfPlanets,
                glide = Glide.with(binding.root.context),
                onItemClick = ::onPlanetItemWasClicked
            )


            customItemDecoration = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let {
                CustomItemDecoration(
                    it,
                    (8).convertDpToPx(binding.root.context)
                )
            }!!
            rvPlanet.addItemDecoration(customItemDecoration)

            rvPlanet.adapter = adapter

            rvPlanet.adapter = ScaleInAnimationAdapter(adapter).apply {
                // Change the durations.
                setDuration(1000)
                // Change the interpolator.

                // Disable the first scroll mode.
                setFirstOnly(false)
            }
        }
    }


    private fun onPlanetItemWasClicked(itemPosition: Int) {

        Planets.listOfPlanets[itemPosition].colorID = R.color.key_lime

        adapter.notifyItemChanged(itemPosition)


        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_slide_in_top,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_slide_out_top
            )
            .replace(
                containerID,
                DescriptionFragment.newInstance(itemPosition = itemPosition),
                DescriptionFragment.DESCRIPTION_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}
