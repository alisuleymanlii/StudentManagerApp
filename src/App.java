import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

class Student {
    String name;
    int age;
    double score;

    Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }
}

/// Main application class
public class App {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        loadFromFile();
        
        while(true){
            System.out.println("--------------------------------");
            System.out.println("1. Add student");
            System.out.println("2. Show students");
            System.out.println("3. Delete student");
            System.out.println("4. Calculate average score");
            System.out.println("5. Show the highest score");
            System.out.println("6. Exit");
            System.out.println("--------------------------------");
            System.out.println("Enter your choice: ");
            String choiceLine = scanner.nextLine(); 
            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch(Exception e) {
                System.out.println("Invalid choice!");
                continue;
            }
            switch(choice){
                case 1:
                    addStudent();
                    break;
                case 2:
                    showStudents();
                    break;
                case 3:
                    deleteStudent();
                    break;    
                case 4:
                    calculateAverageScore();
                    break;
                case 5:
                    showHighestScore();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

// Method to add a student
static void addStudent() {
    clearConsole();
    System.out.println("Enter name: ");
    String name;
    try {
        name = scanner.nextLine().trim();
        if(name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
    } catch(IllegalArgumentException e) {
        System.out.println(e.getMessage());
        return;
    }

    System.out.println("Enter age: ");
    int age;
    try {
       age = Integer.parseInt(scanner.nextLine().trim());
    } catch(NumberFormatException e) {
        System.out.println("Invalid age! Please enter a number.");
        return;
    }

    System.out.println("Enter score: ");
    double score;
    try {
       score = Double.parseDouble(scanner.nextLine().trim());
    } catch(NumberFormatException e) {
        System.out.println("Invalid Score! Please enter a number.");
        return;
    }

    students.add(new Student(name, age, score));
    System.out.println("Student added!\n");

    saveToFile();
}

// Method to delete a student
static void deleteStudent(){
    clearConsole();
    if(students.size() == 0){
        System.out.println("No students");
        return;
    }
    showStudents();
    System.out.println("Enter student number to delete: ");
    int number;
    try {
        number = Integer.parseInt(scanner.nextLine().trim());
        if(number < 1 || number > students.size()) {
            throw new IllegalArgumentException("Invalid student number!");
        }
    } catch(Exception e) {
        System.out.println(e.getMessage());
        return;
    }
    students.remove(number - 1);
    System.out.println("Student deleted!");
    saveToFile();
}


// Method to show all students
static void showStudents() {
    clearConsole();
    if(students.size() == 0){
        System.out.println("No students");
        return;
    }

    System.out.println("Number   Name    Age    Score");
    System.out.println("--------------------------------");
    for (int i = 0; i < students.size(); i++) {
        Student s = students.get(i);
        System.out.println((i + 1) + ". " + s.name + " " + s.age + " " + s.score);
    }
}

// Method to calculate average score
    static void calculateAverageScore(){
        clearConsole();
        if(students.size() == 0){
            System.out.println("No students");
            return;
        }

        double sum = 0;
        for(Student s : students){
            sum += s.score;
        }
        double average = sum / students.size();
        System.out.println("Average score: " + average);
    }

// Method to show the highest score
    static void showHighestScore(){
        clearConsole();
        if(students.size() == 0){
            System.out.println("No students");
            return;
        }    
    
        double highestScore = students.get(0).score;
        String highestName = students.get(0).name;
        int highestAge = students.get(0).age;
        
        for(Student s : students){
            if(s.score > highestScore){
                highestScore = s.score;
                highestName = s.name;
                highestAge = s.age;
            }
        }
        System.out.println("Highest score: " + highestName + " " + highestAge  + " " + highestScore);
    }

    // Method to save students to file
    static void saveToFile(){
     try{
        FileWriter writer = new FileWriter("students.txt", true);
           for(Student s : students){
               writer.write(s.name + "," + s.age + "," + s.score + "\n");
           }
        writer.close();
     }catch(IOException e){
        System.out.println("Error saving to file: " + e.getMessage());
     }
    }
//  Method to load students from file
    static void loadFromFile(){
        try{
            File file = new File("students.txt");
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 3){
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    double score = Double.parseDouble(parts[2]);
                    students.add(new Student(name, age, score));
                }
            }
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        }
    }

// Method to clear the console
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
}
