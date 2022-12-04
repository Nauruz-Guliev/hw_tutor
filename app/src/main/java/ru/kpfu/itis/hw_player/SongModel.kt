package ru.kpfu.itis.hw_player

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SongModel (
    val id: Int,
    val name: String,
    val genre: String,
    val singer: String,
    @DrawableRes val cover: Int,
    val uri: Uri,
): Parcelable