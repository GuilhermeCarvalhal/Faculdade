package Segunda;

public class Main {
    public static void main(String[] args) {
        // Criando a primeira árvore binária de busca
        NodoArvore raiz1 = new NodoArvore(10);
        raiz1.esquerda = new NodoArvore(5);
        raiz1.direita = new NodoArvore(15);
        raiz1.esquerda.esquerda = new NodoArvore(3);
        raiz1.esquerda.direita = new NodoArvore(7);
        raiz1.direita.esquerda = new NodoArvore(12);
        raiz1.direita.direita = new NodoArvore(18);

        // Criando a segunda árvore binária de busca (igual à primeira)
        NodoArvore raiz2 = new NodoArvore(10);
        raiz2.esquerda = new NodoArvore(5);
        raiz2.direita = new NodoArvore(15);
        raiz2.esquerda.esquerda = new NodoArvore(3);
        raiz2.esquerda.direita = new NodoArvore(7);
        raiz2.direita.esquerda = new NodoArvore(12);
        raiz2.direita.direita = new NodoArvore(18);

        // Verificando se as duas árvores são iguais
        System.out.println("As árvores são iguais? " + raiz1.saoIguais(raiz2));

        // Alterando a segunda árvore para torná-la diferente
        raiz2.direita.direita = new NodoArvore(20);

        // Verificando novamente se as árvores são iguais
        System.out.println("As árvores são iguais? " + raiz1.saoIguais(raiz2));
    }
}
