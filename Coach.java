package gettzfitness;

public class Coach {
    private String id;
    private String firstName;
    private String lastName;
    private String speciality;
    private String contact;

    public Coach(String id, String firstName, String lastName, String speciality, String contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getSpeciality() { return speciality; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return id + " - " + firstName + " " + lastName;
    }
}
