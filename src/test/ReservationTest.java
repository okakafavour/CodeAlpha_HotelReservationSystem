import example.Guest;
import example.Reservation;
import example.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    @Test
    public void testToCreateReservationWithCorrectDetails(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(1, guest, room, LocalDate.of(2026, 11, 06), LocalDate.of(2026, 11, 18));

        assertEquals(1, reservation.getReservationId());
        assertEquals(guest, reservation.getGuest());
        assertEquals(room, reservation.getRoom());
    }

    @Test
    public void shouldRejectInvalidReservationId(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, ()->{
            new Reservation(0, guest, room, LocalDate.of(2026,11, 15), LocalDate.of(2026, 11, 18));
        });
    }

    @Test
    public void shouldRejectNullGuest(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, ()->{
           new Reservation(1, null, room, LocalDate.of(2025, 11, 5), LocalDate.of(2025, 11, 8));
        });
    }

    @Test
    public void shouldRejectNullRoom(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, ()->{
            new Reservation(1, guest, null, LocalDate.of(2025, 11, 5), LocalDate.of(2025, 11, 8));
        });
    }

    @Test
    public void shouldRejectNullCheckInDate() {
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(1, guest, room, null, LocalDate.of(2026, 11, 18)
            );
        });
    }

    @Test
    public void shouldRejectNullCheckOutDate() {
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(1, guest, room, LocalDate.of(2026, 11, 15), null);
        });
    }

    @Test
    public void shouldRejectCheckOutBeforeCheckIn() {
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(1, guest, room, LocalDate.of(2026, 11, 18), LocalDate.of(2026, 11, 15));
        });
    }

    @Test
    public void shouldCalculateTotalReservationCost(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(1, guest, room, LocalDate.of(2026, 11, 06), LocalDate.of(2026, 11, 9));
        assertEquals(15000.0, reservation.calculateTotalCost(), 0.001);

    }

    @Test
    public void shouldRejectOverlappingReservation(){
        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");
        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation1 = new Reservation(1, guest, room, LocalDate.of(2026, 11, 06), LocalDate.of(2026, 11, 9));
        Reservation reservation2 = new Reservation(1, guest, room, LocalDate.of(2026, 11, 07), LocalDate.of(2026, 11, 10));
        assertTrue(reservation1.overlapsWith(reservation2));
    }

    @Test
    public void shouldReturnFalseWhenReservationsDoNotOverlap() {
        Guest guest = new Guest(5, "favour okaka",
                "favour@gmail.com", "07015705372");

        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation1 = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 6),
                LocalDate.of(2026, 11, 9)
        );

        Reservation reservation2 = new Reservation(
                2, guest, room,
                LocalDate.of(2026, 11, 10),
                LocalDate.of(2026, 11, 13)
        );

        assertFalse(reservation1.overlapsWith(reservation2));
    }
}
