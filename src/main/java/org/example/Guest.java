package org.example;

public class Guest {

    private int guestID;
    private String guestFullName;
    private String guestEmail;
    private String guestPhoneNumber;

    public Guest(int guestID, String guestFullName, String guestEmail, String guestPhoneNumber) {
        this.guestID = guestID;
        this.guestFullName = guestFullName;
        this.guestEmail = guestEmail;
        this.guestPhoneNumber = guestPhoneNumber;
    }

    public int getGuestID() {
        return guestID;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }
}
