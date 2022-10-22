package ru.kpfu.itis

sealed class DataModel (var id: Int){
    data class FirstType(var ID: Int, val primaryName: String, val secondaryName: String, var url: String): DataModel(ID)
    data class SecondType(var ID: Int,val name: String, val drawableID: Int): DataModel(ID)
    data class ThirdType(var ID: Int,val primaryName: String, val secondaryName: String, val url: String, var isFirstItem: Boolean): DataModel(ID)
}
