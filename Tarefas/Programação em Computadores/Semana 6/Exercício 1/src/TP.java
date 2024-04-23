import modelos.Circulo;
import modelos.Ponto;

public class TP {
    public static void main(String[] args) throws Exception {
        Ponto p1 = new Ponto(10, 5, 15);
        Circulo c1 = new Circulo(18, 6, 3, 9);

        System.out.println("Raio " + c1.getRaio());
        System.out.println("Movendo");
        p1.move(2, 2);
        System.out.println("X " + c1.getX());
        System.out.println("Numprotegido: " + c1.getNumProtegido());

    }
}
