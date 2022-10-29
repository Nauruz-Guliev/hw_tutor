package ru.kpfu.hw_bottom_nav.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(id: Int, classCalled: NavigationFragmentInterface) =

    findNavController().navigate(
        id,
        classCalled.createBundle(
            classCalled.getClassName()
        )
    )


fun NavigationFragmentInterface.getClassName(): String = this.javaClass.toString()
    .substring(
        startIndex = this.javaClass.toString().lastIndexOf(".") + 1,
        endIndex = this.javaClass.toString().lastIndexOf("$")
    )