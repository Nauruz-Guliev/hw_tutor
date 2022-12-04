// MusicAIDL.aidl
package ru.kpfu.itis.hw_player;

// Declare any non-default types here with import statements
import ru.kpfu.itis.hw_player.AsyncCallback;


interface MusicAIDL {
    boolean isPlaying();

    oneway void play(int position, AsyncCallback callback);

    void stop();

    int currentSongPosition();
}