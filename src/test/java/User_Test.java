import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class User_Test {
    ArrayList<Songs> list;
    User test ;
    @BeforeEach
    void setUp(){
        list = new ArrayList();
        test= new User();
    }
    @AfterEach
    void tearDown()
    {
        list = null;
        test = null;
    }
    @Test
    public void test_DisplayAll_Songs(){
        String expected ="1\t\tCKay  Love Nwan\t\tJoeBoy\t\tPOP\t\t193";
        assertEquals(expected,test.toString());
    }
}


