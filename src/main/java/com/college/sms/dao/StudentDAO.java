package com.college.sms.dao;

import com.college.sms.model.Student;
import com.college.sms.util.FileStorage;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student Entity
 * Handles all file storage operations for students
 */
public class StudentDAO {
    
    /**
     * Add a new student to the file
     * @param student Student object to add
     * @return true if successful, false otherwise
     */
    public boolean addStudent(Student student) {
        try {
            List<Student> students = FileStorage.loadStudents();
            
            // Check for duplicate roll number
            for (Student s : students) {
                if (s.getRollNo().equals(student.getRollNo())) {
                    System.err.println("Error: Student with roll number " + student.getRollNo() + " already exists!");
                    return false;
                }
            }
            
            // Assign new ID
            student.setId(FileStorage.getNextId());
            students.add(student);
            FileStorage.saveStudents(students);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all students from the file
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        try {
            return FileStorage.loadStudents();
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Get a student by roll number
     * @param rollNo Roll number to search for
     * @return Student object if found, null otherwise
     */
    public Student getStudentByRollNo(String rollNo) {
        try {
            List<Student> students = FileStorage.loadStudents();
            for (Student student : students) {
                if (student.getRollNo().equalsIgnoreCase(rollNo)) {
                    return student;
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Update student information
     * @param student Student object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        try {
            List<Student> students = FileStorage.loadStudents();
            boolean found = false;
            
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getRollNo().equalsIgnoreCase(student.getRollNo())) {
                    students.set(i, student);
                    found = true;
                    break;
                }
            }
            
            if (found) {
                FileStorage.saveStudents(students);
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete a student from the file
     * @param rollNo Roll number of student to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStudent(String rollNo) {
        try {
            List<Student> students = FileStorage.loadStudents();
            boolean removed = students.removeIf(s -> s.getRollNo().equalsIgnoreCase(rollNo));
            
            if (removed) {
                FileStorage.saveStudents(students);
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get count of total students
     * @return Total number of students
     */
    public int getStudentCount() {
        try {
            return FileStorage.loadStudents().size();
        } catch (Exception e) {
            System.err.println("Error getting student count: " + e.getMessage());
            return 0;
        }
    }
}
