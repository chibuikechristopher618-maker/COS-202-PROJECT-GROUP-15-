public class Student {
    private int id;
    private String name;
    private double cgpa;

    // Overloaded constructors (OOP: encapsulation + overloading)
    public Student(int id, String name) { this(id, name, 0.0); }

    public Student(int id, String name, double cgpa) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (cgpa < 0.0 || cgpa > 5.0) throw new IllegalArgumentException("CGPA must be 0.0–5.0");
        this.id = id;
        this.name = name.trim();
        this.cgpa = cgpa;
    }

    // Getters/setters (encapsulation + validation)
    public int getId() { return id; }
    public String getName() { return name; }
    public double getCgpa() { return cgpa; }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        this.name = name.trim();
    }

    public void setCgpa(double cgpa) {
        if (cgpa < 0.0 || cgpa > 5.0) throw new IllegalArgumentException("CGPA must be 0.0–5.0");
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', cgpa=%.2f}", id, name, cgpa);
    }
}

