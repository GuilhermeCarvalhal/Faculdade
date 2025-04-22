package Primeira;

public class Main {
    public static void main(String[] args) {
        // Criando a árvore binária de busca
        NodoArvore raiz = new NodoArvore(10);
        raiz.esquerda = new NodoArvore(5);
        raiz.direita = new NodoArvore(15);
        raiz.esquerda.esquerda = new NodoArvore(3);
        raiz.esquerda.direita = new NodoArvore(7);
        raiz.direita.esquerda = new NodoArvore(12);
        raiz.direita.direita = new NodoArvore(18);

        // Imprimindo a árvore original
        System.out.println("Árvore original em ordem:");
        raiz.imprimirEmOrdem();
        System.out.println();

        // Invertendo a árvore
        NodoArvore.inverte(raiz);

        // Imprimindo a árvore invertida
        System.out.println("Árvore invertida em ordem:");
        raiz.imprimirEmOrdem();
    }
}
