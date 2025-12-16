package com.college.sms.model;

import java.time.LocalDate;

/**
 * Student Entity Class
 * Represents a student in the management system
 */
public class Student {
    private int id;
    private String rollNo;
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String department;
    
    // Default Constructor
    public Student() {}
    
    // Parameterized Constructor
    public Student(String rollNo, String name, String email, String phone, 
                   LocalDate dob, String department) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.department = department;
    }
    
    // Getters and Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getRollNo() { 
        return rollNo; 
    }
    
    public void setRollNo(String rollNo) { 
        this.rollNo = rollNo; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPhone() { 
        return phone; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
    
    public LocalDate getDob() { 
        return dob; 
    }
    
    public void setDob(LocalDate dob) { 
        this.dob = dob; 
    }
    
    public String getDepartment() { 
        return department; 
    }
    
    public void setDepartment(String department) { 
        this.department = department; 
    }
    
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Roll=%s, Name=%s, Dept=%s, Email=%s, Phone=%s, DOB=%s]",
                           id, rollNo, name, department, email, phone, dob);
    }
}
