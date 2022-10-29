package ru.kpfu.hw_bottom_nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.kpfu.hw_bottom_nav.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller =
            (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
                .navController

        with(binding) {
            binding.bnvMain.run {
                bnvMain.setupWithNavController(controller)
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.firstFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        findViewById<Toolbar>(androidx.appcompat.R.id.action_bar)
            .setupWithNavController(controller, appBarConfiguration)
    }
}