package gettzfitnesscenter.models;

public class Member {
    private String memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private double height;
    private double weight;
    private String joinDate;
    private String membershipType;

    public Member(String memberId, String firstName, String lastName, String email, 
                 String phone, double height, double weight, String joinDate, String membershipType) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.height = height;
        this.weight = weight;
        this.joinDate = joinDate;
        this.membershipType = membershipType;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getJoinDate() { return joinDate; }
    public String getMembershipType() { return membershipType; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setHeight(double height) { this.height = height; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + memberId + ")";
    }
}