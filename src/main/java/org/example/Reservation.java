package example;

import java.time.LocalDate;

public class Reservation {

    private int reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(int reservationId, Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        validateReservationId(reservationId);
        validateGuest(guest);
        validateRoom(room);
        validateCheckInDate(checkInDate);
        validateCheckOutDate(checkOutDate, checkInDate);

        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    private void validateReservationId(int reservationId){
        if(reservationId <= 0) {
            throw new IllegalArgumentException("Invalid Reservation Id");
        }
    }

    private void validateGuest(Guest guest){
        if(guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
    }

    private void validateRoom(Room room){
        if(room == null){
            throw new IllegalArgumentException("Room cannot be null");
        }
    }

    private void validateCheckInDate(LocalDate checkInDate){
        if(checkInDate == null) {
            throw new IllegalArgumentException("CheckIn date cannot be null");
        }
    }

    private void validateCheckOutDate(LocalDate checkOutDate, LocalDate checkInDate){
        if(checkOutDate == null) {
            throw new IllegalArgumentException("CheckIn date cannot be null");
        }

        if(!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check out date cannot be null");
        }
    }

    public double calculateTotalCost() {

        LocalDate currentDate = checkInDate;
        int nights = 0;

        while (currentDate.isBefore(checkOutDate)) {
            nights++;
            currentDate = currentDate.plusDays(1);
        }

        double pricePerNight = room.getPricePerNight();

        return nights * pricePerNight;
    }

    public boolean overlapsWith(Reservation other) {
        if(other == null) throw new IllegalArgumentException("Reservation cannot be null");

        return this.checkInDate.isBefore(other.checkOutDate) && other.checkInDate.isBefore(this.checkOutDate);
    }
}
