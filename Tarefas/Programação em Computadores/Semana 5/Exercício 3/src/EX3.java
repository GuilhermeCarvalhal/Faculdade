import modelos.Student;
import java.util.Scanner;

public class EX3 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Student a1 = new Student();

        System.out.println("Digite o nome do estudante: ");
        String tempn = sc.next();

        float n1 = lerNota(sc, "primeiro trimestre", 0, 30);
        float n2 = lerNota(sc, "segundo trimestre", 0, 35);
        float n3 = lerNota(sc, "terceiro trimestre", 0, 35);

        a1.setName(tempn);
        a1.setT1(n1);
        a1.setT2(n2);
        a1.setT3(n3);

        System.out.println("FINAL GRADE:" + a1.getFinalGrade());
        if (a1.boolApproval(a1.getFinalGrade())) {
            System.out.println("PASS");
        } else {
            System.out.println("FAILED");

            System.out.println("MISSING " + (60 - a1.getFinalGrade()) + " POINTS");
        }
        System.out.println();

        sc.close();
    }

    public static float lerNota(Scanner sc, String trimestre, float min, float max) {
        float nota;
        do {
            System.out.println("Digite a nota referente ao " + trimestre + ": ");
            nota = sc.nextFloat();
        } while (nota < min || nota > max);
        return nota;
    }

}
