import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTest {

    @Test
    public void shouldCreateGuestWithCorrectDetails(){
        Guest guest = new Guest(1, "Favour okaka", "favour@gmail.com", "07015705372");

        assertEquals(1, getGuestId());
        assertEquals("Favour okaka", getGuestFullName());
        assertEquals("favour@gmail.com", getGuestEmail);
        assertEquals("07015705372", getGuestPhoneNumber);
    }
}
