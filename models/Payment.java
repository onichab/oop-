package gettzfitnesscenter.models;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private String type;
    private String referenceId;
    private double amount;
    private LocalDate date;
    private String description;

    public Payment(String paymentId, String type, String referenceId, double amount, LocalDate date, String description) {
        this.paymentId = paymentId;
        this.type = type;
        this.referenceId = referenceId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public String getPaymentId() { return paymentId; }
    public String getType() { return type; }
    public String getReferenceId() { return referenceId; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
}