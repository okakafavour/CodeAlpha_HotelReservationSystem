package example;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelService {

    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public HotelService(){
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addRoom(Room room){
        if (room == null){
            throw new IllegalArgumentException("Room can not be null");
        }
        rooms.add(room);
    }

    public ArrayList<Room> getAllRooms(){
        return rooms;
    }

    public void addReservation(Reservation reservation){
        if (reservation == null) throw new IllegalArgumentException("Reservation can not be null");

        reservations.add(reservation);
    }

    public ArrayList<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        ArrayList<Room> availableRooms = new ArrayList<>();

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        for(Room room : rooms){
            boolean isAvailable = true;

            for(Reservation reservation : reservations){
                if(reservation.getRoom().getRoomNumber() == room.getRoomNumber()){
                    Reservation requestedReservation = new Reservation(9999, reservation.getGuest(), room, checkInDate, checkOutDate);
                    if(reservation.overlapsWith(requestedReservation)){
                        isAvailable = false;
                        break;
                    }
                }
            }

            if(isAvailable){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
}
