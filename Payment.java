package gettzfitness;

public class Payment {
    private String id;
    private String forId; // member or coach id
    private double amount;
    private String type; // MEMBER or COACH
    private String date;

    public Payment(String id, String forId, double amount, String type, String date) {
        this.id = id;
        this.forId = forId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public String getId() { return id; }
    public String getForId() { return forId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return id + " - " + type + " " + forId + " : " + amount;
    }
}
