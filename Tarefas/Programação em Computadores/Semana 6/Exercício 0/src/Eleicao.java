import modelos.Urna;
import java.util.Scanner;

public class Eleicao {
    public static void main(String[] args) throws Exception {

        Urna eleicao = new Urna(3);

        Scanner sc = new Scanner(System.in);

        int menu = 9;
        while (menu != 0) {
            System.out.println("=======================");
            System.out.println("Seja bem vindo a urna eletrônica!" + "\n");
            System.out.println("Para votar, Digite 1");
            System.out.println("Parar votar em branco, Digite 2");
            System.out.println("Para votar nulo, Digite 3");
            System.out.println("Para apurar a eleição, Digite 4");
            System.out.println("Para mostrar os resultados, Digite 5");
            System.out.println("Para desligar a urna, Digite 0");
            menu = sc.nextInt();

            switch (menu) {
                case 1:
                    System.out.println("==================");
                    eleicao.votar(sc, 0);
                    break;
                case 2:
                    System.out.println("==================");
                    eleicao.votarBranco();
                    break;
                case 3:
                    System.out.println("==================");
                    eleicao.votarNulo();
                    break;
                case 4:
                    System.out.println("==================");
                    eleicao.apurarEleicao();
                    break;
                case 5:
                    System.out.println("==================");
                    eleicao.mostrarResultados();
                    break;
            }

        }

        sc.close();
    }
}
