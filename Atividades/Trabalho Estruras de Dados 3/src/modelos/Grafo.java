package modelos;

// Classe que representa o grafo
public class Grafo {
    private int[][] matrizAdjacencia;
    private int vertices;

    public Grafo(int tamanhoInicial) {
        this.vertices = tamanhoInicial;
        this.matrizAdjacencia = new int[tamanhoInicial][tamanhoInicial];
    }

    public void adicionarAresta(int origem, int destino) {
        if (origem < vertices && destino < vertices) {
            matrizAdjacencia[origem][destino] = 1;
            matrizAdjacencia[destino][origem] = 1; // Para grafo não-direcionado
        }
    }

    public void removerAresta(int origem, int destino) {
        if (origem < vertices && destino < vertices) {
            matrizAdjacencia[origem][destino] = 0;
            matrizAdjacencia[destino][origem] = 0;
        }
    }

    public void adicionarVertice() {
        int novoTamanho = vertices + 1;
        int[][] novaMatriz = new int[novoTamanho][novoTamanho];

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                novaMatriz[i][j] = matrizAdjacencia[i][j];
            }
        }

        matrizAdjacencia = novaMatriz;
        vertices++;
    }

    public void removerVertice(int vertice) {
        if (vertice >= vertices) return;

        int[][] novaMatriz = new int[vertices - 1][vertices - 1];
        int novaLinha = 0;

        for (int i = 0; i < vertices; i++) {
            if (i == vertice) continue;
            int novaColuna = 0;

            for (int j = 0; j < vertices; j++) {
                if (j == vertice) continue;
                novaMatriz[novaLinha][novaColuna] = matrizAdjacencia[i][j];
                novaColuna++;
            }
            novaLinha++;
        }

        matrizAdjacencia = novaMatriz;
        vertices--;
    }

    public void imprimirMatrizAdjacencia() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getVertices() {
        return vertices;
    }

    public int[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }
}
