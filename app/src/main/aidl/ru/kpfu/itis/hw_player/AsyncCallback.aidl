package ru.kpfu.itis.hw_player;


import ru.kpfu.itis.hw_player.AidlException;


interface AsyncCallback {
   oneway void onSuccess(in int index);
   oneway void onError(in AidlException error);
}