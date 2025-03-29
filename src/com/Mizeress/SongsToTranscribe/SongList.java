package com.Mizeress.SongsToTranscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with a list of songs
 */
public class SongList {
    private List<Song> songList;

    // -----------CONSTRUCTORS------------\\
    public SongList() {
        songList = new ArrayList<>();
    }

    public SongList(List<Song> songList) {
        this.songList = songList;
    }

    //-------------------------------------\\

    /**
     * Sort the list by Author, Name in ascending order
     */
    public void sortList() {
        songList.sort(null);
    }

    /**
     * Add a song to the list
     * @param songToAdd song to add to list
     */
    public void addSong(Song songToAdd) {
        songList.add(songToAdd);
    }

    /**
     * Remove a song from the list
     * @param songToDelete song to delete from list
     */
    public void deleteSong(Song songToDelete) {
        for(Song song : songList) {
            if (song.equals(songToDelete)) {
                songToDelete = song;
            }
        }

        songList.remove(songToDelete);

    }

    //---------------TOSTRING-----------------------//
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(String.format("%-25s %-25s %n", "Name:", "Artist:"));

        for(Song song : songList) {
            string.append(String.format("%-25s %-25s%n", song.getName(), song.getArtist()));
        }

        return string.toString();

    }

    //----------GETTERS AND SETTERS--------------//
    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

}
