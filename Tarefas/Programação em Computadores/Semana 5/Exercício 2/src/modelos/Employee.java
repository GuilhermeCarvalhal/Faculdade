package modelos;

public class Employee {

    // Atributos
    private String name;
    private double GrossSalary;
    private double Tax;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrossSalary() {
        return GrossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        GrossSalary = grossSalary;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double NetSalary() {
        System.out.println("Employee: " + this.name + ", $ " + (this.GrossSalary - this.Tax));
        return this.GrossSalary - this.Tax;
    }

    public void increaseSalary(double percentage) {

        this.GrossSalary += (percentage / 100) * this.GrossSalary;
        System.out.println("Updated data: " + this.name + ", $ " + (this.GrossSalary - this.Tax));

    }

}
