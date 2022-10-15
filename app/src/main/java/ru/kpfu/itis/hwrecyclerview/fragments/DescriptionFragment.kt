package ru.kpfu.itis.hwrecyclerview.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.kpfu.itis.hwrecyclerview.R
import ru.kpfu.itis.hwrecyclerview.databinding.FragmentDescriptionBinding
import ru.kpfu.itis.hwrecyclerview.databinding.FragmentMainBinding
import ru.kpfu.itis.hwrecyclerview.repository.Planets


class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding by lazy { _binding!!}

    private var planetID: Int  = -100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        planetID = arguments?.getInt(PLANET_ID_TAG)?:-100

        _binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        val planet = Planets.listOfPlanets[planetID]

        with(binding) {
            tvDescription.setText(planet.description)

            Glide.with(root)
                .load(planet.imageUrl)
                .error(androidx.vectordrawable.R.drawable.notify_panel_notification_icon_bg)
                .into(ivPlanet)
        }
    }

    companion object {
        const val DESCRIPTION_FRAGMENT_TAG = "DESCRIPTION_FRAGMENT_TAG"
        const val PLANET_ID_TAG = "PLANET_ID_TAG"
        fun newInstance(itemPosition : Int) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(PLANET_ID_TAG, itemPosition)
                }
            }
    }
}
