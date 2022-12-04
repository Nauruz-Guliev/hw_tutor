package ru.kpfu.itis.hw_player

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.itis.hw_player.databinding.ActivityMainBinding
import ru.kpfu.itis.hw_player.recyclerView.MusicListAdapter
import ru.kpfu.itis.hw_player.recyclerView.SongActionCallback
import ru.kpfu.itis.hw_player.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: MusicListAdapter? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = MusicAIDL.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Repository.initRepository(binding.root.context)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MusicListAdapter(object : SongActionCallback {
            override fun play(position: Int) {
                binder?.play(position, object : AsyncCallback.Stub() {
                    override fun onSuccess(index: Int) {
                        Log.d("SYSTEM_MESSAGE", "status:${binder?.isPlaying} ")
                        if(position != index) {
                            Repository.setPlaying(position)
                            Repository.setPaused(index)
                        } else {
                            Repository.setPaused(index)
                            if(binder?.isPlaying == true) {
                                Repository.setPlaying(index)
                            }
                        }
                        adapter?.submitList(Repository.getPlayerSongModelList())
                    }

                    override fun onError(error: AidlException?) {
                        TODO("Not yet implemented")
                    }

                })
            }

            override fun onSongClicked(position: Int) {
                //todo
            }
        })

        adapter?.submitList(Repository.getPlayerSongModelList())
        binding.rvMusic.adapter = adapter
    }


    override fun onStart() {
        super.onStart()
        bindService(
            getMusicServiceIntent(),
            connection,
            android.app.Service.BIND_AUTO_CREATE
        )
        startService(getMusicServiceIntent())
    }

    private fun getMusicServiceIntent() = Intent(this@MainActivity, MusicService::class.java)

    companion object {
        private var binder: MusicAIDL? = null
    }
}