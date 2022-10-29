package ru.kpfu.hw_rv

import android.content.Context

object Repository {

    var dataList: List<MyModel> = listOf()
        private set

    private val randomGeneralItems: List<MyModel> = listOf(
        MyModel.Item(
            "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work.",
            "In Search of Lost Time"
        ),
        MyModel.Item(
            "Ulysses chronicles the passage of Leopold Bloom through Dublin during an ordinary day, June 16, 1904. The title parallels and alludes to Odysseus (Latinised into Ulysses), the hero of Homer's",
            "Ulysses "
        )
    )
    private val randomAdvertisementItems: List<MyModel> = listOf(
        MyModel.Advertisement(
            "Burger",
            "https://study.com/cimages/multimages/16/burgerad15179945781952220614.png"
        ),
        MyModel.Advertisement(
            "Coca cola",
            "https://datantify.com/knowledge/wp-content/uploads/2019/07/coca-cola_the_pause_that_refreshes_1931-610x697.jpg"
        ),
        MyModel.Advertisement(
            "Snickers",
            "https://purpleaddictcom.files.wordpress.com/2016/10/e067b-snickers2.jpg?w=640"
        )
    )

    fun generateList(size: Int) {
        val list = mutableListOf<MyModel>()
        var k = 0
        for (i in 0 until size) {
            if (i % 6 == 0) {
                k = 0
                val item =
                    randomAdvertisementItems[(randomAdvertisementItems.indices).random()] as MyModel.Advertisement
                val newItem = item.copy()
                list.add(newItem)
            } else {
                val item =
                    (randomGeneralItems[(randomGeneralItems.indices).random()] as MyModel.Item)
                val newItem = item.copy()
                newItem.title = k.toString() + " " + newItem.title
                list.add(newItem)
            }
            k++
        }
        dataList = list

    }

    fun addItem(position: Int, item: MyModel.Item) {
        val list = dataList.toMutableList()
        if (position >= dataList.size - 1) {
            list.add(item)
        } else {
            list.add(position, item)
            for (i in position..dataList.size) {
                if (list[i] is MyModel.Advertisement && i % 6 != 0) {
                    list[i] = list[i - 1].also {
                        list[i - 1] = list[i]
                    }
                }
            }
        }
        dataList = list.toList()
    }

    fun deleteItem(context: Context, position: Int) {
        val list = dataList.toMutableList()
        list.removeAt(position)
        for (i in position until dataList.size - 2) {
            if (list[i] is MyModel.Advertisement && i % 6 != 0) {
                /*
                val adItem = list[i] as MyModel.Advertisement
                val genItem = list[i+1] as MyModel.Item
                list[i] = genItem
                list[i+1] = adItem
                */
                list[i] = list[i + 1].also {
                    list[i + 1] = list[i]
                }
            }
            if (list[list.size - 1] is MyModel.Advertisement) {
                list.removeAt(list.size - 1)
            }
        }
        dataList = list.toList()
    }
}