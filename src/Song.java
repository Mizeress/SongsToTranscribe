import javafx.util.Pair;

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

    @Override
    public int compareTo(Song otherSong) {
        int artistComparison = this.getArtist().compareTo(otherSong.getArtist());

        if (artistComparison != 0) {
            return artistComparison;
        } else {
            return this.getName().compareTo(otherSong.getName());
        }
    }

    public boolean equals(Song otherSong) {
        return (getName().equals(otherSong.getName())) && (getArtist().equals(otherSong.getArtist()));
    }

}
