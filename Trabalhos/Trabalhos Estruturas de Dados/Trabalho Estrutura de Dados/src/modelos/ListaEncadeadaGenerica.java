package modelos;

class ListaEncadeadaGenerica<Item> {
    Nodo inicio;

    public class Nodo {
        Item dados;
        Nodo proximo;
    }
    
    void insereInicio(Item n) {
        Nodo novo = new Nodo();
        novo.dados = n;
        novo.proximo = inicio; //Troca de lugar com o antigo inicio
        inicio = novo;
    }

    Item removeInicio() {
        if (inicio != null) {
            Item valor = inicio.dados;
            inicio = inicio.proximo;
            return valor;
        }
        return null;
    }

    Item removeMeio(Item n) {
        Nodo atual = inicio;
        Nodo anterior = null;

        while (atual.dados != null && !atual.dados.equals(n)) {
            anterior = atual;
            atual = atual.proximo;
        }

        if (atual.equals(null)) {
            return null;
        }

        if (anterior.equals(null)) {
            return removeInicio();
        }
        else {
            anterior.proximo = atual.proximo;
            Item x = atual.dados;
            return x;
        }
    }

    void inserefinal(Item n) {
        Nodo novo = new Nodo();
        novo.dados = n;
        novo.proximo = null;

        if (inicio == null) { //Se não tiver nada, o inicio se torna o último
            inicio = novo;


        } else {
            Nodo atual = inicio; //temp
            while (atual.proximo != null) { //Enquanto o próximo não vou null, passa um valor na frente OU SEJA próximo = null logo atual = ultimo
                atual = atual.proximo;
            }
            atual.proximo = novo; //Atual == ultimo logo atual.proximo == ultimo da pilha
        }
        
        }

    Item removeFinal() {
        if (inicio == null) { //Se a pilha tiver vazia, F
            return null;
        }

        if (inicio.proximo ==null) { // Se a pilha tiver só 1 valor armazenado
            Item valor = inicio.dados; //pega esse valor e guarda para retornar
            inicio = null;  //Deleta o valor da pilha
            return valor;
        }

        Nodo atual = inicio; //Temp
        while (atual.proximo.proximo != null) { //vai até o penultimo da pilha, já que o próximo do próximo é vazio
            atual = atual.proximo;
        }

        Item x = atual.proximo.dados; //armazena o valor do próximo OU SEJA do último componente da pilha
        atual.proximo = null; //Deleta o ultimo componente
        return x; //Retorna o valor deletado
    }

    void imprimeLista() {
        Nodo temp = inicio;
        while (temp != null) {
            System.out.printf("'" + temp.dados + "'" + " -> ");
            temp = temp.proximo;
        } 
        System.out.println();
    }

}
