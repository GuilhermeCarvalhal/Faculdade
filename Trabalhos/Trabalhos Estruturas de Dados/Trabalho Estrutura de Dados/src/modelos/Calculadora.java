package modelos;

public class Calculadora {
    private PilhaGenerica<Float> valores;
    private PilhaGenerica<Character> operadores;
    
    
    public Calculadora(){
        valores = new PilhaGenerica<Float>();
        operadores = new PilhaGenerica<Character>();
    
    }

    public void imprimePilhaReal() {
        valores.imprimePilha();
    }

    public void imprimePilhaOperadores() {
        operadores.imprimePilha();
    }

    public void empilhaReal (Float n){
        valores.empilha(n);
        System.out.println("Valor empilhado com sucesso.");
    }

    public void empilhaOperador (Character n) {
        if (operadorValido(n)) {
            operadores.empilha(n);
            System.out.println("Operador empilhado com sucesso.");
        }
        else {
            System.out.println("Operador inválido!");
        }
    }

    public void realizarCalculo () {
        if(!faltaConteudo()) {
            float val1 = valores.desempilha();
            float val2 = valores.desempilha();

            Character op = operadores.desempilha();
            float resultado = 0;
            switch (op) {
                case '/':
                    resultado = val1 / val2;
                    break;
                case '*':
                    resultado = val1 * val2;
                    break;          
                case '+':
                    resultado = val1 + val2;
                    break;  
                case '-':
                    resultado = val1 - val2;
                    break;  
                default:
                    break;
            }
            System.out.printf("%.2f %c %.2f = %.2f", val1, op, val2, resultado);
            System.out.println();
            empilhaReal(resultado); 
    }
}

    public boolean operadorValido (Character n) {
        if ((n.equals('/')) || (n.equals('*')) || (n.equals('+')) || (n.equals('-'))) {
            return true;
        }
        return false;
    }

    public boolean faltaConteudo () {
        int qnt = valores.getQnt();

        if (qnt < 2) {
            System.out.println("Necessário mais " + (2-qnt) + " número(s) na pilha de reais.");
            return true;
        }
        else if(operadores.getQnt() < 1) {
            System.out.println("Lista de operadores vazia, adicione ao menos 1 para efetuar o cálculo.");
            return true;
        }
        return false;
    }

    public void teste() {
        empilhaReal(2.2f);
        empilhaReal(4.2f); 
        empilhaReal(6.2f); 
        empilhaReal(8.2f); 
        empilhaReal(10.2f); 

        empilhaOperador('-');
        empilhaOperador('*');
        empilhaOperador('+');
        empilhaOperador('/');
    }
}