
package example;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelService {

    private ArrayList<Room> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<Reservation> reservations;
    private FileManager fileManager;

    public HotelService() {
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.fileManager = new FileManager();
    }

    public HotelService(FileManager fileManager) {

        this.fileManager = fileManager;

        this.rooms = fileManager.loadRooms();
        this.guests = fileManager.loadGuests();

        this.reservations = fileManager.loadReservations(
                guests,
                rooms
        );
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservations;
    }

    public ArrayList<Guest> getAllGuests() {
        return guests;
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room can not be null");
        }
        rooms.add(room);
        fileManager.saveRooms(rooms);
    }

    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }

        reservations.add(reservation);
        fileManager.saveReservations(reservations);
    }

    public void addGuest(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }

        guests.add(guest);
        fileManager.saveGuests(guests);
    }

    public ArrayList<Room> getAvailableRooms(
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {
        ArrayList<Room> availableRooms = new ArrayList<>();

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        for (Room room : rooms) {
            boolean isAvailable = true;

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().getRoomNumber() == room.getRoomNumber()) {

                    Reservation requestedReservation = new Reservation(
                            9999,
                            reservation.getGuest(),
                            room,
                            checkInDate,
                            checkOutDate
                    );

                    if (reservation.overlapsWith(requestedReservation)) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public Reservation makeReservation(
            int reservationId,
            Guest guest,
            Room room,
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {
        boolean roomExists = false;

        for (Room hotelRoom : rooms) {
            if (hotelRoom.getRoomNumber() == room.getRoomNumber()) {
                roomExists = true;
                break;
            }
        }

        if (!roomExists) {
            throw new IllegalArgumentException("Room does not belong to this hotel");
        }

        ArrayList<Room> availableRooms = getAvailableRooms(checkInDate, checkOutDate);
        boolean isAvailable = false;

        for (Room availableRoom : availableRooms) {
            if (availableRoom.getRoomNumber() == room.getRoomNumber()) {
                isAvailable = true;
                break;
            }
        }

        if (!isAvailable) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        for (Reservation existingReservation : reservations) {
            if (existingReservation.getReservationId() == reservationId) {
                throw new IllegalArgumentException("Reservation ID already exists");
            }
        }

        Reservation reservation = new Reservation(
                reservationId, guest, room, checkInDate, checkOutDate
        );

        reservations.add(reservation);
        fileManager.saveReservations(reservations);

        return reservation;
    }

    public void cancelReservation(int reservationId) {
        boolean reservationFound = false;

        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                reservations.remove(reservation);
                reservationFound = true;
                break;
            }
        }

        if (!reservationFound) {
            throw new IllegalArgumentException("Reservation not found");
        }
        fileManager.saveReservations(reservations);
    }

    public Reservation getReservationById(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }

        throw new IllegalArgumentException("No Reservation Found!!");
    }

    public void makePayment(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                if (reservation.getPaymentStatus() == PaymentStatus.PAID) {
                    throw new IllegalArgumentException(
                            "Reservation has already been paid for"
                    );
                }

                reservation.setPaymentStatus(PaymentStatus.PAID);
                fileManager.saveReservations(reservations);
                return;
            }
        }

        throw new IllegalArgumentException("Reservation not found");

    }
}
