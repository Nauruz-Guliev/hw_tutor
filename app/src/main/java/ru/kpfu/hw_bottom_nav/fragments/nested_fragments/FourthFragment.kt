package ru.kpfu.hw_bottom_nav.fragments.nested_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.hw_bottom_nav.databinding.FragmentFourthBinding
import ru.kpfu.hw_bottom_nav.utils.NavigationFragmentInterface


class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
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
        _binding = FragmentFourthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayouts()
    }
    private fun initLayouts(){
     //todo
    }
    companion object : NavigationFragmentInterface{
        override val ARG: String = "FORTH_FRAGMENT"
        override fun createBundle(name: String) = Bundle().apply {
            putString(ARG, name)
        }
    }
}