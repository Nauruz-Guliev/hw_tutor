package ru.kpfu.hw_bottom_nav.utils

import android.os.Bundle

interface NavigationFragmentInterface {
    val ARG: String
    fun createBundle(name: String) : Bundle
}