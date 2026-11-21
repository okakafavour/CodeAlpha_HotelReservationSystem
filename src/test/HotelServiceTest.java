import example.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void shouldSuccessfullyMakeReservation() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        hotelService.addRoom(room);

        Reservation reservation = hotelService.makeReservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        assertNotNull(reservation);
        assertEquals(1, reservation.getReservationId());
        assertEquals(9, reservation.getRoom().getRoomNumber());
        assertEquals(1, hotelService.getAllReservations().size());
    }

    @Test
    public void shouldThrowExceptionWhenRoomIsAlreadyBooked() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        hotelService.addRoom(room);


        hotelService.makeReservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            hotelService.makeReservation(
                    2,
                    guest,
                    room,
                    LocalDate.of(2026, 11, 21),
                    LocalDate.of(2026, 11, 24)
            );
        });
    }


    @Test
    public void shouldThrowExceptionWhenRoomDoesNotBelongToHotel() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);


        assertThrows(IllegalArgumentException.class, () -> {
            hotelService.makeReservation(
                    1,
                    guest,
                    room,
                    LocalDate.of(2026, 11, 20),
                    LocalDate.of(2026, 11, 23)
            );
        });
    }

    @Test
    public void shouldThrowExceptionWhenReservationIdAlreadyExists() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room1 = new Room(9, "Standard", 5000.0, 2);
        Room room2 = new Room(10, "Deluxe", 8000.0, 3);

        hotelService.addRoom(room1);
        hotelService.addRoom(room2);

        hotelService.makeReservation(
                1,
                guest,
                room1,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            hotelService.makeReservation(
                    1,
                    guest,
                    room2,
                    LocalDate.of(2026, 11, 20),
                    LocalDate.of(2026, 11, 23)
            );
        });
    }

    @Test
    public void shouldSuccessfullyCancelReservation() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        hotelService.addRoom(room);

        hotelService.makeReservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        hotelService.cancelReservation(1);

        assertTrue(hotelService.getAllReservations().isEmpty());
    }

    @Test
    public void shouldReturnReservationById() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        hotelService.addRoom(room);

        Reservation reservation = hotelService.makeReservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        Reservation result = hotelService.getReservationById(1);

        assertNotNull(result);
        assertEquals(1, result.getReservationId());
        assertEquals(9, result.getRoom().getRoomNumber());
    }

    @Test
    public void shouldReturnAllReservations() {

        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room1 = new Room(9, "Standard", 5000.0, 2);
        Room room2 = new Room(10, "Deluxe", 8000.0, 3);

        hotelService.addRoom(room1);
        hotelService.addRoom(room2);

        hotelService.makeReservation(
                1, guest, room1,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        hotelService.makeReservation(
                2, guest, room2,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        assertEquals(2, hotelService.getAllReservations().size());
    }

    @Test
    public void shouldHaveUnpaidStatusWhenReservationIsCreated() {

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        assertEquals(PaymentStatus.UNPAID, reservation.getPaymentStatus());
    }

    @Test
    public void shouldSuccessfullyMakePayment(){

        HotelService hotelService = new HotelService();
        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);
        hotelService.addRoom(room);

        Reservation reservation = hotelService.makeReservation(1,
                guest,
                room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );
        hotelService.makePayment(1);

        assertEquals(PaymentStatus.PAID, reservation.getPaymentStatus());
    }

    @Test
    public void shouldThrowExceptionWhenReservationIsAlreadyPaid() {
        // Arrange
        HotelService hotelService = new HotelService();

        Guest guest = new Guest(
                5, "favour okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);
        hotelService.addRoom(room);

        Reservation reservation = hotelService.makeReservation(
                1, guest, room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        // Act
        hotelService.makePayment(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                hotelService.makePayment(1)
        );
    }

    @Test
    public void shouldThrowExceptionWhenReservationDoesNotExist() {
        HotelService hotelService = new HotelService();

        assertThrows(IllegalArgumentException.class, () ->
                hotelService.makePayment(100)
        );
    }


    @Test
    public void shouldLoadExistingDataWhenHotelServiceIsCreated() {

        FileManager fileManager = new FileManager();

        Guest guest = new Guest(
                5, "Favour Okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(9, "Standard", 5000.0, 2);

        Reservation reservation = new Reservation(
                1, guest, room,
                LocalDate.of(2026, 11, 20),
                LocalDate.of(2026, 11, 23)
        );

        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);

        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        fileManager.saveGuests(guests);
        fileManager.saveRooms(rooms);
        fileManager.saveReservations(reservations);

        HotelService hotelService = new HotelService(fileManager);

        assertEquals(1, hotelService.getAllRooms().size());
        assertEquals(1, hotelService.getAllReservations().size());
        assertEquals(9, hotelService.getAllReservations().get(0).getRoom().getRoomNumber());
    }

    @Test
    public void shouldSaveRoomWhenRoomIsAdded() {
        FileManager fileManager = new FileManager();
        fileManager.saveRooms(new ArrayList<>());
        fileManager.saveGuests(new ArrayList<>());
        fileManager.saveReservations(new ArrayList<>());
        HotelService hotelService = new HotelService(fileManager);

        Room room = new Room(20, "Deluxe", 10000.0, 3);

        hotelService.addRoom(room);

        ArrayList<Room> savedRooms = fileManager.loadRooms();

        assertEquals(1, savedRooms.size());
        assertEquals(20, savedRooms.get(0).getRoomNumber());
    }

    @Test
    public void shouldSaveGuestWhenGuestIsAdded(){
        FileManager fileManager = new FileManager();
        fileManager.saveRooms(new ArrayList<>());
        fileManager.saveGuests(new ArrayList<>());
        fileManager.saveReservations(new ArrayList<>());
        HotelService hotelService = new HotelService(fileManager);

        Guest guest = new Guest(12, "john doe", "john14@gmail.com", "09876543212");
        hotelService.addGuest(guest);

        ArrayList<Guest> savedGuests = fileManager.loadGuests();

        assertEquals(1, savedGuests.size());
        assertEquals(12, savedGuests.get(0).getID());
    }

    @Test
    public void shouldSaveReservationWhenReservationIsMade() {
        FileManager fileManager = new FileManager();
        fileManager.saveRooms(new ArrayList<>());
        fileManager.saveGuests(new ArrayList<>());
        fileManager.saveReservations(new ArrayList<>());

        HotelService hotelService = new HotelService(fileManager);

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705372");

        Room room = new Room(20, "Deluxe", 10000.0, 3);

        hotelService.addGuest(guest);
        hotelService.addRoom(room);

        hotelService.makeReservation(
                1,
                guest,
                room,
                LocalDate.of(2026, 11, 25),
                LocalDate.of(2026, 11, 28)
        );

        ArrayList<Reservation> savedReservations =
                fileManager.loadReservations(
                        fileManager.loadGuests(),
                        fileManager.loadRooms()
                );

        assertEquals(1, savedReservations.size());
        assertEquals(1, savedReservations.get(0).getReservationId());
    }

    @Test
    public void shouldSaveUpdatedReservationsWhenReservationIsCancelled() {
        FileManager fileManager = new FileManager();

        fileManager.saveRooms(new ArrayList<>());
        fileManager.saveGuests(new ArrayList<>());
        fileManager.saveReservations(new ArrayList<>());

        HotelService hotelService = new HotelService(fileManager);

        Guest guest = new Guest(
                5, "Favour Okaka", "favour@gmail.com", "07015705372"
        );

        Room room = new Room(20, "Deluxe", 10000.0, 3);

        hotelService.addGuest(guest);
        hotelService.addRoom(room);

        hotelService.makeReservation(
                1, guest, room,
                LocalDate.of(2026, 11, 25),
                LocalDate.of(2026, 11, 28)
        );

        hotelService.cancelReservation(1);

        ArrayList<Reservation> savedReservations =
                fileManager.loadReservations(
                        fileManager.loadGuests(),
                        fileManager.loadRooms()
                );

        assertEquals(0, savedReservations.size());
    }

    @Test
    public void shouldSavePaymentStatusWhenPaymentIsMade() {
        FileManager fileManager = new FileManager();

        fileManager.saveRooms(new ArrayList<>());
        fileManager.saveGuests(new ArrayList<>());
        fileManager.saveReservations(new ArrayList<>());

        HotelService hotelService = new HotelService(fileManager);

        Guest guest = new Guest(5, "Favour Okaka", "favour@gmail.com", "07015705372");

        Room room = new Room(20, "Deluxe", 10000.0, 3);

        hotelService.addGuest(guest);
        hotelService.addRoom(room);

        hotelService.makeReservation(
                1, guest, room,
                LocalDate.of(2026, 11, 25),
                LocalDate.of(2026, 11, 28)
        );

        hotelService.makePayment(1);

        ArrayList<Reservation> savedReservations =
                fileManager.loadReservations(
                        fileManager.loadGuests(),
                        fileManager.loadRooms()
                );

        assertEquals(
                PaymentStatus.PAID,
                savedReservations.get(0).getPaymentStatus()
        );
    }

}