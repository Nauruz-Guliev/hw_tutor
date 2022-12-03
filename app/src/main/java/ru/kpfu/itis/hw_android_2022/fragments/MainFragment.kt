package ru.kpfu.itis.hw_android_2022.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.models.CatServiceActionsEnum.*
import ru.kpfu.itis.hw_android_2022.databinding.MainFragmentBinding
import ru.kpfu.itis.hw_android_2022.isServiceRunning
import ru.kpfu.itis.hw_android_2022.models.CatServiceActionsEnum
import ru.kpfu.itis.hw_android_2022.service.CatService
import ru.kpfu.itis.hw_android_2022.showToast

class MainFragment : Fragment(R.layout.main_fragment), View.OnClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding by lazy { _binding!! }
    private var catServiceIntent: Intent? = null
    private var imageUrl: String? = null

    private val catImageUrlsArray by lazy { resources.getStringArray(R.array.cat_array) }
    private var urlIndex = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainFragmentBinding.bind(view)
        catServiceIntent = Intent(
            binding.root.context,
            CatService::class.java
        )
        registerReceiver()
        initClickListeners()
    }


    private fun manageAction(action: CatServiceActionsEnum?) {
        with(binding.root.context) {
            val isRunning = isServiceRunning(CatService::class.java)
            val isStartAction = action == START_SERVICE
            when {
                isStartAction && !isRunning -> {
                    startCatService(action)
                    showToast(getString(R.string.service_success))
                }
                isStartAction && isRunning -> {
                    showToast(getString(R.string.service_is_running_error))
                }
                action == PRELOAD_IMAGE && isRunning -> {

                    imageUrl = catImageUrlsArray[urlIndex]
                    urlIndex++
                    urlIndex %= catImageUrlsArray.size
                    catServiceIntent?.putExtra(CatService.IMAGE_URL_KEY, imageUrl)
                    startCatService(action)
                }
                !isStartAction && isRunning -> {
                    startCatService(action)
                }
                else -> {
                    showToast(getString(R.string.service_not_running_error))
                }
            }
        }
    }

    // можно так кратко на все кнопки одно действие повесить при надобности
    private fun initClickListeners() {
        binding.root.children.forEach {
            when (it) {
                is Button -> it.setOnClickListener(this)
            }
        }
    }

    private fun registerReceiver() {
        LocalBroadcastManager.getInstance(binding.root.context).registerReceiver(
            catServiceReceiver, IntentFilter(
                CatService.IMAGE_INTENT_KEY
            )
        )
    }

    override fun onPause() {
        super.onPause()
        //не забываем отписаться от приемщика
        activity?.unregisterReceiver(catServiceReceiver)
    }

    //отдельный класс нет смысла создавать
    private val catServiceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(CatService.IMAGE_KEY, Bitmap::class.java)
            } else {
                intent?.getParcelableExtra(CatService.IMAGE_KEY) as? Bitmap
            }
            val message = intent?.getStringExtra(CatService.MESSAGE_KEY)
            when {
                bitmap != null -> binding.ivCats.setImageBitmap(bitmap)
                message != null -> binding.root.context.showToast(message)
            }
        }
    }

    //вроде, так более кратко, чем view.setOnClickListener { }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start_foreground_service -> manageAction(START_SERVICE)
            R.id.btn_start_downloading_image -> manageAction(PRELOAD_IMAGE)
            R.id.btn_display_image -> manageAction(DISPLAY_IMAGE)
            R.id.btn_stop_service -> manageAction(STOP_SERVICE)
        }
    }

    private fun startCatService(action: CatServiceActionsEnum?) {
        catServiceIntent?.action = action?.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(catServiceIntent)
        } else {
            activity?.startService(catServiceIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        const val FRAGMENT_NAME = "MAIN_FRAGMENT"
    }
}