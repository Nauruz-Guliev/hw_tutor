package ru.kpfu.itis.hometasktwo.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.kpfu.itis.hometasktwo.databinding.FragmentDialogBinding


class CustomDialogFragment(val counterValue: Int, val onButtonClicked: (Int) -> Unit) : DialogFragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val metrics: DisplayMetrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog?.window?.setLayout(width,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }


    private fun checkValue() : Boolean{
        if (!binding.etCounter.text.isNullOrBlank()) {
            val editTextValue = Integer.valueOf(binding.etCounter.text.toString())
            if (!(editTextValue in 0..100)) {
                binding.textInputLayout.error = "Counter should be in range 0..100."
                return false
            } else {
                binding.textInputLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun initClickListeners(){
        var editTextValue = 0
        Toast.makeText(context,counterValue.toString(), Toast.LENGTH_LONG ).show()

        with(binding) {
            positiveButton.setOnClickListener {
                if (!binding.etCounter.text.isNullOrBlank()) {
                    editTextValue = Integer.valueOf(binding.etCounter.text.toString())
                }
                if(checkValue()) {
                    onButtonClicked(counterValue + editTextValue)
                }

            }
            neutralButton.setOnClickListener {
                dismiss()
            }
            negativeButton.setOnClickListener {
                if (!binding.etCounter.text.isNullOrBlank()) {
                    editTextValue = Integer.valueOf(binding.etCounter.text.toString())
                }
                if(checkValue()) onButtonClicked(counterValue - editTextValue)

            }
        }
    }

}
