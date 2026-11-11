package example;

public class Guest {

    private int Id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public Guest(int Id, String fullName, String email, String phoneNumber) {
        allValidations(Id, fullName, email, phoneNumber);

        this.Id = Id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return Id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private void validateFullName(String fullName){
        if(fullName == null || fullName.isBlank()) throw new IllegalArgumentException("FullName cannot be empty");
    }

    private void validateEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        if (!phoneNumber.matches("^\\+?[0-9]{7,15}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    private void validateGuestId(int Id) {
        if (Id <= 0) {
            throw new IllegalArgumentException("Guest ID must be positive");
        }
    }

    private void allValidations(int id, String fullName, String email, String phoneNumber){
        validateGuestId(id);
        validateEmail(email);
        validateFullName(fullName);
        validatePhoneNumber(phoneNumber);
    }
}
