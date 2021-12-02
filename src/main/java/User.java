import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.function.Predicate;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class User implements SongsInterface,Podcastsinterface,PlayList_Interface,PlayListData_Interface {
    private static Connection connection;
    private static String users_Id;
    private String usersPassword;
    ArrayList<Songs> song = new ArrayList<>();
    //ArrayList<Songs> serch = new ArrayList<>();
    ArrayList<Podcasts> pserch = new ArrayList<>();
    private static boolean debug = false;
    ArrayList<Podcasts> podcasts = new ArrayList<>();
    ArrayList<PlayList> playlist = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

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
            if (usersPassword.equals(retrivedPasswd)) {userValid = true;}
            else{
                System.out.println("Incorrect Password. Please try again!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userValid;
    }
    public void New_User(){
        System.out.println("Enter your Name");
        String name = sc.next();
        System.out.println("Enter Your New Password");
        String password = sc.next();
        try {
            Statement stmt = getConnection().createStatement();
            String Query = "insert into Users values ('"+name+"','"+password+"')";
            boolean result = stmt.execute(Query);
            System.out.println("Your Apple_Music Account is Succesfully Created\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public ArrayList<Songs> displaySongs() {
        try {
            //System.out.println("=====================================================================================================");
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
        return song;
    }
    public void displaiAllSongs() {
        song.stream().forEach(System.out::println);
    }
    public void display_Songs() {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from song order by songId");
            System.out.printf("%-10s %-20s %-20s %-10s %-10s\n", "songId", "Name", "Artist", "Genre", "Duration");
            while (result.next()) {
                System.out.printf("%-3s %-20s %-20s %-10s %-10s\n",result.getString("songId"),result.getString("songName").substring(0,15),result.getString("artist"),result.getString("genre"),result.getString("duration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Songs>  serchSongByGener(String Search) {
        ArrayList<Songs> serch = new ArrayList<>();
        //String Search=sc.next();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from song where songName='"+Search+"'or artist='"+Search+"'or genre='"+Search+"'");
            System.out.printf("%s %15s %15s %15s %15s","Song_ID","Song_Name","Song_Artist","Song_Genre","Song_Duration\n");
            while(result.next())
            {
                serch.add(new Songs(result.getInt("songId"), result.getString("songName").substring(0, 15), result.getString("artist"), result.getString("genre"), result.getString("duration")));
            }
            serch.stream().forEach(System.out::println);
            System.out.println("\nHere");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return serch;
    }
    public  void playSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleAudioPlayer.main(filePath);
        System.out.println("Came out of file");
        displaySongs1();
        optionsDisplaySongs();

    }
    public static void displaySongs1() {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery("select * from song order by songId");
            System.out.printf("%-3s %-20s %-20s %-10s %-10s\n","songId", "Name", "Artist", "Genre", "Duration");
            while(result.next()) {
                System.out.printf("%-3s %-20s %-20s %-10s %-10s\n",result.getString("songId"),result.getString("songName").substring(0,15),result.getString("artist"),result.getString("genre"),result.getString("duration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void optionsDisplaySongs() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Play Song\n2) Add to PlayList\n3) Main Menu\n4)Exit");
        //System.out.println("is stream available"+System.in.available());
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        System.out.println("Selected Option " + option);
        switch(option) {
            case 1: playSong();break;
            case 2:viewPlayList();break;
            case 3:main_Menu();
            case 4:System.exit(0); break;
            default: System.out.println("Correct Option");
        }

    }
    public ArrayList<Podcasts> displayPodcasts() {
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
        return podcasts;
    }
    public void displaiAllpodcasts() {
        podcasts.stream().forEach(System.out::println);
    }
    public void serchSongBy_Host(String genre) {

        System.out.printf("%-10s %-20s %-20s %-10s %-10s\n", "songId", "Name", "Artist", "Genre", "Duration");
        song.stream().filter(x -> x.getSongName().equalsIgnoreCase(genre)).sorted(Comparator.comparing(Songs::getSongName));
    }
    public  void playPodcasts() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
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
    public  void optionsDisplayPodcasts() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Play Podcast\n2) Add to PlayList\n3) Main Menu\n4) Exit");
        //System.out.println("is stream available"+System.in.available());
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        System.out.println("Selected Option " + option);
        switch(option) {
            case 1: playPodcasts();break;
            case 2: viewPlayList();break;
            case 3: main_Menu();break;
            case 4: System.exit(0);break;
            default: System.out.println("Correct Option");
        }

    }
    public static void createPlayList(String user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter playList Name    :");
        String name = sc.next();
        sc.nextLine();
        boolean playListExists = false;
        try{
            Statement stmt = getConnection().createStatement();
            String query = "Select Name from PlayList where users_Id = '" + user + "';";
            debug(query);
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                if(result.getString("Name").equals(name)){ playListExists = true ; System.out.println("playList already exists");debug("playlist exists");Main.optionsMain();}
            }
            if(!playListExists) {
                debug("Trying to insert playlist Name::::");
                query = "insert into PlayList (users_Id,Name) values('" + user + "','"+name+"')";
                debug(query);
                stmt.execute(query);
                System.out.println("Your PlayList Created Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public ArrayList<Podcasts> serchPodcastsBy_Host(String Search) {
        Scanner sc = new Scanner(System.in);

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
        return pserch;
    }
    public ArrayList<PlayList> Display_Playlists() {
        try {
            Statement stmt = getConnection().createStatement();
            String query = "select * from playlist";
            ResultSet r1 = stmt.executeQuery(query);
            while (r1.next()) {
                playlist.add(new PlayList(r1.getString("users_Id"), r1.getInt("Sno"), r1.getString("Name")));
            }
            playlist.stream().forEach(System.out::println);//Main.optionsMain();
        } catch (Exception e) {
            System.out.println(e);
        }
        return playlist;
    }
    public  void optionsDisplayPlayList() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Add to PlayList\n2) Main Menu\n3) Exit");
        //System.out.println("is stream available"+System.in.available());
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        System.out.println("Selected Option " + option);
        switch(option) {
            case 1://add_To_PlayList();
                break;
            case 2:
                break;
            case 3:System.exit(0);break;
            //case 3: optionsMain();
            //  break;
            default: System.out.println("Correct Option");optionsDisplaySongs();
        }

    }
    public static void select_PlayList() {
        System.out.println("Select Your PlayList");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        //int PlayListName = 0;
        String name = "";
        try {
            Statement stmt = getConnection().createStatement();
            String query = "Select  Name from PlayList  where Sno = " + option + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                //PlayListName = result.getInt("Sno");
               name = result.getString("Name");
            }
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxProject", "root", "Iphone@259696");
            System.out.println("Enter Song Option:");
            int sid = sc.nextInt();
            String sName = "";
            Statement stmt1 = getConnection().createStatement();
            String song = "Select songName from song where songId = "+sid+";";
            ResultSet resultSet=stmt.executeQuery(song);
            while (resultSet.next()){
                sName=resultSet.getString("songName");
            }
            //insert into PlayListData(Sno,Name,Item_Name) values(?,?,?)
            String query2 ="insert into PlayListData (Sno,Name,Item_Name)values(?,?,?)";
            PreparedStatement pmt = connection.prepareStatement(query2);
            pmt.setInt(1,option);
            pmt.setString(2,name);
            pmt.setString(3,sName);
            int no2= pmt.executeUpdate();
            System.out.println("Your Song Is Successfully added to Playlist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void add_Podcasts_to_PlayList() {
        System.out.println("Select Your PlayList");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();sc.nextLine();
        String name = "";
        try {
            Statement stmt = getConnection().createStatement();
            String query = "Select  Name from PlayList  where Sno = " + option + ";";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                //PlayListName = result.getInt("Sno");
                name = result.getString("Name");
            }
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxProject", "root", "Iphone@259696");
            System.out.println("Select Podcasts PodcastId:");
            int sid = sc.nextInt();
            String sName = "";
            Statement stmt1 = getConnection().createStatement();
            String song = "Select Name from Podcast where PodcastId = "+sid+";";
            ResultSet resultSet=stmt.executeQuery(song);
            while (resultSet.next()){
                sName=resultSet.getString("Name");
            }
            String query2 ="insert into PlayListData(Sno,Name,Item_Name) values(?,?,?) ";
            PreparedStatement pmt = connection.prepareStatement(query2);
            pmt.setInt(1,option);
            pmt.setString(2,name);
            pmt.setString(3,sName);
            //pmt.setString(2,name);
            int no2= pmt.executeUpdate();
            System.out.println("Your Podcasts Is Successfully added to Playlist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void viewPlayList() throws UnsupportedAudioFileException, LineUnavailableException, IOException, JavaLayerException, InterruptedException {
        System.out.println("Select Your Option\n1) View PlayList\n2) Add Song to PlayList\n3) Add Podcasts to PlayListn\n4) Main Menu\n5) Exit");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1:Display_Playlists();DisplayplayListdata();play();break;
            case 2:Display_Playlists();select_PlayList();displaiAllSongs();play();break;
            case 3:Display_Playlists();displaiAllpodcasts();add_Podcasts_to_PlayList();viewPlayList();play();break;
            case 4:main_Menu();break;
            case 5:System.exit(0); break;
            default:System.out.println("Invalid choice");
        }
    }
    public void DisplayplayListdata(){
        ArrayList<PlayListData> data = new ArrayList<>();
        System.out.println("Enter Your PlayList Name");
        Scanner sc = new Scanner(System.in);
        String Name = sc.next();
        try {
            Statement stmt = getConnection().createStatement();
            String query = "Select SrP_Id,Sno,Name,Item_Name from PlayListdata where Name = '"+Name+"';";
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.printf("%-20s %-30s %-30s %-10s\n", "SONG\\PODCASTS_ID", "Sno", "Play_List_Name", "Song\\Podcasts");
            while (resultSet.next()){
                data.add(new PlayListData(resultSet.getInt("SrP_Id"), resultSet.getInt("Sno"), resultSet.getString("Name"), resultSet.getString("Item_Name")));
            }
            data.stream().forEach(System.out::println);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public  void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
        System.out.println("Select Your SrP_Id to Play..?");
        //System.out.print("Select Song..");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        String songName = "";
        String filePath = "C:\\Users\\Guru\\Desktop\\NIIT\\Week_7 Project\\Song_and_Podcasts\\";
        try {
            Statement stmt = getConnection().createStatement();
            String query = "select Item_Name from PlayListData where SrP_Id = '" + option + "';";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                songName = result.getString("Item_Name");
            }
            filePath = filePath + songName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleAudioPlayer.main(filePath);
        System.out.println("Came out of file");
        viewPlayList();

    }
    public void main_Menu() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        System.out.println("1) Display Songs\n2)Serch Songs By Genre\n3) DisplayPodcasts\n4) Srech Podcasts\n5) CreatePlayList\n6) Add to PlayList\n7) DisplayPlayList");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        switch (option) {
            case 1:
                display_Songs();
                optionsDisplaySongs();
                break;
            case 2:String Search=sc.next();
                serchSongByGener(Search);
                optionsDisplaySongs();
                break;
            case 3:
                displayPodcasts();
                displaiAllpodcasts();
                optionsDisplaySongs();
                //optionsDisplayPodcasts();
                break;
            case 4:System.out.println("Serch Podcasts By Name\\Host\\Episodes ");
                Search=sc.next();
                serchPodcastsBy_Host(Search);
                optionsDisplayPodcasts();
                break;
            case 5:
                System.out.println("Enter User Name:");
                String UserName = sc.next();
                createPlayList(UserName);
                Display_Playlists();viewPlayList();
                break;
            case 6:
                viewPlayList();
                Display_Playlists();
                displaiAllpodcasts();
                add_Podcasts_to_PlayList();
                break;
            case 7:
                viewPlayList();
                Display_Playlists();
                select_PlayList();
                optionsDisplayPlayList();
                break;
            case 8:System.exit(0);break;
            default:
                System.out.println("select correct option");
        }
    }
}
