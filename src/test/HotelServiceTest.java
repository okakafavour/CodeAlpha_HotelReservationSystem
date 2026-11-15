import example.Guest;
import example.HotelService;
import example.Reservation;
import example.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HotelServiceTest {

    @Test
    public void shouldAddRoomToHotel() {
        HotelService hotelService = new HotelService();
        Room room = new Room(9, "Standard", 500.0, 2);

        hotelService.addRoom(room);
        assertEquals(1, hotelService.getAllRooms().size());
    }

    @Test
    public void shouldReturnOnlyAvailableRooms() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");

        Room room1 = new Room(9, "Standard", 5000.0, 2);
        Room room2 = new Room(10, "Deluxe", 8000.0, 3);

        hotelService.addRoom(room1);
        hotelService.addRoom(room2);

        Reservation reservation = new Reservation(
                1, guest, room1,
                LocalDate.of(2026, 11, 6),
                LocalDate.of(2026, 11, 9)
        );

        hotelService.addReservation(reservation);
    }


    @Test
    public void shouldReturnAvailableRooms() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(5, "favour okaka", "favour@gmail.com", "07015705372");

        Room room1 = new Room(9, "Standard", 5000.0, 2);
        Room room2 = new Room(10, "Deluxe", 8000.0, 3);

        hotelService.addRoom(room1);
        hotelService.addRoom(room2);

        Reservation reservation = new Reservation(
                1, guest, room1,
                LocalDate.of(2026, 11, 6),
                LocalDate.of(2026, 11, 9)
        );

        hotelService.addReservation(reservation);

        ArrayList<Room> availableRooms = hotelService.getAvailableRooms(LocalDate.of(2026, 11, 7),
                LocalDate.of(2026, 11, 10));

        assertEquals(1, availableRooms.size());
        assertEquals(10, availableRooms.get(0).getRoomNumber());
    }

    @Test
    public void shouldReturnRoomWhenExistingReservationDoesNotOverlap() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        hotelService.addRoom(room);

        Reservation reservation = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 6),
                LocalDate.of(2026, 11, 9)
        );

        hotelService.addReservation(reservation);

        ArrayList<Room> availableRooms = hotelService.getAvailableRooms(LocalDate.of(2026, 11, 9),
                LocalDate.of(2026, 11, 12));

        assertEquals(1, availableRooms.size());
        assertEquals(9, availableRooms.get(0).getRoomNumber());
    }


    @Test
    public void shouldReturnEmptyListWhenAllRoomsAreBooked() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);
        Room room2 = new Room(10, "Deluxe", 8000.0, 3);
        hotelService.addRoom(room2);

        hotelService.addRoom(room);

        Reservation reservation = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 6),
                LocalDate.of(2026, 11, 9)
        );

        Reservation reservation2 = new Reservation(
                2, guest, room2,
                LocalDate.of(2026, 11, 7),
                LocalDate.of(2026, 11, 10)
        );

        hotelService.addReservation(reservation2);

        hotelService.addReservation(reservation);

        ArrayList<Room> availableRooms = hotelService.getAvailableRooms(LocalDate.of(2026, 11, 8),
                LocalDate.of(2026, 11, 9));

        assertEquals(0, availableRooms.size());
//        assertEquals(9, availableRooms.get(0).getRoomNumber());
    }

    @Test
    public void shouldThrowExceptionWhenCheckInDateIsNull() {

        HotelService hotelService = new HotelService();

        assertThrows(IllegalArgumentException.class, () ->
                hotelService.getAvailableRooms(
                        null,
                        LocalDate.of(2026, 11, 10)
                )
        );
    }

    @Test
    public void shouldThrowExceptionWhenCheckOutDateIsBeforeCheckInDate() {

        HotelService hotelService = new HotelService();

        assertThrows(IllegalArgumentException.class, () ->
                hotelService.getAvailableRooms(
                        LocalDate.of(2026, 11, 15),
                        LocalDate.of(2026, 11, 10)
                )
        );
    }


}