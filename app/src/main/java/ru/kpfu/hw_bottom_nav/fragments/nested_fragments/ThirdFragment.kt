package ru.kpfu.hw_bottom_nav.fragments.nested_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.hw_bottom_nav.R
import ru.kpfu.hw_bottom_nav.databinding.FragmentThirdBinding
import ru.kpfu.hw_bottom_nav.utils.NavigationFragmentInterface
import ru.kpfu.hw_bottom_nav.utils.navigate


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding by lazy {_binding!!}
    private var className: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            className = it?.getString(ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayouts()
    }

    private fun initLayouts(){
        with(binding.includer) {
           //todo
        }
    }

    companion object : NavigationFragmentInterface{
        override val ARG = "THIRD_FRAGMENT"
        override fun createBundle(name: String) = Bundle().apply {
            putString(ARG, name)
        }
    }
}