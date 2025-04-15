package Segunda;

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

    // Método para verificar se duas árvores são iguais
    public boolean saoIguais(NodoArvore outraArvore) {
        // Se ambos os nós forem nulos, são iguais
        if (this == null && outraArvore == null) {
            return true;
        }

        // Se um dos nós for nulo e o outro não, as árvores são diferentes
        if (this == null || outraArvore == null) {
            return false;
        }

        // Se os valores dos nós não forem iguais, as árvores são diferentes
        if (this.valor != outraArvore.valor) {
            return false;
        }

        // Verifica recursivamente as subárvores esquerda e direita
        return (this.esquerda == null ? outraArvore.esquerda == null : this.esquerda.saoIguais(outraArvore.esquerda)) &&
               (this.direita == null ? outraArvore.direita == null : this.direita.saoIguais(outraArvore.direita));
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
