package com.Mizeress.SongsToTranscribe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SongListFile listFile = new SongListFile();

        System.out.println();
        System.out.println(listFile.getSongList());

        boolean quit = false;

        // Ux Loop
        while(!quit) {
            Scanner scan = new Scanner(System.in);

            //Prompt
            System.out.print("\nPress 'a' to add a song, 'd' to delete a song, 'p' to print the current list, 's' to sort the list, or 'q' to quit! ");

            String enteredChar = scan.next();
            scan.nextLine(); //consume leftover nl

            //Operation performed based on enteredChar
            switch (enteredChar.toLowerCase()) {
                case "a": {
                    String name;
                    String artist;

                    //Get name for song to be added
                    System.out.print("Name: ");
                    name = scan.nextLine();

                    // Get artist for song to be added
                    System.out.print("Artist: ");
                    artist = scan.nextLine();

                    Song newSong = new Song(name, artist);

                    //Write newSong to the file and update the list in memory
                    listFile.writeSong(newSong);

                    System.out.println(listFile.getSongList());
                    break;
                }

                case "d": {
                    String name;
                    String artist;

                    //Get name for song to be deleted
                    System.out.print("Name: ");
                    name = scan.nextLine();

                    //Get artist for song to be deleted
                    System.out.print("Artist: ");
                    artist = scan.nextLine();

                    Song songToDelete = new Song(name, artist);

                    //Delete the song from the list in memory and update the file
                    listFile.getSongList().deleteSong(songToDelete);
                    listFile.writeList();

                    break;
                }


                case "p": {
                    System.out.println(listFile.getSongList());
                    break;
                }

                case "s": {
                    //Sort the list from memory and update file
                    listFile.getSongList().sortList();
                    listFile.writeList();
                    break;
                }

                case "q": {
                    quit = true;
                    break;
                }

                default: {
                    break;
                }
            }
        }
    }

}
