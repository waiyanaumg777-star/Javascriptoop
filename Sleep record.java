package model;

import java.time.LocalDate;

public class SleepRecord extends HealthRecord {
    private static final long serialVersionUID = 1L;
    
    private double hoursSlept;
    private int sleepQuality;
    
    public SleepRecord(String recordId, String userId, LocalDate date, double hoursSlept, int sleepQuality) {
        super(recordId, userId, date, "Sleep");
        this.hoursSlept = hoursSlept;
        this.sleepQuality = sleepQuality;
    }
    
    @Override
    public String getStatus() {
        if (hoursSlept >= 7 && hoursSlept <= 9 && sleepQuality >= 4) {
            return "🌟 Excellent - Optimal sleep";
        } else if (hoursSlept >= 7 && hoursSlept <= 9) {
            return "✅ Good - Good duration, work on quality";
        } else if (hoursSlept >= 6 && hoursSlept < 7) {
            return "⚠️ Fair - Could use more sleep";
        } else if (hoursSlept > 9) {
            return "⚠️ Excessive sleep detected";
        } else {
            return "🔴 Poor - Sleep deprivation risk";
        }
    }
    
    @Override
    public String getRecommendation() {
        if (hoursSlept < 7) {
            return "🛌 Aim for 7-9 hours of sleep. Try to sleep earlier and maintain consistent schedule. Avoid screens 1 hour before bed.";
        } else if (hoursSlept > 9) {
            return "🏥 Excessive sleep may indicate health issues. Consider consulting a doctor if this persists.";
        } else if (sleepQuality < 4) {
            return "😴 Improve sleep quality: dark room, cool temperature, no caffeine before bed, and relaxation techniques.";
        } else {
            return "🎉 Great job! Keep maintaining these healthy sleep habits!";
        }
    }
    
    @Override
    public double getScore() {
        double score = 0;
        if (hoursSlept >= 7 && hoursSlept <= 9) {
            score = 100;
        } else if (hoursSlept >= 6) {
            score = 70;
        } else if (hoursSlept >= 5) {
            score = 40;
        } else {
            score = 20;
        }
        
        // Adjust by quality (1-5 scale)
        score += (sleepQuality - 3) * 8;
        return Math.max(0, Math.min(100, score));
    }
    
    @Override
    public String getSummary() {
        return String.format("💤 Sleep: %.1f hours | Quality: %d/5 | Score: %.0f/100", 
                            hoursSlept, sleepQuality, getScore());
    }
    
    public double getHoursSlept() { return hoursSlept; }
    public int getSleepQuality() { return sleepQuality; }
}
