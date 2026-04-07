ackage model;

import java.time.LocalDate;

public class NutritionRecord extends HealthRecord {
    private static final long serialVersionUID = 1L;
    
    private int mealsPerDay;
    private double waterIntakeLiters;
    private int fruitVegServings;
    private boolean hadBreakfast;
    
    public NutritionRecord(String recordId, String userId, LocalDate date, 
                           int mealsPerDay, double waterIntakeLiters, int fruitVegServings, boolean hadBreakfast) {
        super(recordId, userId, date, "Nutrition");
        this.mealsPerDay = mealsPerDay;
        this.waterIntakeLiters = waterIntakeLiters;
        this.fruitVegServings = fruitVegServings;
        this.hadBreakfast = hadBreakfast;
    }
    
    @Override
    public String getStatus() {
        int score = 0;
        if (mealsPerDay >= 3) score++;
        if (waterIntakeLiters >= 2) score++;
        if (fruitVegServings >= 5) score++;
        if (hadBreakfast) score++;
        
        if (score == 4) {
            return "🌟 Excellent - Perfect nutrition habits!";
        } else if (score >= 3) {
            return "✅ Good - Balanced nutrition, minor improvements possible";
        } else if (score >= 2) {
            return "⚠️ Fair - Room for improvement";
        } else {
            return "🔴 Poor - Nutritional deficiencies detected";
        }
    }
    
    @Override
    public String getRecommendation() {
        StringBuilder rec = new StringBuilder("🥗 ");
        if (mealsPerDay < 3) rec.append("Eat 3 balanced meals daily. ");
        if (!hadBreakfast) rec.append("Don't skip breakfast - it's the most important meal! ");
        if (waterIntakeLiters < 2) rec.append("Drink at least 2L of water daily. ");
        if (fruitVegServings < 5) rec.append("Consume 5+ servings of fruits and vegetables. ");
        if (rec.length() <= 3) rec.append("Excellent nutrition habits! Keep maintaining!");
        return rec.toString();
    }
    
    @Override
    public double getScore() {
        double score = (mealsPerDay / 3.0) * 25;
        score += (waterIntakeLiters / 2.0) * 25;
        score += (fruitVegServings / 5.0) * 30;
        score += hadBreakfast ? 20 : 0;
        return Math.min(100, score);
    }
    
    @Override
    public String getSummary() {
        return String.format("🍎 Meals: %d | Water: %.1fL | Veg: %d | Breakfast: %s | Score: %.0f/100", 
                            mealsPerDay, waterIntakeLiters, fruitVegServings, 
                            hadBreakfast ? "✅" : "❌", getScore());
    }
    
    public int getMealsPerDay() { return mealsPerDay; }
    public double getWaterIntakeLiters() { return waterIntakeLiters; }
    public int getFruitVegServings() { return fruitVegServings; }
    public boolean getHadBreakfast() { return hadBreakfast; }
}
