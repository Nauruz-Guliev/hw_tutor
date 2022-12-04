package ru.kpfu.itis.hw_player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class MusicActionsEnum : Parcelable {
    PLAY,  STOP, PREV, NEXT, PAUSE, RESUME
}