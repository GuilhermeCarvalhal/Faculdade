import modelos.*;

public class Comendo {
    public static void main(String[] args) throws Exception {

        Animal a1 = new Animal("Caju", "gato", "felino", 5);
        Aves v1 = new Aves("Papagaio", "Verde", "Dos verde", 3, "Longo");
        Mamifero m1 = new Mamifero("Molly", "Mamute", "Mamíferos", 8, "Peluda");

        System.out.println(a1.alimentar());
        System.out.println(v1.alimentar());
        System.out.println(m1.alimentar());

        System.out.println(v1.voar());

        System.out.println(a1.toString());
        System.out.println(v1.toString());
        System.out.println(m1.toString());
    }
}
