



public class Songs {
    private int songId;
    private String songName;
    private String artist;
    private String genre;
    private String duration;

    public Songs(int songId, String songName, String artist, String genre, String duration) {
        this.songId = songId;
        this.songName = songName;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    public Songs() {

    }

    public int getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }
    public int compareTo(Songs e) {
        return this.songName.compareTo(e.songName);
    }

    @Override
    public String toString() {
        return songId+"\t\t"+songName+"\t\t"+artist+"\t\t"+genre+"\t\t"+duration;
    }
}
