import example.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    @Test
    public void shouldSaveRoomsToFile() {

        FileManager fileManager = new FileManager();

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(9, "Standard", 5000.0, 2));
        rooms.add(new Room(10, "Deluxe", 10000.0, 3));

        fileManager.saveRooms(rooms);

        File file = new File("data/rooms.txt");

        assertTrue(file.exists());
    }

    @Test
    public void shouldSaveCorrectRoomDetailsToFile() throws IOException {

        FileManager fileManager = new FileManager();

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(9, "Standard", 5000.0, 2));
        rooms.add(new Room(10, "Deluxe", 10000.0, 3));

        fileManager.saveRooms(rooms);

        List<String> lines = Files.readAllLines(
                Path.of("data/rooms.txt")
        );

        assertEquals(2, lines.size());

        assertEquals("9,Standard,5000.0,2", lines.get(0));
        assertEquals("10,Deluxe,10000.0,3", lines.get(1));
    }

    @Test
    public void shouldLoadRoomsFromFile() throws IOException {

        FileManager fileManager = new FileManager();

        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(9, "Standard", 5000.0, 2));
        rooms.add(new Room(10, "Deluxe", 10000.0, 3));

        fileManager.saveRooms(rooms);

        ArrayList<Room> loadedRooms = fileManager.loadRooms();

        assertEquals(2, loadedRooms.size());

        assertEquals(9, loadedRooms.get(0).getRoomNumber());
        assertEquals("Standard", loadedRooms.get(0).getCategory());

        assertEquals(10, loadedRooms.get(1).getRoomNumber());
        assertEquals("Deluxe", loadedRooms.get(1).getCategory());
    }

    @Test
    public void shouldReturnEmptyListWhenRoomsFileIsEmpty() throws IOException {

        FileManager fileManager = new FileManager();

        Files.write(Path.of("data/rooms.txt"), new byte[0]);

        ArrayList<Room> rooms = fileManager.loadRooms();

        assertTrue(rooms.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenRoomsFileDoesNotExist() {

        FileManager fileManager = new FileManager();

        File file = new File("data/rooms.txt");

        file.delete();

        ArrayList<Room> rooms = fileManager.loadRooms();

        assertTrue(rooms.isEmpty());
    }

    @Test
    public void shouldSaveGuestsToFile() throws IOException {

        FileManager fileManager = new FileManager();

        ArrayList<Guest> guests = new ArrayList<>();

        guests.add(new Guest(
                5, "Favour Okaka", "favour@gmail.com", "07015705134"
        ));

        guests.add(new Guest(
                6, "John Doe", "john@gmail.com", "08012345678"
        ));

        fileManager.saveGuests(guests);

        File file = new File("data/guests.txt");

        assertTrue(file.exists());
    }

    @Test
    public void shouldSaveCorrectGuestDetailsToFile() throws IOException {

        FileManager fileManager = new FileManager();

        ArrayList<Guest> guests = new ArrayList<>();

        guests.add(new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705372"));

        guests.add(new Guest(6, "John Doe", "john@gmail.com", "08012345678"));

        fileManager.saveGuests(guests);

        List<String> lines = Files.readAllLines(Path.of("data/guests.txt"));

        assertEquals(2, lines.size());

        assertEquals("5,Favour Okaka,favour@gmail.com,07015705143", lines.get(0));

        assertEquals("6,John Doe,john@gmail.com,08012345678", lines.get(1));
    }

    @Test
    public void shouldLoadGuestsFromFile() throws IOException {

        FileManager fileManager = new FileManager();

        ArrayList<Guest> guests = new ArrayList<>();

        guests.add(new Guest(
                5, "Favour Okaka", "favour@gmail.com", "07015705143"
        ));

        guests.add(new Guest(
                6, "John Doe", "john@gmail.com", "08012345678"
        ));

        fileManager.saveGuests(guests);

        ArrayList<Guest> loadedGuests = fileManager.loadGuests();

        assertEquals(2, loadedGuests.size());

        assertEquals(5, loadedGuests.get(0).getID());
        assertEquals("Favour Okaka", loadedGuests.get(0).getFullName());
        assertEquals("favour@gmail.com", loadedGuests.get(0).getEmail());
        assertEquals("07015705143", loadedGuests.get(0).getPhoneNumber());

        assertEquals(6, loadedGuests.get(1).getID());
        assertEquals("John Doe", loadedGuests.get(1).getFullName());
    }

    @Test
    public void shouldReturnEmptyListWhenGuestsFileIsEmpty() throws IOException {

        FileManager fileManager = new FileManager();

        Files.write(Path.of("data/guests.txt"), new byte[0]);

        ArrayList<Guest> guests = fileManager.loadGuests();

        assertTrue(guests.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenGuestsFileDoesNotExist() {

        FileManager fileManager = new FileManager();

        File file = new File("data/guests.txt");
        file.delete();

        ArrayList<Guest> guests = fileManager.loadGuests();

        assertTrue(guests.isEmpty());
    }

    @Test
    public void shouldSaveReservationsToFile() throws IOException {

        FileManager fileManager = new FileManager();

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705372");

        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        fileManager.saveReservations(reservations);

        File file = new File("data/reservations.txt");

        assertTrue(file.exists());
    }

    @Test
    public void shouldSaveCorrectReservationDetailsToFile() throws IOException {

        FileManager fileManager = new FileManager();

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705372");

        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        fileManager.saveReservations(reservations);

        List<String> lines = Files.readAllLines(Path.of("data/reservations.txt"));

        assertEquals(1, lines.size());

        assertEquals("1,5,9,2026-11-20,2026-11-23,UNPAID", lines.get(0));
    }



    @Test
    public void shouldLoadReservationsFromFile() {
        FileManager fileManager = new FileManager();

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705143");
        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        fileManager.saveReservations(reservations);

        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);

        ArrayList<Reservation> loadedReservations =
                fileManager.loadReservations(guests, rooms);

        assertEquals(1, loadedReservations.size());
        assertEquals(1, loadedReservations.get(0).getReservationId());
        assertEquals(5, loadedReservations.get(0).getGuest().getID());
        assertEquals(9, loadedReservations.get(0).getRoom().getRoomNumber());
    }


    @Test
    public void shouldRestorePaymentStatusWhenLoadingReservations() {
        FileManager fileManager = new FileManager();

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705143");
        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        reservation.setPaymentStatus(PaymentStatus.PAID);

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        fileManager.saveReservations(reservations);

        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);

        ArrayList<Reservation> loadedReservations =
                fileManager.loadReservations(guests, rooms);

        assertEquals(
                PaymentStatus.PAID,
                loadedReservations.get(0).getPaymentStatus()
        );
    }


    @Test
    public void shouldReturnEmptyListWhenReservationsFileIsEmpty() throws IOException {
        FileManager fileManager = new FileManager();

        Files.write(
                Path.of("data/reservations.txt"),
                new byte[0]
        );

        ArrayList<Guest> guests = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        ArrayList<Reservation> reservations =
                fileManager.loadReservations(guests, rooms);

        assertTrue(reservations.isEmpty());
    }


    @Test
    public void shouldReturnEmptyListWhenReservationsFileDoesNotExist() {

        FileManager fileManager = new FileManager();

        File file = new File("data/reservations.txt");

        file.delete();

        ArrayList<Guest> guests = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        ArrayList<Reservation> reservations = fileManager.loadReservations(guests, rooms);

        assertTrue(reservations.isEmpty());
    }


}
