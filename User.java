package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String fullName;
    private String studentId;
    private String email;
    private int age;
    private String gender;
    private LocalDateTime registrationDate;
    
    public User(String userId, String fullName, String studentId, String email, int age, String gender) {
        this.userId = userId;
        this.fullName = fullName;
        this.studentId = studentId;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.registrationDate = LocalDateTime.now();
    }
    
    // Getters
    public String getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getStudentId() { return studentId; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    
    // Setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    
    public String getFormattedRegistrationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return registrationDate.format(formatter);
    }
    
    @Override
    public String toString() {
        return fullName + " (" + studentId + ")";
    }
}
