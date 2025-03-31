package com.Mizeress.SongsToTranscribe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for interacting with a csv file containing a list of songs
 */
public class SongListFile {
    static final String PATH = "songs.csv";
    private SongList songList;

    public SongListFile() {
        songList = readFile();
    }

    /**
     * Read the songs file and populate a list in memory to match it
     * @return A SongList representation of the current state of the songs file
     */
    public SongList readFile() {
        String line;
        SongList resultList = new SongList();

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            br.readLine(); //Skip over column header

            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Song song = new Song(data[0], data[1]);
                resultList.addSong(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    /**
     * Append a song to the song file and list in memory
     * @param song the song to append
     */
    public void appendSong(Song song) {
        String lineToWrite;
        lineToWrite = song.getName() + "," + song.getArtist() + "\n";

        try(FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(lineToWrite);
            songList.addSong(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete song from the list
     * @param song The song to delete
     */
    public void deleteSong(Song song) {
        songList.deleteSong(song);
        writeList();
    }

    /**
     * Sort the list by Artist, Name
     */
    public void sortList() {
        songList.sortList();
        writeList();
    }

    /**
     * Writes the current state of the SongList to the songs file
     */
    public void writeList() {
        try(FileWriter writer = new FileWriter(PATH)) {
            writer.write("Name,Artist\n");

            for (Song song : songList.getSongList()) {
                String lineToWrite = song.getName() + "," + song.getArtist() + "\n";
                writer.write(lineToWrite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //-------------------GETTERS AND SETTERS------------------\\
    public SongList getSongList() {
        return songList;
    }

    public void setSongList(SongList songList) {
        this.songList = songList;
    }
}
