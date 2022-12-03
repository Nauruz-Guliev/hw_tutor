package ru.kpfu.itis.hw_android_2022

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.widget.Toast

fun<T> Context.showToast(message: T) {
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()
}

// как иначе проверить, работает ли сервис?
// можно просто завести переменную в фрагменте, но правильно ли это??
fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it -> it.service.className == service.name }
}