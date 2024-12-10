package Terceira;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criando a árvore binária de busca
        System.out.println("Digite os valores para a árvore binária de busca.");
        System.out.println("Digite um número negativo para finalizar a inserção.");

        NodoArvore raiz = null;
        while (true) {
            System.out.print("Digite um valor: ");
            int valor = scanner.nextInt();

            if (valor < 0) {
                break; // Finaliza a inserção
            }

            if (raiz == null) {
                raiz = new NodoArvore(valor);
            } else {
                raiz.inserir(valor);
            }
        }

        // Criando a lista duplamente encadeada e preenchendo com os valores da árvore
        ListaDuplamenteEncadeada lista = new ListaDuplamenteEncadeada();
        if (raiz != null) {
            raiz.preencherListaOrdenada(lista);
        }

        // Imprimindo a lista duplamente encadeada
        System.out.println("Elementos na lista duplamente encadeada (em ordem):");
        lista.imprimir();

        scanner.close();
    }
}
