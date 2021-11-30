import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class User implements SongsInterface,Podcastsinterface {
    private static Connection connection;
    private static String users_Id;
    private String usersPassword;
    ArrayList<Songs> song = new ArrayList<>();
    ArrayList<Songs> serch = new ArrayList<>();
    ArrayList<Podcasts> pserch = new ArrayList<>();
    private static boolean debug = false;
    ArrayList<Podcasts> podcasts = new ArrayList<>();

    public User(String users_Id, String usersPassword) {
        this.users_Id = users_Id;
        this.usersPassword = usersPassword;
    }

    public User() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxProject", "root", "Iphone@259696");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void debug(String value) {
        if(debug) {
            System.out.println(value);
        }
    }
    public String getUsers_Id() {
        return users_Id;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public boolean authenticateUser() {
        Boolean userValid = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("UserName.....?");
        users_Id = sc.next();
        System.out.println("Password.....?");
        usersPassword = sc.next();
        String retrivedPasswd = "";
        try {
            Statement stmt = getConnection().createStatement();
            //java.sql.SQLSyntaxErrorException: Unknown column 'usersPassword' in 'field list'
            String query = "select usersPassword from users where users_Id = '" + users_Id + "';";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                retrivedPasswd = result.getString("usersPassword");
            }
            if (usersPassword.equals(retrivedPasswd)) userValid = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userValid;
    }

    public void displaySongs() {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from song order by songId");
            System.out.printf("%-10s %-20s %-20s %-10s %-10s\n", "songId", "Name", "Artist", "Genre", "Duration");
            while (result.next()) {
                //System.out.printf("%-3s %-20s %-20s %-10s %-10s\n",result.getString("songId"),result.getString("songName").substring(0,15),result.getString("artist"),result.getString("genre"),result.getString("duration"));
                song.add(new Songs(result.getInt("songId"), result.getString("songName").substring(0, 15), result.getString("artist"), result.getString("genre"), result.getString("duration")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaiAllSongs() {
        song.stream().forEach(System.out::println);
    }

    public void serchSongByGener() {
        Scanner sc = new Scanner(System.in);
        String Search=sc.next();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from song where songName='"+Search+"'or artist='"+Search+"'or genre='"+Search+"'");
            //select * from Songs where Song_Name='Happy_Life.wav'or Song_Artist='2'or Song_Genre='Rock';
            System.out.printf("%s %15s %15s %15s %15s","Song_ID","Song_Name","Song_Artist","Song_Genre","Song_Duration\n");
            while(result.next())
            {
                //System.out.printf("%-3s %-20s %-20s %-10s %-10s\n",result.getString("songId"),result.getString("songName").substring(0,15),result.getString("artist"),result.getString("genre"),result.getString("duration"));
                serch.add(new Songs(result.getInt("songId"), result.getString("songName").substring(0, 15), result.getString("artist"), result.getString("genre"), result.getString("duration")));

            }
            serch.stream().forEach(System.out::println);
            System.out.println("\nHere");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public static void playSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
        System.out.print("Select Song..");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        String songName = "";
        String filePath = "C:\\Users\\Guru\\Desktop\\NIIT\\Week_7 Project\\Songs\\";
        try {
            Statement stmt = getConnection().createStatement();
            String query = "select songName from song where songId = '" + option + "';";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                songName = result.getString("songName");
            }
            filePath = filePath + songName;
            System.out.println("file Path:::" + filePath);
            System.out.println("Passing " + filePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Passing " + filePath);
        SimpleAudioPlayer.main(filePath);
        System.out.println("Came out of file");
        ///displaySongs();
        optionsDisplaySongs();

    }
    public static void optionsDisplaySongs() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Play Song\n2) Add to PlayList\n3) Main Menu");
        //System.out.println("is stream available"+System.in.available());
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        System.out.println("Selected Option " + option);
        switch(option) {
            case 1: playSong();
                break;
            case 2:
                break;
            //case 3: optionsMain();
              //  break;
            default: System.out.println("Correct Option");optionsDisplaySongs();
        }

    }
    public void displayPodcasts() {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from Podcast order by PodcastId");
            System.out.printf("%-10s %-40s %-8s %-10s %-3s\n", "PodcastId", "Name", "Host", "Episodes", "users_Id");
            while (result.next()) {
                //System.out.printf("%-3s %-20s %-20s %-10s %-10s\n",result.getString("songId"),result.getString("songName").substring(0,15),result.getString("artist"),result.getString("genre"),result.getString("duration"));
                podcasts.add(new Podcasts(result.getInt("PodcastId"), result.getString("Name"), result.getString("Host"), result.getString("Episodes"), result.getString("users_Id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void displaiAllpodcasts() {
        podcasts.stream().forEach(System.out::println);
    }
    public void serchSongBy_Host(String genre) {

        System.out.printf("%-10s %-20s %-20s %-10s %-10s\n", "songId", "Name", "Artist", "Genre", "Duration");
        song.stream().filter(x -> x.getSongName().equalsIgnoreCase(genre)).sorted(Comparator.comparing(Songs::getSongName));
    }
    public static void playPodcasts() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
        System.out.print("Select Podcast..");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        String Podcast_Name = "";
        String filePath = "C:\\Users\\Guru\\Desktop\\NIIT\\Week_7 Project\\Podcasts\\";
        try {
            Statement stmt = getConnection().createStatement();
            String query = "select Name from Podcast where PodcastId = '" + option + "';";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Podcast_Name = result.getString("Name");
            }
            filePath = filePath + Podcast_Name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleAudioPlayer.main(filePath);
        System.out.println("Came out of file");
        ///displaySongs();
        optionsDisplayPodcasts();

    }
    public static void optionsDisplayPodcasts() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Play Podcast\n2) Add to PlayList\n3) Main Menu");
        //System.out.println("is stream available"+System.in.available());
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        System.out.println("Selected Option " + option);
        switch(option) {
            case 1: playPodcasts();
                break;
            case 2:
                break;
            //case 3: optionsMain();
            //  break;
            default: System.out.println("Correct Option");optionsDisplaySongs();
        }

    }
    public static void createPlayList(String user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter playList Name    :");
        String name = sc.next();
        boolean playListExists = false;
        try{
            Statement stmt = getConnection().createStatement();
            String query = "Select Name from PlayList where users_Id = '" + user + "';";
            debug(query);
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                if(result.getString("Name").equals(name)){ playListExists = true ; System.out.println("playList already exists");debug("playlist exists");}
            }
            if(!playListExists) {
                debug("Trying to insert playlist Name::::");
                query = "insert into PlayList (users_Id,Name) values('" + user + "','"+name+"')";
                debug(query);
                stmt.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void serchPodcastsBy_Host() {
        Scanner sc = new Scanner(System.in);
        String Search=sc.next();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from Podcast where Name='"+Search+"'or Host='"+Search+"'or Episodes='"+Search+"'");
            //select * from Songs where Song_Name='Happy_Life.wav'or Song_Artist='2'or Song_Genre='Rock';
            System.out.printf("%-10s %-30s %-8s %-10s %-3s\n","PodcastId","Name","Host","Episodes","users_Id\n");
            while(result.next())
            {
                pserch.add(new Podcasts(result.getInt("PodcastId"), result.getString("Name").substring(0,25), result.getString("Host"), result.getString("Episodes"), result.getString("users_Id")));

            }
            pserch.stream().forEach(System.out::println);
            System.out.println("\nHere");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
