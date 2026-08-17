public class Employee {
    private int empId;
    private String fname;
    private String lname;
    private double salary;

    public Employee(int empId, String fname, String lname, double salary) {
        this.empId = empId;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return fname + " " + lname;
    }

    public double getSalary() {
        return salary;
    }
}
