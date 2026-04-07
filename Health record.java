package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class HealthRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String recordId;
    protected String userId;
    protected LocalDate date;
    protected String type;
    
    public HealthRecord(String recordId, String userId, LocalDate date, String type) {
        this.recordId = recordId;
        this.userId = userId;
        this.date = date;
        this.type = type;
    }
    
    // Abstract methods
    public abstract String getStatus();
    public abstract String getRecommendation();
    public abstract double getScore();
    public abstract String getSummary();
    
    // Getters
    public String getRecordId() { return recordId; }
    public String getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public String getType() { return type; }
    
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return date.format(formatter);
    }
}
