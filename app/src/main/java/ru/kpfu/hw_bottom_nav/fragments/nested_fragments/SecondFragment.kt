package ru.kpfu.hw_bottom_nav.fragments.nested_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.hw_bottom_nav.R
import ru.kpfu.hw_bottom_nav.databinding.FragmentSecondBinding
import ru.kpfu.hw_bottom_nav.utils.NavigationFragmentInterface
import ru.kpfu.hw_bottom_nav.utils.navigate

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding by lazy { _binding!! }
    private var className: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)
        arguments.let {
            className = it?.getString(ARG)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayouts()
    }

    private fun initLayouts() {
       //todo
    }

    companion object : NavigationFragmentInterface {
        override val ARG = "SECOND_FRAGMENT"
        override fun createBundle(name: String) = Bundle().apply {
            putString(ARG, name)
        }
    }
}