import java.io.*;
import java.util.*;

// Student class to store student details
class Student {
    int id;
    String name;
    int age;
    String grade;

    // Constructor
    Student(int id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // To display student info
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students = new ArrayList<>();
    private String fileName = "students.txt";

    // Load students from file
    void loadStudents() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String grade = parts[3];
                students.add(new Student(id, name, age, grade));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading file!");
        }
    }

    // Save students to file
    void saveStudents() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Student s : students) {
                writer.write(s.id + "," + s.name + "," + s.age + "," + s.grade);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving file!");
        }
    }

    // Method to take user input
    Student getStudentInput(Scanner scanner) {
        try {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            System.out.print("Enter Grade: ");
            String grade = scanner.nextLine();

            if (age < 0 || age > 100) {
                System.out.println("Age should be between 0 and 100!");
                return null;
            }

            // Check if ID already exists
            for (Student s : students) {
                if (s.id == id) {
                    System.out.println("This ID is already used!");
                    return null;
                }
            }

            return new Student(id, name, age, grade);
        } catch (Exception e) {
            System.out.println("Wrong input! Try again.");
            scanner.nextLine(); // Clear bad input
            return null;
        }
    }

    // Add a student
    void addStudent(Scanner scanner) {
        Student student = getStudentInput(scanner);
        if (student != null) {
            students.add(student);
            saveStudents();
            System.out.println("Student added!");
        }
    }

    // Update a student
    void updateStudent(Scanner scanner) {
        try {
            System.out.print("Enter ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).id == id) {
                    Student newStudent = getStudentInput(scanner);
                    if (newStudent != null) {
                        students.set(i, newStudent);
                        saveStudents();
                        System.out.println("Student updated!");
                        return;
                    }
                    return;
                }
            }
            System.out.println("Student not found!");
        } catch (Exception e) {
            System.out.println("Wrong input! Try again.");
            scanner.nextLine();
        }
    }

    // Delete a student
    void deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id == id) {
                students.remove(i);
                saveStudents();
                System.out.println("Student deleted!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    // Search a student
    void searchStudent(int id) {
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("Student not found!");
    }

    // Show all students
    void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        System.out.println("\nAll Students:");
        for (Student s : students) {
            System.out.println(s);
        }
    }
}

public class Student {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.loadStudents();

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                if (choice == 6) {
                    System.out.println("Bye!");
                    break;
                }

                if (choice == 1) {
                    sms.addStudent(scanner);
                } else if (choice == 2) {
                    sms.updateStudent(scanner);
                } else if (choice == 3) {
                    System.out.print("Enter ID to delete: ");
                    int id = scanner.nextInt();
                    sms.deleteStudent(id);
                } else if (choice == 4) {
                    System.out.print("Enter ID to search: ");
                    int id = scanner.nextInt();
                    sms.searchStudent(id);
                } else if (choice == 5) {
                    sms.viewAllStudents();
                } else {
                    System.out.println("Wrong choice! Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Try again.");
                scanner.nextLine(); // Clear bad input
            }
        }
        scanner.close();
    }
}