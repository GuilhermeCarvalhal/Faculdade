import modelos.Employee;
import java.util.Scanner;

public class EX2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Employee p1 = new Employee();

        p1.setName("James");
        p1.setGrossSalary(6000);
        p1.setTax(1000);

        p1.NetSalary();

        System.out.println("Which percentage to increase salary? ");
        double temp = sc.nextDouble();
        p1.increaseSalary(temp);

        sc.close();
    }
}
