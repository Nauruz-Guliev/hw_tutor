package ru.kpfu.itis.hwrecyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.kpfu.itis.hwrecyclerview.PlanetAdapter
import ru.kpfu.itis.hwrecyclerview.R
import ru.kpfu.itis.hwrecyclerview.databinding.FragmentMainBinding
import ru.kpfu.itis.hwrecyclerview.repository.Planets


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding by lazy { _binding!! }

    private val containerID = R.id.container

    private lateinit var adapter: PlanetAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding.rvPlanet.adapter == null) {
            adapter = PlanetAdapter(
                listOfPlanets = Planets.listOfPlanets,
                glide = Glide.with(this),
                onItemClick = ::onPlanetItemWasClicked
            )
        }
        binding.rvPlanet.adapter = adapter
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

    private fun onPlanetItemWasClicked(planetID: Int) {
        Planets.listOfPlanets[planetID].colorID = R.color.light_pink
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_slide_in_top,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_slide_out_top
            )
            .replace(
                containerID,
                DescriptionFragment.newInstance(planetID = planetID),
                DescriptionFragment.DESCRIPTION_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }
}
