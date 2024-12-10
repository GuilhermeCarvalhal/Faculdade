package Terceira;

class NodoLista {
    int valor;
    NodoLista anterior;
    NodoLista proximo;

    // Construtor
    public NodoLista(int valor) {
        this.valor = valor;
        this.anterior = null;
        this.proximo = null;
    }
}
