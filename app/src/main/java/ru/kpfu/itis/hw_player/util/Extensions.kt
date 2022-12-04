package ru.kpfu.itis.hw_player.util

import android.content.Context
import android.widget.Toast

fun<T: Any> Context.showToast(message: T?) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()