import example.Guest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    @Test
    public void shouldCreateGuestWithCorrectDetails(){
        Guest guest = new Guest(1, "Favour okaka", "favour@gmail.com", "07015705372");

        assertEquals(1, guest.getID());
        assertEquals("Favour okaka", guest.getFullName());
        assertEquals("favour@gmail.com", guest.getEmail());
        assertEquals("07015705372", guest.getPhoneNumber());
    }

    @Test
    public void  shouldRejectEmptyGuestName(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Guest(1, " ", "favour@gmail.com", "07015705372");
        });
    }

    @Test
    public void shouldRejectInvalidEmail(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Guest(1, "favour okaka", "favourgmail.com", "07015705372");
        });
    }

    @Test
    public void shouldRejectEmptyPhoneNumber(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Guest(1, "favour okaka", "favourgmail.com", "");
        });
    }
}

