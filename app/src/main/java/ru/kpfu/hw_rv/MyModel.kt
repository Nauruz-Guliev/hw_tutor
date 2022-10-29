package ru.kpfu.hw_rv

sealed class MyModel(val id: Int) {
    data class Advertisement(val title: String, val imageUrl:String): MyModel(0)
    data class Item(val description: String, var title: String): MyModel(0)
}
