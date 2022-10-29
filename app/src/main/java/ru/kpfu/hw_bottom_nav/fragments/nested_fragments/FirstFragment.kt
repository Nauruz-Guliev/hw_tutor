package ru.kpfu.hw_bottom_nav.fragments.nested_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import ru.kpfu.hw_bottom_nav.R
import ru.kpfu.hw_bottom_nav.databinding.FragmentFirstBinding
import ru.kpfu.hw_bottom_nav.utils.NavigationFragmentInterface
import ru.kpfu.hw_bottom_nav.utils.getClassName
import ru.kpfu.hw_bottom_nav.utils.navigate

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding by lazy {_binding!!}
    private var className: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        className = arguments?.getString(ARG)?: FirstFragment.getClassName()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayouts()
    }

    private fun initLayouts() {
        with(binding.includer) {
            tvFragmentName.text = className
            btnNavigate.setOnClickListener {
                navigate(R.id.action_firstFragment_to_secondFragment2, SecondFragment)
            }
        }
    }


    companion object: NavigationFragmentInterface {
        override val ARG: String = "FIRST_FRAGMENT"
        override fun createBundle(name: String) = Bundle().apply {
            putString(ARG, name)
        }
    }
}