import org.example.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    public void shouldCreateRoomWithCorrectDetails(){
        Room room = new Room(101, "Standard", 25000.0, 2);

        assertEquals(101, room.getRoomNumber());
        assertEquals("Standard", room.getCategory());
        assertEquals(25000.0, room.getPricePerNight());
        assertEquals(2, room.getCapacity());
    }

    @Test
    public void shouldRejectInvalidRoomNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Room(0, "Standard", 25000.0, 2);
        });
    }

    @Test
    public void shouldRejectEmptyCategory(){
        assertThrows(IllegalArgumentException.class, ()-> {
            new Room(101, "", 2500.0, 2);
        });
    }

    @Test
    public void shouldRejectInvalidPrice(){
        assertThrows(IllegalArgumentException.class, ()-> {
            new Room(101, "Standard", 0, 2 );
        });
    }

    @Test
    public void shouldRejectInvalidCapacity(){
        assertThrows(IllegalArgumentException.class, ()-> {
            new Room(101, "Standard", 2500.0, 0);
        });
    }

    @Test
    public void shouldRejectNegativeRoomNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Room(-101, "Standard", 25000.0, 2);
        });
    }

    @Test
    public void shouldRejectCategoryContainingOnlySpaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Room(101, "   ", 25000.0, 2);
        });
    }
}
