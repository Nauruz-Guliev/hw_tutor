package ru.kpfu.itis.hometasktwo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hometasktwo.Constants
import ru.kpfu.itis.hometasktwo.R
import ru.kpfu.itis.hometasktwo.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val counterValue = arguments?.get(Constants.COUNTER_KEY) ?: 0

        var colorValue = 0
        with(binding) {
            when (counterValue) {
                in 0..33 -> {
                    colorValue = resources.getIntArray(R.array.iv_colors_array).get(0)

                }
                in 34..67 ->
                    colorValue = resources.getIntArray(R.array.iv_colors_array).get(1)
                in 67..100 ->
                    colorValue = resources.getIntArray(R.array.iv_colors_array).get(2)
                in 100..Integer.MAX_VALUE ->
                    colorValue = resources.getIntArray(R.array.iv_colors_array).get(3)
            }

            //todo databinding
            tvCounterValue.setText("Counter value: $counterValue")

            mainScreen.setBackgroundColor(colorValue)

        }
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): SecondFragment {
            val secondFragment = SecondFragment()
            secondFragment.arguments = bundle
            return secondFragment
        }
    }
}
