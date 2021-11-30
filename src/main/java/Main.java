import javazoom.jl.decoder.JavaLayerException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] guru) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, JavaLayerException {
        Main ma = new Main();
        Scanner sc = new Scanner(System.in);
        User user = new User();
        boolean isUserValid = user.authenticateUser();
        System.out.println("isUserValid" + isUserValid);
        if(isUserValid) {
            ///List<Songs> sortt=
            int option = optionsMain();
            switch(option) {
                case 1: user.displaySongs();user.displaiAllSongs();user.optionsDisplaySongs();break;
                case 2: //System.out.println("Enter Your Genre Name");String genre = sc.next();
                    user.serchSongByGener();break;
                case 3:user.displayPodcasts();user.displaiAllpodcasts();user.optionsDisplayPodcasts();break;
                case 4:user.serchPodcastsBy_Host();break;
                case 5:System.out.println("Enter User Name:");String UserName = sc.next();user.createPlayList(UserName);break;
                default: System.out.println("select correct option");
            }
        }
        System.out.println("isUserValid" + isUserValid);
    }
    private static int optionsMain() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1) Display Songs\n2)Serch Songs By Genre\n3) DisplayPodcasts\n4) Srech Podcasts\n5) CreatePlayList\n6) DisplayPlayList");
        int option = sc.nextInt();
        return option;
    }
}
