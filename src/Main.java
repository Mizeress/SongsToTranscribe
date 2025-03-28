import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final String PATH = "songs.csv";

    public static void main(String[] args) {
        List<Song> songList = readFile();
        songList.sort(null);

        printList(songList);

        boolean quit = false;

        // Ux Loop
        while(!quit) {
            Scanner scan = new Scanner(System.in);

            System.out.println("\nPress 'a' to add a song, 'd' to delete a song, 'p' to print the current list, 's' to sort the list, or 'q' to quit!");

            String enteredChar = scan.next();
            scan.nextLine(); //consume leftover nl

            switch (enteredChar.toLowerCase()) {
                case "a": {
                    String name;
                    String artist;

                    System.out.print("Song Name: ");
                    name = scan.nextLine();

                    System.out.print("Artist: ");
                    artist = scan.nextLine();

                    Song newSong = new Song(name, artist);

                    addSong(newSong);

                    //Update songList
                    songList = readFile();

                    printList(songList);
                    break;
                }

                case "d": {
                    String name;
                    String artist;

                    System.out.print("Song Name: ");
                    name = scan.nextLine();

                    System.out.print("Artist: ");
                    artist = scan.nextLine();

                    Song songToDelete = new Song(name, artist);

                    deleteSong(songToDelete, songList);
                    break;
                }


                case "p": {
                    printList(songList);
                    break;
                }

                case "s": {
                    sortList(songList);
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

    //Read the songs.csv file to an array
    private static List<Song> readFile() {
        String line;
        List<Song> resultList = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            br.readLine(); //Skip over column header

            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Song song = new Song(data[0], data[1]);
                resultList.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    //Print a two column table of the list
    private static void printList(List<Song> songList) {
        System.out.printf("%-25s %-25s %n", "Name:", "Artist:");
        for(Song song : songList) {
            System.out.printf("%-25s %-25s%n", song.getName(), song.getArtist());
        }
    }

    //Add provided song to file
    private static void addSong(Song song) {
        String lineToWrite;
        lineToWrite = song.getName() + "," + song.getArtist() + "\n";

        try(FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(lineToWrite);
            System.out.println("Added " + song.getName() + " " + song.getArtist() + " to File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sort the list and update the file
    private static void sortList(List<Song> songList) {
        songList.sort(null);
        updateList(songList);
    }

    //Update the file to match the list in memory
    private static void updateList(List<Song> songList) {
        try(FileWriter writer = new FileWriter(PATH)) {
            writer.write("Name,Artist\n");

            for (Song song : songList) {
                String lineToWrite = song.getName() + "," + song.getArtist() + "\n";
                writer.write(lineToWrite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Song> deleteSong(Song songToDelete, List<Song> songList) {
        //Loop through list, find target song, set song to delete reference to that song, delete it
        for(Song song : songList) {
            if (song.equals(songToDelete)) {
                songToDelete = song;
            }
        }

        songList.remove(songToDelete);
        updateList(songList);
        return songList;
    }
}
