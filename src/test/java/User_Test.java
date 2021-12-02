import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class User_Test {
    ArrayList<Songs> list;
    ArrayList<Songs> list2 = new ArrayList<>();
    ArrayList<Podcasts> list3 =new ArrayList<>();
    User test ;
    Songs song ;
    @BeforeEach
    void setUp(){
        list = new ArrayList();
        test= new User();
        song = new Songs();
    }
    @AfterEach
    void tearDown()
    {
        list = null;
        test = null;
    }
    @Test
    public void test_DisplayAll_Songs(){
        list=test.displaySongs();
        assertEquals(7,list.size());
        //assertFalse(list.isEmpty());

    }
    @Test
    public void test_serchSongByGener(){
        list2=test.serchSongByGener("POP");
        //Songs s=test.serchSongByGener("Pop");
        assertEquals("POP",list2.get(0).getGenre());
    }
    @Test
    public void test_serchSongByArtist(){
        list2=test.serchSongByGener("JoeBoy");
        assertEquals("JoeBoy",list2.get(0).getArtist());
    }
    @Test
    public void test_displayPodcasts(){
        list3=test.displayPodcasts();
        assertEquals(2,list3.size());
    }
    @Test
    public void test_serchPodcastsBy_Host(){
        list3=test.serchPodcastsBy_Host("Apple");
        assertEquals("Apple",list3.get(0).getHost());
    }
    @Test
    public void test_Display_Playlists(){

    }
}


