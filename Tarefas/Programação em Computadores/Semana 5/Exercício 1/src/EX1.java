import modelos.Retangulo;
import java.util.Scanner;

public class EX1 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Retangulo r1 = new Retangulo();

        System.out.println("Enter rectangle width and height: ");
        double w = sc.nextDouble();
        double h = sc.nextDouble();

        r1.setWidth(w);
        r1.setHeight(h);

        System.out.println(r1.Area());
        System.out.println(r1.Perimeter());
        System.out.println(r1.Diagonal());

        sc.close();
    }
}
