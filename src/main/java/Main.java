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

        System.out.println("Wellcome to Juke_Box2" +
                "");
        while (true){
            System.out.println("Enter Your Option : \n1.New Customer\n2.Existing Customer ");
            int choice=sc.nextInt();
            if(choice==1){
                user.New_User();
            }
            else if(choice==2){
                boolean isUserValid = user.authenticateUser();
                System.out.println("isUserValid" + isUserValid);
            if(isUserValid) {
                ///List<Songs> sortt=
                int option = optionsMain();
                switch (option) {
                    case 1:
                        user.displaySongs();
                        user.displaiAllSongs();
                        user.optionsDisplaySongs();
                        break;
                    case 2:
                        user.serchSongByGener();
                        user.optionsDisplaySongs();
                        break;
                    case 3:
                        user.displayPodcasts();
                        user.displaiAllpodcasts();
                        user.optionsDisplayPodcasts();
                        break;
                    case 4:
                        user.serchPodcastsBy_Host();
                        user.optionsDisplayPodcasts();
                        break;
                    case 5:
                        System.out.println("Enter User Name:");
                        String UserName = sc.next();
                        user.createPlayList(UserName);
                        user.Display_Playlists();user.viewPlayList();
                        break;
                    case 6:
                        user.viewPlayList();
                        user.Display_Playlists();
                        user.displaiAllpodcasts();
                        user.add_Podcasts_to_PlayList();
                        break;
                    case 7:
                        user.viewPlayList();
                        user.Display_Playlists();
                        user.select_PlayList();
                        user.optionsDisplayPlayList();
                        break;
                    default:
                        System.out.println("select correct option");
                }
            }
            }
        }

    }
    public static int optionsMain() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1) Display Songs\n2)Serch Songs By Genre\n3) DisplayPodcasts\n4) Srech Podcasts\n5) CreatePlayList\n6) Add to PlayList\n7) DisplayPlayList");
        int option = sc.nextInt();

        return option;
    }
}
