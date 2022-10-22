package ru.kpfu.itis

import android.util.Log
import android.widget.Toast

object Repository {

    private var firstCursor: Int = 1

    var secondCursor: Int = 4
        private set

    private val listRandomItems: List<DataModel> = listOf(
        DataModel.FirstType(
            91,
            "Most listened",
            "14 tracks - 17 25 minutes",
            "https://cdn.vox-cdn.com/thumbor/9wvE2zm60KvGXoPlFSNTByqibuM=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/9909315/YearEnd_Playlist02_AP_Ringer.jpg"
        ),
        DataModel.SecondType(12, "Sun ", R.drawable.ic_baseline_wb_sunny_24),
        DataModel.ThirdType(41,
            "Grace Jones",
            "12 tracks",
            "https://bestlifeonline.com/wp-content/uploads/sites/3/2018/06/envie-en-rose.jpg?quality=82&strip=all", false
        ),
    )
    fun getDataList(): List<DataModel> {
        return mutableDataList
    }


    private val mutableDataList: MutableList<DataModel> = mutableListOf(
        DataModel.FirstType(1,
            "My favorites",
            "5 tracks - \u0020 17 minutes",
            "https://i.pinimg.com/originals/be/fd/36/befd36c1007c8bf8a122af52bd2eef89.png"
        ),

        DataModel.SecondType(2,"Tracks", R.drawable.ic_baseline_audiotrack_24),
        DataModel.SecondType(3,"Downloaded", R.drawable.ic_outline_download_24),
        DataModel.SecondType(4,"For kids", R.drawable.ic_outline_child_care_24),


        DataModel.ThirdType(5,
            "The weekend",
            "72 tracks",
            "https://i.pinimg.com/originals/6d/2e/eb/6d2eeb0eaa66fbe2e6fbe3136e5e1680.jpg",true
        ),
        DataModel.ThirdType(6,
            "Monocle",
            "72 tracks",
            "https://e.snmc.io/i/300/w/0bc5cb804b55e50591b238badaf36eb2/155068", false
        )
    )

    fun addRandomItem(): Int {
        var position = 0
        when (val item = getRandomItem()) {
            is DataModel.FirstType -> {
                position = (0..firstCursor).random()
                item.id = (mutableDataList.size..Integer.MAX_VALUE).random()

                mutableDataList.add(position, item)
                firstCursor++
                secondCursor++
            }
            is DataModel.SecondType -> {
                position = (firstCursor..secondCursor).random()
                item.id = (mutableDataList.size..Integer.MAX_VALUE).random()

                mutableDataList.add(position, item)
                secondCursor++
            }
            is DataModel.ThirdType -> {
                position = (secondCursor until mutableDataList.size).random()
                item.id = (mutableDataList.size..Integer.MAX_VALUE).random()
                mutableDataList.add(position, item)
            }
        }
        return position
    }

    private fun getRandomItem(): DataModel {
        val item =  listRandomItems[(0..2).random()]
        return item
    }
}