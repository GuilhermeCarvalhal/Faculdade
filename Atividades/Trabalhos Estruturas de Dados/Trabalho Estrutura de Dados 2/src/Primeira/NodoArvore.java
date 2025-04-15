package Primeira;

class NodoArvore {
    int valor;
    NodoArvore esquerda;
    NodoArvore direita;

    // Construtor
    public NodoArvore(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }

    // Método estático para inverter as subárvores
    public static void inverte(NodoArvore nodo) {
        if (nodo == null) {
            return; // Se o nodo for nulo, não há nada para inverter
        }

        // Troca as subárvores esquerda e direita
        NodoArvore temp = nodo.esquerda;
        nodo.esquerda = nodo.direita;
        nodo.direita = temp;

        // Chama recursivamente para inverter as subárvores dos filhos, se existirem
        inverte(nodo.esquerda);
        inverte(nodo.direita);
    }

    // Método para imprimir a árvore em ordem (in-order)
    public void imprimirEmOrdem() {
        if (this.esquerda != null) {
            this.esquerda.imprimirEmOrdem();
        }
        System.out.print(this.valor + " ");
        if (this.direita != null) {
            this.direita.imprimirEmOrdem();
        }
    }
}
