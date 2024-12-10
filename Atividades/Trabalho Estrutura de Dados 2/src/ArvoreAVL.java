public class ArvoreAVL {
    
    private NodoArvoreAVL raiz;
    
    ArvoreAVL(){
        raiz = null;
    }

    int altura(){
        if (raiz == null) return -1;
        else return raiz.altura(raiz);
    
    }
    
    void insere(Integer v){
        if (this.raiz == null) 
            raiz = new NodoArvoreAVL(v, null, null);
        else
            raiz = raiz.insereBalanceado(raiz, v);
    }
    
    void imprimeSim(){
        if (raiz != null){
            raiz.imprimeSim(raiz);
        }
        System.out.println();
    
    }
    
   // void retira(Integer v){
    //    if (this.raiz != null)
    //       raiz = raiz.retiraValor(raiz, v);
   // }
    
}
