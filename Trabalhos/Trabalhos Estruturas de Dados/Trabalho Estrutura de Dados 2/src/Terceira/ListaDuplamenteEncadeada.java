package Terceira;

class ListaDuplamenteEncadeada {
    NodoLista inicio;
    NodoLista fim;

    // Construtor
    public ListaDuplamenteEncadeada() {
        this.inicio = null;
        this.fim = null;
    }

    // Método para adicionar um elemento à lista
    public void adicionar(int valor) {
        NodoLista novoNodo = new NodoLista(valor);
        if (inicio == null) {
            inicio = novoNodo;
            fim = novoNodo;
        } else {
            fim.proximo = novoNodo;
            novoNodo.anterior = fim;
            fim = novoNodo;
        }
    }

    // Método para imprimir os elementos da lista
    public void imprimir() {
        NodoLista atual = inicio;
        while (atual != null) {
            System.out.print(atual.valor + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }
}
