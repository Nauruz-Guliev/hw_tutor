package ru.kpfu.itis.hometasktwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.hometasktwo.databinding.ActivityMainBinding
import ru.kpfu.itis.hometasktwo.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val containerID: Int = R.id.main_fragments_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if(savedInstanceState !=null) {
            return
        }

        supportFragmentManager.beginTransaction().add(
            containerID,
            FirstFragment.getInstance(Bundle()),
            FirstFragment.FIRST_FRAGMENT_TAG
        ).setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .addToBackStack(null)
            .commit()
    }
}
