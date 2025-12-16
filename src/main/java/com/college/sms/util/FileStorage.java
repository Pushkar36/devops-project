package com.college.sms.util;

import com.college.sms.model.Student;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * File Storage Utility Class
 * Handles reading and writing student data to file
 */
public class FileStorage {
    
    private static final String DATA_FILE = "students_data.txt";
    private static final String DELIMITER = "\\|";
    
    /**
     * Save all students to file
     */
    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                String line = String.format("%d|%s|%s|%s|%s|%s|%s",
                    student.getId(),
                    student.getRollNo(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getDob(),
                    student.getDepartment()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }
    
    /**
     * Load all students from file
     */
    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            // Create file with sample data
            initializeSampleData();
            return loadStudents();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                if (parts.length == 7) {
                    Student student = new Student();
                    student.setId(Integer.parseInt(parts[0]));
                    student.setRollNo(parts[1]);
                    student.setName(parts[2]);
                    student.setEmail(parts[3]);
                    student.setPhone(parts[4]);
                    student.setDob(LocalDate.parse(parts[5]));
                    student.setDepartment(parts[6]);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Initialize file with sample data
     */
    private static void initializeSampleData() {
        List<Student> sampleStudents = new ArrayList<>();
        
        Student s1 = new Student("CS2021001", "Rahul Sharma", "rahul.sharma@college.edu", 
                                  "9876543210", LocalDate.of(2003, 5, 15), "Computer Science");
        s1.setId(1);
        
        Student s2 = new Student("CS2021002", "Priya Patel", "priya.patel@college.edu",
                                  "9876543211", LocalDate.of(2003, 8, 22), "Computer Science");
        s2.setId(2);
        
        Student s3 = new Student("EC2021001", "Amit Kumar", "amit.kumar@college.edu",
                                  "9876543212", LocalDate.of(2003, 3, 10), "Electronics");
        s3.setId(3);
        
        Student s4 = new Student("ME2021001", "Sneha Gupta", "sneha.gupta@college.edu",
                                  "9876543213", LocalDate.of(2003, 12, 5), "Mechanical");
        s4.setId(4);
        
        sampleStudents.add(s1);
        sampleStudents.add(s2);
        sampleStudents.add(s3);
        sampleStudents.add(s4);
        
        saveStudents(sampleStudents);
        System.out.println("✅ Sample data initialized in " + DATA_FILE);
    }
    
    /**
     * Get next available ID
     */
    public static int getNextId() {
        List<Student> students = loadStudents();
        if (students.isEmpty()) {
            return 1;
        }
        return students.stream()
                      .mapToInt(Student::getId)
                      .max()
                      .orElse(0) + 1;
    }
}
