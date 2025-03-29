package com.Mizeress.SongsToTranscribe;

import javafx.util.Pair;

/**
 * Name, Author pair representation of a song
 */
public class Song implements Comparable<Song> {
    private final Pair<String, String> song;

    public Song(String name, String artist) {
        song = new Pair<>(name, artist);
    }

    public Pair<String, String> getSong() {
        return song;
    }

    public String getName() {
        return song.getKey();
    }

    public String getArtist() {
        return song.getValue();
    }

    /**
     * Compare this song object with another song object in Author, Name order.
     * @param otherSong the other song to be compared.
     * @return -1 if this song comes before otherSong, 0 if equal, 1 if this song follows otherSong
     */
    @Override
    public int compareTo(Song otherSong) {
        int artistComparison = this.getArtist().compareTo(otherSong.getArtist());

        if (artistComparison != 0) {
            return artistComparison;
        } else {
            return this.getName().compareTo(otherSong.getName());
        }
    }

    /**
     * Determines equality between two songs
     * @param otherSong song to check for equality with this song
     * @return true if name and artist of otherSong are equal to this song
     */
    public boolean equals(Song otherSong) {
        return (getName().equals(otherSong.getName())) && (getArtist().equals(otherSong.getArtist()));
    }

}
