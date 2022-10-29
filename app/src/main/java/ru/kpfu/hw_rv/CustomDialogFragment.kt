package ru.kpfu.hw_rv

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.kpfu.hw_rv.databinding.FragmentCustomDialogBinding

class CustomDialogFragment(
    val onAddButtonClicked: (position: Int, MyModel.Item) -> Unit) :
    DialogFragment() {

    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val metrics: DisplayMetrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog?.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
    }

    private fun initValues() {
        with(binding) {
            btnSubmit.setOnClickListener {
                //todo
                dialog?.hide()
            }
        }
    }
}