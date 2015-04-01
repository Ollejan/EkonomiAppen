package se.mah.ad0025.inluppg1;


/**
 * Klass som har hand om användarnas information.
 * @author Jonas Dahlström 5/10-14
 */
public class User {
    private String firstName, lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
