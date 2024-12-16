package modelos;

import java.util.*;
public class BuscaLargura {
    public static List<Integer> buscaEmLargura(Grafo grafo, int origem) {
        List<Integer> visitados = new ArrayList<>();
        Queue<Integer> fila = new LinkedList<>();
        boolean[] marcados = new boolean[grafo.getVertices()];

        fila.add(origem);
        marcados[origem] = true;

        while (!fila.isEmpty()) {
            int vertice = fila.poll();
            visitados.add(vertice);

            for (int i = 0; i < grafo.getVertices(); i++) {
                if (grafo.getMatrizAdjacencia()[vertice][i] == 1 && !marcados[i]) {
                    fila.add(i);
                    marcados[i] = true;
                }
            }
        }

        return visitados;
    }
}
