import org.example.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTest {

    @Test
    public void shouldCreateGuestWithCorrectDetails(){
        Guest guest = new Guest(1, "Favour okaka", "favour@gmail.com", "07015705372");

        assertEquals(1, guest.getGuestID());
        assertEquals("Favour okaka", guest.getGuestFullName());
        assertEquals("favour@gmail.com", guest.getGuestEmail());
        assertEquals("07015705372", guest.getGuestPhoneNumber());
    }
}
