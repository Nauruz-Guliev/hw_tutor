package ru.kpfu.itis.hw_android_2022.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class CatServiceActionsEnum : Parcelable {
    START_SERVICE, PRELOAD_IMAGE, DISPLAY_IMAGE, STOP_SERVICE
}