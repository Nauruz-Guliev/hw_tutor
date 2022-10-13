package ru.kpfu.itis.hometasktwo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hometasktwo.Constants
import ru.kpfu.itis.hometasktwo.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreCounterValue()
        binding.counter = counter
        initClickListeners()
    }

    private fun initClickListeners() {

        with(binding) {

            btnOpenDialogFragment.setOnClickListener {
                val dialog = CustomDialogFragment(counterValue = counter,
                    { counterFromDialogFragment ->
                        counter = counterFromDialogFragment
                        binding.counter = counter
                        saveValueToBundle(counterFromDialogFragment)
                    }

                )
                dialog.show(parentFragmentManager, "Custom dialog")
            }

            btnIncrementCounterValue.setOnClickListener {
                counter++
                //databinding

                binding.counter = counter

                saveValueToBundle(counter)
            }
            btnNavigateToSecondFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    Constants.containerID,
                    SecondFragment.getInstance(arguments),
                    SecondFragment.SECOND_FRAGMENT_TAG
                ).setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun saveValueToBundle(counterToSave: Int) {
        arguments?.apply {
            putInt(Constants.COUNTER_KEY, counterToSave)
        }
    }

    private fun restoreCounterValue() {
        if (arguments != null) {
            counter = arguments?.getInt(Constants.COUNTER_KEY) ?: 0
        }
    }

    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): FirstFragment {
            val firstFragment = FirstFragment()
            firstFragment.arguments = bundle
            return firstFragment
        }
    }

}
