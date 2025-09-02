import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentStore implements Serializable {
    private final List<Student> students = new ArrayList<>();

    // Add a new student
    public void addStudent(Student s) {
        students.add(s);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Find student by ID (linear search)
    public Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    // Update student CGPA by ID
    public boolean updateCgpa(int id, double newCgpa) {
        Student s = findById(id);
        if (s != null) {
            s.setCgpa(newCgpa);
            return true;
        }
        return false;
    }

    // Compute average CGPA
    public double averageCgpa() {
        if (students.isEmpty()) return 0.0;
        double sum = 0;
        for (Student s : students) sum += s.getCgpa();
        return sum / students.size();
    }

    // =========================
    // Result Summaries
    // =========================

    /** Returns the top performer (highest CGPA), or null if empty */
    public Student getTopPerformer() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getCgpa() > top.getCgpa()) top = s;
        }
        return top;
    }

    /** Returns the lowest performer (lowest CGPA), or null if empty */
    public Student getLowestPerformer() {
        if (students.isEmpty()) return null;
        Student low = students.get(0);
        for (Student s : students) {
            if (s.getCgpa() < low.getCgpa()) low = s;
        }
        return low;
    }

    /** Returns total number of students */
    public int getStudentCount() {
        return students.size();
    }

    // =========================
    // Sorting & Searching
    // =========================
    public void quickSortByName() {
        quickSort(0, students.size() - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        Student pivot = students.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (students.get(j).getName().compareToIgnoreCase(pivot.getName()) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }

    public void bubbleSortByCGPA() {
        int n = students.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getCgpa() > students.get(j + 1).getCgpa()) {
                    swap(j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public Student binarySearchById(int id) {
        students.sort((a, b) -> a.getId() - b.getId());
        int low = 0, high = students.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            Student s = students.get(mid);
            if (s.getId() == id) return s;
            else if (s.getId() < id) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    // =========================
    // File Save/Load
    // =========================

    // Save students to file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load students from file
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Student> loaded = (List<Student>) ois.readObject();
            students.clear();
            students.addAll(loaded);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting with empty store.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
