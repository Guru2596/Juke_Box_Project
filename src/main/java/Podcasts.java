import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Podcasts {
    private int PodcastId;
    private String Name;
    private String Host;
    private String Episodes;
    private String users_Id;
    private static Connection connection;

    public Podcasts(int podcastId, String name, String host, String episodes, String users_Id) {
        PodcastId = podcastId;
        Name = name;
        Host = host;
        Episodes = episodes;
        this.users_Id = users_Id;
    }
    public Podcasts() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxProject", "root", "Iphone@259696");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPodcastId() {
        return PodcastId;
    }

    public String getName() {
        return Name;
    }

    public String getHost() {
        return Host;
    }

    public String getEpisodes() {
        return Episodes;
    }

    public String getUsers_Id() {
        return users_Id;
    }

    public static Connection getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        return PodcastId+"\t\t"+Name+"\t\t"+Host+"\t\t"+Episodes+"\t\t"+users_Id;
    }
}
