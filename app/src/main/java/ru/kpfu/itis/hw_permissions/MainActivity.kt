package ru.kpfu.itis.hw_permissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import ru.kpfu.itis.hw_permissions.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var cameraIntent: Intent? = null
    private var galleryIntent: Intent? = null
    private var imageUri: Uri? = null
    private var chooserIntent: Intent? = null
    private var PICK_IMAGE_ID: Int = 1

    //для первого способа
    //запускаем внешний интент и получаем результат, полученные данные будут храниться в
    //result.data?.data
    //в нашем случае это uri от фото
    private val activityForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            binding.ivPhoto.setImageURI(result.data?.data)
            this.onActivityResult(
                PICK_IMAGE_ID,
                result.resultCode,
                result.data
            )
        }

    private val ff = registerForActivityResult(SecondActivityContract()){
        if(it == null) {
            binding.ivPhoto.setImageURI(imageUri)
        } else {
            binding.ivPhoto.setImageURI(it)
        }
    }
    //получаем фото вторым способом
    //запускаем камеру, которая записывает фото по предварительно заданному URI
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) binding.ivPhoto.setImageURI(imageUri)
    }

    //запрашиваем несколько разрешений
    private val mPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    //можно переключаться тут между разными способами?
    private fun setOnClickListeners() {
        with(binding) {
            btnOpenCamera.setOnClickListener {
                imageUri = initTempUri()
                ff.launch(imageUri)
            }
        }
    }

    //инициализируем 2 простых интента и один общий, который будем в себе 2 простых содержать
    private fun initIntents() {
        galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        ActivityResultContracts.TakePicturePreview()

        cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).takeIf {
            it.resolveActivity(packageManager) != null
        }?.apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }

        chooserIntent = Intent.createChooser(galleryIntent, "Choose")
        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
    }

    //инициализурем интенты, запрашиваем разрешения, проверяем,  и если всё ок, то  запускаем активити
    //открывается окошко
    private fun takePictureMethodSecond() {
        initIntents()
        requirePermissions()
        // надо проверить разрешения на чтение/запись тоже (Manifest.permission.WRITE/READ_EXTERNAL_STORAGE)
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            activityForResult.launch(chooserIntent)
        }
    }
    //запрашиваем разрешения
    //инициализируем путь до временной ссылки на фото
    //если всё ок, то запускаем камеру
    private fun takePictureMethodFirst() {
        requirePermissions()
        imageUri = initTempUri()
        // надо проверить разрешения на чтение/запись тоже (Manifest.permission.WRITE/READ_EXTERNAL_STORAGE)
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePhoto.launch(imageUri)
        }
    }

    //требуем у пользователя разрешения (RUNTIME PERMISSIONS)
    private fun requirePermissions() {
        //передаем все необходимые разрешения как массив
        mPermissions.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        )
    }

    //инициализируем временную uri для фото
    private fun initTempUri(): Uri {
        val tempImagesDir = File(
            applicationContext.filesDir,
            getString(R.string.temp_images_dir)
        )
        tempImagesDir.mkdir()
        val tempImage = File(
            tempImagesDir,
            getString(R.string.temp_image)
        )
        return FileProvider.getUriForFile(
            applicationContext,
            getString(R.string.authorities),
            tempImage
        )
    }
}