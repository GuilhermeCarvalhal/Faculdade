import java.util.*;

import modelos.*;

class TestaGrafo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = null;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Criar um grafo");
            System.out.println("2) Adicionar Aresta");
            System.out.println("3) Remover Aresta");
            System.out.println("4) Adicionar Vértice");
            System.out.println("5) Remover Vértice");
            System.out.println("6) Imprimir Matriz de Adjacência");
            System.out.println("7) Realizar Busca em Largura e Imprimir Resultado");
            System.out.println("8) Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Informe o número de vértices iniciais: ");
                    int tamanho = scanner.nextInt();
                    grafo = new Grafo(tamanho);
                    System.out.println("Grafo criado com sucesso!");
                    break;
                case 2:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    System.out.print("Informe a origem: ");
                    int origem = scanner.nextInt();
                    System.out.print("Informe o destino: ");
                    int destino = scanner.nextInt();
                    grafo.adicionarAresta(origem, destino);
                    System.out.println("Aresta adicionada!");
                    break;
                case 3:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    System.out.print("Informe a origem: ");
                    origem = scanner.nextInt();
                    System.out.print("Informe o destino: ");
                    destino = scanner.nextInt();
                    grafo.removerAresta(origem, destino);
                    System.out.println("Aresta removida!");
                    break;
                case 4:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    grafo.adicionarVertice();
                    System.out.println("Vértice adicionado!");
                    break;
                case 5:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    System.out.print("Informe o vértice a ser removido: ");
                    int vertice = scanner.nextInt();
                    grafo.removerVertice(vertice);
                    System.out.println("Vértice removido!");
                    break;
                case 6:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    System.out.println("Matriz de Adjacência:");
                    grafo.imprimirMatrizAdjacencia();
                    break;
                case 7:
                    if (grafo == null) {
                        System.out.println("Crie um grafo primeiro!");
                        break;
                    }
                    System.out.print("Informe o vértice de origem: ");
                    origem = scanner.nextInt();
                    List<Integer> resultado = BuscaLargura.buscaEmLargura(grafo, origem);
                    System.out.println("Resultado da Busca em Largura: " + resultado);
                    break;
                case 8:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
