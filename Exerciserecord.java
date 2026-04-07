package model;

import java.time.LocalDate;

public class ExerciseRecord extends HealthRecord {
    private static final long serialVersionUID = 1L;
    
    private String exerciseType;
    private int durationMinutes;
    private int intensity;
    private int caloriesBurned;
    
    public ExerciseRecord(String recordId, String userId, LocalDate date, String exerciseType, 
                          int durationMinutes, int intensity) {
        super(recordId, userId, date, "Exercise");
        this.exerciseType = exerciseType;
        this.durationMinutes = durationMinutes;
        this.intensity = intensity;
        this.caloriesBurned = calculateCalories(durationMinutes, intensity);
    }
    
    private int calculateCalories(int minutes, int intensity) {
        // Rough estimate: 5-15 calories per minute based on intensity
        int baseCalories = minutes * 8;
        return baseCalories + (intensity - 3) * 15;
    }
    
    @Override
    public String getStatus() {
        if (durationMinutes >= 30 && intensity >= 4) {
            return "🏆 Excellent - Meeting and exceeding recommendations!";
        } else if (durationMinutes >= 30 && intensity >= 3) {
            return "✅ Good - Meeting WHO recommendations!";
        } else if (durationMinutes >= 20) {
            return "⚠️ Fair - Good effort, try to increase to 30 minutes";
        } else {
            return "🔴 Insufficient - Need more physical activity";
        }
    }
    
    @Override
    public String getRecommendation() {
        if (durationMinutes < 30) {
            return "🏃 Aim for at least 30 minutes of moderate exercise daily. Try brisk walking, jogging, or cycling.";
        } else if (intensity < 3) {
            return "💪 Increase intensity for better cardiovascular benefits. Try interval training or faster pace.";
        } else {
            return "🎯 Excellent! Mix different exercises for overall fitness. Try strength training twice a week.";
        }
    }
    
    @Override
    public double getScore() {
        double score = (durationMinutes / 60.0) * 100;
        score += intensity * 8;
        return Math.min(100, score);
    }
    
    @Override
    public String getSummary() {
        return String.format("🏃 %s: %d min | Intensity: %d/5 | 🔥 %d cal | Score: %.0f/100", 
                            exerciseType, durationMinutes, intensity, caloriesBurned, getScore());
    }
    
    public String getExerciseType() { return exerciseType; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getIntensity() { return intensity; }
    public int getCaloriesBurned() { return caloriesBurned; }
}
