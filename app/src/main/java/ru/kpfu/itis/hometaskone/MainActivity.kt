package ru.kpfu.itis.hometaskone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.itis.hometaskone.databinding.ActivityMainBinding
import ru.kpfu.itis.hometaskone.fragments.SecondFragment
import ru.kpfu.itis.hometaskone.fragments.ThirdFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val containerID: Int = R.id.main_fragments_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction().add(
            containerID,
            ThirdFragment.getInstance(Bundle()),
            ThirdFragment.THIRD_FRAGMENT_TAG
        )
            .commit()
    }
}

