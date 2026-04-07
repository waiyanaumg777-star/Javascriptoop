package model;

import java.time.LocalDate;

public class StressRecord extends HealthRecord {
    private static final long serialVersionUID = 1L;
    
    private int stressLevel;
    private String copingStrategy;
    private String stressSource;
    
    public StressRecord(String recordId, String userId, LocalDate date, int stressLevel, 
                        String copingStrategy, String stressSource) {
        super(recordId, userId, date, "Stress");
        this.stressLevel = stressLevel;
        this.copingStrategy = copingStrategy;
        this.stressSource = stressSource;
    }
    
    @Override
    public String getStatus() {
        if (stressLevel <= 3) {
            return "😊 Excellent - Low stress levels maintained";
        } else if (stressLevel <= 6) {
            return "😐 Moderate - Manageable stress levels";
        } else {
            return "😰 High stress - Immediate attention needed";
        }
    }
    
    @Override
    public String getRecommendation() {
        if (stressLevel > 7) {
            return "🧘 Seek professional counseling, practice daily meditation, and take breaks. Your mental health matters!";
        } else if (stressLevel > 4) {
            return "🌿 Practice deep breathing, take regular walks, and maintain work-life balance. Consider journaling.";
        } else {
            return "🎉 Great stress management! Keep using your coping strategies: " + copingStrategy;
        }
    }
    
    @Override
    public double getScore() {
        return Math.max(0, 100 - (stressLevel * 10));
    }
    
    @Override
    public String getSummary() {
        return String.format("🧠 Stress: %d/10 | Source: %s | Coping: %s | Score: %.0f/100", 
                            stressLevel, stressSource, copingStrategy, getScore());
    }
    
    public int getStressLevel() { return stressLevel; }
    public String getCopingStrategy() { return copingStrategy; }
    public String getStressSource() { return stressSource; }
}
