package example;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileManager {


    public void saveRooms(ArrayList<Room> rooms) {

        try (FileWriter writer = new FileWriter("data/rooms.txt")) {

            for (Room room : rooms) {

                writer.write(
                        room.getRoomNumber() + "," +
                                room.getCategory() + "," +
                                room.getPricePerNight() + "," +
                                room.getCapacity() + "\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save rooms", e);
        }
    }

    public ArrayList<Room> loadRooms() {

        ArrayList<Room> rooms = new ArrayList<>();

        File file = new File("data/rooms.txt");

        if (!file.exists()) {
            return rooms;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] roomDetails = line.split(",");

                int roomNumber = Integer.parseInt(roomDetails[0]);
                String category = roomDetails[1];
                double pricePerNight = Double.parseDouble(roomDetails[2]);
                int capacity = Integer.parseInt(roomDetails[3]);

                Room room = new Room(
                        roomNumber,
                        category,
                        pricePerNight,
                        capacity
                );

                rooms.add(room);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load rooms", e);
        }

        return rooms;
    }

    public void saveGuests(ArrayList<Guest> guests) {

        try (FileWriter writer = new FileWriter("data/guests.txt")) {

            for (Guest guest : guests) {

                writer.write(
                        guest.getID() + "," +
                                guest.getFullName() + "," +
                                guest.getEmail() + "," +
                                guest.getPhoneNumber() + "\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save guests", e);
        }
    }

    public ArrayList<Guest> loadGuests() {

        ArrayList<Guest> guests = new ArrayList<>();

        File file = new File("data/guests.txt");

        if (!file.exists()) {
            return guests;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] guestDetails = line.split(",");

                int guestId = Integer.parseInt(guestDetails[0]);
                String fullName = guestDetails[1];
                String email = guestDetails[2];
                String phoneNumber = guestDetails[3];

                Guest guest = new Guest(
                        guestId,
                        fullName,
                        email,
                        phoneNumber
                );

                guests.add(guest);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load guests", e);
        }

        return guests;
    }

    public void saveReservations(ArrayList<Reservation> reservations) {

        try (FileWriter writer = new FileWriter("data/reservations.txt")) {

            for (Reservation reservation : reservations) {

                writer.write(
                        reservation.getReservationId() + "," +
                                reservation.getGuest().getID() + "," +
                                reservation.getRoom().getRoomNumber() + "," +
                                reservation.getCheckInDate() + "," +
                                reservation.getCheckOutDate() + "," +
                                reservation.getPaymentStatus() + "\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save reservations", e);
        }
    }


    public ArrayList<Reservation> loadReservations(
            ArrayList<Guest> guests,
            ArrayList<Room> rooms
    ) {

        ArrayList<Reservation> reservations = new ArrayList<>();

        File file = new File("data/reservations.txt");

        if (!file.exists()) {
            return reservations;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] reservationDetails = line.split(",");

                int reservationId = Integer.parseInt(reservationDetails[0]);
                int guestId = Integer.parseInt(reservationDetails[1]);
                int roomNumber = Integer.parseInt(reservationDetails[2]);

                LocalDate checkInDate = LocalDate.parse(reservationDetails[3]);
                LocalDate checkOutDate = LocalDate.parse(reservationDetails[4]);

                PaymentStatus paymentStatus = PaymentStatus.valueOf(reservationDetails[5]);

                Guest guest = null;
                Room room = null;


                for (Guest existingGuest : guests) {
                    if (existingGuest.getID() == guestId) {
                        guest = existingGuest;
                        break;
                    }
                }


                for (Room existingRoom : rooms) {
                    if (existingRoom.getRoomNumber() == roomNumber) {
                        room = existingRoom;
                        break;
                    }
                }

                if (guest == null || room == null) {
                    throw new IllegalArgumentException(
                            "Guest or Room not found for reservation ID: " + reservationId
                    );
                }

                Reservation reservation = new Reservation(
                        reservationId,
                        guest,
                        room,
                        checkInDate,
                        checkOutDate
                );

                reservation.setPaymentStatus(paymentStatus);
                reservations.add(reservation);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load reservations", e);
        }

        return reservations;
    }


}