package modelos;

class PilhaGenerica<Item> {
    private ListaEncadeadaGenerica<Item> lis;
    private int qnt = 0;

    PilhaGenerica() {
        lis = new ListaEncadeadaGenerica<Item>();
    }

    void empilha(Item valor) {
        lis.inserefinal(valor);
        qnt++;
    }

    Item desempilha() {
        qnt--;
        return lis.removeFinal();
        
    }

    boolean estaVazia() {   
        return lis.inicio == null;
    }

    void imprimePilha() {
        lis.imprimeLista();
    }

    int getQnt () {
        return this.qnt;
    }
}