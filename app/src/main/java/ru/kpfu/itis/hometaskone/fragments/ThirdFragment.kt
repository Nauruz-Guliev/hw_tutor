package ru.kpfu.itis.hometaskone.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hometaskone.R
import ru.kpfu.itis.hometaskone.databinding.FragmentThirdBinding
import ru.kpfu.itis.hometaskone.util.Constants



class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    private val binding get() = _binding!!

    private var colorIndex = 0

    private var isNavigatingBack = false
    private var isConfigurationChange = false

    //TODO refactor. This method should be in kotlin extension



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(layoutInflater)


        //TODO change title of toolbar differently
        activity?.title = getString(R.string.third_fragment)

        changeOnBackPressedNavigationLogic()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun saveValueToBundle(colorIndex : Int){
        arguments?.apply {
            putInt(Constants.COLOR_INDEX_KEY, colorIndex)
        }
    }

    private fun initViews() {
        val randomNumberValue = arguments?.getInt(Constants.RANDOM_NUMBER_KEY).toString()
        if (!randomNumberValue.equals("null") && !randomNumberValue.equals("0")) {
            binding.tvRandomNumber.text = randomNumberValue
        }

    }

    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): ThirdFragment {
            val thirdFragment = ThirdFragment()
            thirdFragment.arguments = bundle
            return thirdFragment
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        if(arguments!=null) {
            outState.putAll(arguments)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        //Log.d("valueOnResume", arguments?.getInt(Constants.COLOR_INDEX_KEY).toString())
        val colorValue = arguments?.getInt(Constants.COLOR_INDEX_KEY)?.let {
            resources.getIntArray(R.array.iv_colors_array).get(it)
        }
        if (colorValue != null) {
            binding.mainScreen.setBackgroundColor(colorValue)
        }
    }


    override fun onStop() {
        super.onStop()
        if(!isNavigatingBack) {
            colorIndex++
            colorIndex %= resources.getIntArray(R.array.iv_colors_array).size
            saveValueToBundle(colorIndex)
            //Log.d("valueOnStop", arguments?.getInt(Constants.COLOR_INDEX_KEY).toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            this.arguments = savedInstanceState
        }
    }

    private fun changeOnBackPressedNavigationLogic(){
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isNavigatingBack = true
                    parentFragmentManager.beginTransaction().replace(
                        Constants.containerID,
                        SecondFragment.getInstance(arguments),
                        SecondFragment.SECOND_FRAGMENT_TAG
                    ).commit()
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
