package gettzfitness;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String contact;

    public Member(String id, String firstName, String lastName, int age, String gender, String contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return id + " - " + firstName + " " + lastName;
    }
}
