package ru.kpfu.itis.hometaskone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.kpfu.itis.hometaskone.R
import ru.kpfu.itis.hometaskone.databinding.FragmentFirstBinding
import ru.kpfu.itis.hometaskone.util.Constants


class FirstFragment : Fragment() {

    private var counter: Int = 0
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)

        //todo
        activity?.title = getString(R.string.first_fragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initClickListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initClickListeners() {
        with(binding) {
            btnNavigateToThirdFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    Constants.containerID,
                    ThirdFragment.getInstance(arguments),
                    ThirdFragment.THIRD_FRAGMENT_TAG
                ).commit()
            }

            btnGenerateRandomNumber.setOnClickListener {
                saveValueToBundle((1000..9999).random())
                Toast.makeText(context, "random number has been generadted", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveValueToBundle(randomNumber : Int){
        arguments?.apply {
            putInt(Constants.RANDOM_NUMBER_KEY, randomNumber)
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
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putAll(arguments)

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            arguments = savedInstanceState
        }
        Log.d("value", arguments?.getInt(Constants.RANDOM_NUMBER_KEY).toString())
    }
}
