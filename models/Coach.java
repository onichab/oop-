package gettzfitnesscenter.models;

public class Coach {
    private String coachId;
    private String firstName;
    private String lastName;
    private String phone;
    private String nic;
    private String specialization;
    private double salary;
    private String hireDate;

    public Coach(String coachId, String firstName, String lastName, String phone, 
                String nic, String specialization, double salary, String hireDate) {
        this.coachId = coachId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.nic = nic;
        this.specialization = specialization;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters
    public String getCoachId() { return coachId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getNic() { return nic; }
    public String getSpecialization() { return specialization; }
    public double getSalary() { return salary; }
    public String getHireDate() { return hireDate; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setNic(String nic) { this.nic = nic; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + specialization + ")";
    }
}