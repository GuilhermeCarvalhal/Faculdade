package Terceira;

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

    // Método para inserir um valor na árvore binária de busca
    public void inserir(int valor) {
        if (valor < this.valor) {
            if (this.esquerda == null) {
                this.esquerda = new NodoArvore(valor);
            } else {
                this.esquerda.inserir(valor);
            }
        } else if (valor > this.valor) {
            if (this.direita == null) {
                this.direita = new NodoArvore(valor);
            } else {
                this.direita.inserir(valor);
            }
        }
    }

    // Método para fazer o percurso em ordem (simétrico) e preencher a lista duplamente encadeada
    public void preencherListaOrdenada(ListaDuplamenteEncadeada lista) {
        if (this.esquerda != null) {
            this.esquerda.preencherListaOrdenada(lista);
        }
        lista.adicionar(this.valor);
        if (this.direita != null) {
            this.direita.preencherListaOrdenada(lista);
        }
    }
}
