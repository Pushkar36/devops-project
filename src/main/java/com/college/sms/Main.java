package com.college.sms;

import com.college.sms.dao.StudentDAO;
import com.college.sms.model.Student;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main Application Entry Point
 * Console-based Student Management System
 */
public class Main {
    
    private static StudentDAO studentDAO = new StudentDAO();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        displayWelcomeBanner();
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            int choice = getIntInput("\nEnter your choice: ");
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    running = false;
                    displayExitMessage();
                    break;
                default:
                    System.out.println("\n❌ Invalid choice! Please select 1-7.");
            }
            
            if (running) {
                pauseForUser();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display welcome banner
     */
    private static void displayWelcomeBanner() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                                                       ║");
        System.out.println("║       STUDENT MANAGEMENT SYSTEM - v1.0               ║");
        System.out.println("║       Java • File Storage • Maven                    ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
    
    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("\n════════════════ MAIN MENU ════════════════");
        System.out.println("  1. ➕ Add New Student");
        System.out.println("  2. 📋 View All Students");
        System.out.println("  3. 🔍 Search Student by Roll No");
        System.out.println("  4. ✏️  Update Student Details");
        System.out.println("  5. 🗑️  Delete Student");
        System.out.println("  6. 📊 View Statistics");
        System.out.println("  7. 🚪 Exit");
        System.out.println("═══════════════════════════════════════════");
    }
    
    /**
     * Add a new student
     */
    private static void addStudent() {
        System.out.println("\n━━━━━━━━━━━ ADD NEW STUDENT ━━━━━━━━━━━");
        
        try {
            System.out.print("Enter Roll Number: ");
            String rollNo = scanner.nextLine().trim();
            
            if (rollNo.isEmpty()) {
                System.out.println("❌ Roll number cannot be empty!");
                return;
            }
            
            // Check if roll number already exists
            if (studentDAO.getStudentByRollNo(rollNo) != null) {
                System.out.println("❌ Student with roll number " + rollNo + " already exists!");
                return;
            }
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();
            
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dobStr = scanner.nextLine().trim();
            LocalDate dob = LocalDate.parse(dobStr);
            
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            
            Student student = new Student(rollNo, name, email, phone, dob, department);
            
            if (studentDAO.addStudent(student)) {
                System.out.println("\n✅ Student added successfully!");
                System.out.println("   Roll No: " + rollNo);
                System.out.println("   Name: " + name);
            } else {
                System.out.println("\n❌ Failed to add student. Please try again.");
            }
            
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format! Use YYYY-MM-DD (e.g., 2003-05-15)");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * View all students
     */
    private static void viewAllStudents() {
        System.out.println("\n━━━━━━━━━━━ ALL STUDENTS ━━━━━━━━━━━");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("\n📭 No students found in the system.");
            System.out.println("   Add some students first using option 1.");
        } else {
            System.out.println("\nTotal Students: " + students.size());
            System.out.println("\n" + "─".repeat(120));
            System.out.printf("%-5s %-12s %-25s %-20s %-20s %-15s %-12s%n",
                    "ID", "Roll No", "Name", "Department", "Email", "Phone", "DOB");
            System.out.println("─".repeat(120));
            
            for (Student s : students) {
                System.out.printf("%-5d %-12s %-25s %-20s %-20s %-15s %-12s%n",
                        s.getId(), 
                        s.getRollNo(), 
                        truncate(s.getName(), 25), 
                        truncate(s.getDepartment(), 20), 
                        truncate(s.getEmail(), 20), 
                        s.getPhone(), 
                        s.getDob());
            }
            System.out.println("─".repeat(120));
        }
    }
    
    /**
     * Search student by roll number
     */
    private static void searchStudent() {
        System.out.print("\n🔍 Enter Roll Number to search: ");
        String rollNo = scanner.nextLine().trim();
        
        Student student = studentDAO.getStudentByRollNo(rollNo);
        
        if (student != null) {
            System.out.println("\n✅ Student Found:");
            System.out.println("━".repeat(60));
            System.out.println("  ID          : " + student.getId());
            System.out.println("  Roll No     : " + student.getRollNo());
            System.out.println("  Name        : " + student.getName());
            System.out.println("  Email       : " + student.getEmail());
            System.out.println("  Phone       : " + student.getPhone());
            System.out.println("  DOB         : " + student.getDob());
            System.out.println("  Department  : " + student.getDepartment());
            System.out.println("━".repeat(60));
        } else {
            System.out.println("\n❌ Student not found with Roll No: " + rollNo);
        }
    }
    
    /**
     * Update student details
     */
    private static void updateStudent() {
        System.out.print("\n✏️ Enter Roll Number of student to update: ");
        String rollNo = scanner.nextLine().trim();
        
        Student student = studentDAO.getStudentByRollNo(rollNo);
        
        if (student == null) {
            System.out.println("❌ Student not found with Roll No: " + rollNo);
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println("━".repeat(60));
        System.out.println(student);
        System.out.println("━".repeat(60));
        System.out.println("\nEnter new details (press Enter to keep current value):\n");
        
        System.out.print("Name [" + student.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) student.setName(name);
        
        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) student.setEmail(email);
        
        System.out.print("Phone [" + student.getPhone() + "]: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) student.setPhone(phone);
        
        System.out.print("Department [" + student.getDepartment() + "]: ");
        String dept = scanner.nextLine().trim();
        if (!dept.isEmpty()) student.setDepartment(dept);
        
        System.out.print("Date of Birth [" + student.getDob() + "] (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine().trim();
        if (!dobStr.isEmpty()) {
            try {
                student.setDob(LocalDate.parse(dobStr));
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Invalid date format! Keeping existing date.");
            }
        }
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("\n✅ Student updated successfully!");
        } else {
            System.out.println("\n❌ Failed to update student.");
        }
    }
    
    /**
     * Delete a student
     */
    private static void deleteStudent() {
        System.out.print("\n🗑️ Enter Roll Number to delete: ");
        String rollNo = scanner.nextLine().trim();
        
        Student student = studentDAO.getStudentByRollNo(rollNo);
        
        if (student == null) {
            System.out.println("❌ Student not found with Roll No: " + rollNo);
            return;
        }
        
        System.out.println("\n⚠️  You are about to delete:");
        System.out.println("   " + student);
        System.out.print("\nAre you sure? Type 'yes' to confirm: ");
        String confirm = scanner.nextLine().trim();
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (studentDAO.deleteStudent(rollNo)) {
                System.out.println("\n✅ Student deleted successfully!");
            } else {
                System.out.println("\n❌ Failed to delete student.");
            }
        } else {
            System.out.println("\n🚫 Delete operation cancelled.");
        }
    }
    
    /**
     * Display system statistics
     */
    private static void displayStatistics() {
        System.out.println("\n━━━━━━━━━━━ SYSTEM STATISTICS ━━━━━━━━━━━");
        
        int totalStudents = studentDAO.getStudentCount();
        
        System.out.println("\n📊 Total Students: " + totalStudents);
        
        if (totalStudents > 0) {
            List<Student> students = studentDAO.getAllStudents();
            System.out.println("\n📈 Recent Additions:");
            int count = Math.min(5, students.size());
            for (int i = students.size() - 1; i >= students.size() - count; i--) {
                Student s = students.get(i);
                System.out.println("   • " + s.getRollNo() + " - " + s.getName());
            }
        }
        
        System.out.println("\n" + "━".repeat(40));
    }
    
    /**
     * Display exit message
     */
    private static void displayExitMessage() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║   Thank you for using                    ║");
        System.out.println("║   Student Management System!             ║");
        System.out.println("║                                          ║");
        System.out.println("║   Goodbye! 👋                            ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");
    }
    
    /**
     * Get integer input with error handling
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine(); // clear buffer
            }
        }
    }
    
    /**
     * Pause and wait for user
     */
    private static void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Truncate string to specified length
     */
    private static String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}
