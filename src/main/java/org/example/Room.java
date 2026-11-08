package org.example;

public class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private int capacity;

    public Room(int roomNumber, String category, double pricePerNight, int capacity){
        allValidations(roomNumber,category, pricePerNight, capacity);

        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public String getCategory(){
        return category;
    }

    public double getPricePerNight(){
        return pricePerNight;
    }

    public int getCapacity(){
        return capacity;
    }

    private void validateRoomNumber(int roomNumber){
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be positive");
        }
    }

    private void validateCategory(String category){
        if(category == null || category.isBlank()){
            throw new IllegalArgumentException("Category cannot be empty");
        }
    }

    private void validatePrice(double pricePerNight){
        if(pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    private void validateCapacity(int capacity){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity must be positive");
        }
    }

    private void allValidations(int roomNumber, String category, double pricePerNight, int capacity){
        validateRoomNumber(roomNumber);
        validateCategory(category);
        validatePrice(pricePerNight);
        validateCapacity(capacity);
    }
}
