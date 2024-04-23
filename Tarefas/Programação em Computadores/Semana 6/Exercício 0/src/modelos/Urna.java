package modelos;

import java.util.Scanner;

public class Urna {

    private boolean apurada;
    private int ncandidatos;
    private int votosBrancos;
    private int votosNulos;
    private int[] candidatos;

    public Urna(int ncandidatos) {
        this.ncandidatos = ncandidatos;
        this.apurada = false;
        this.votosBrancos = 0;
        this.votosNulos = 0;
        this.candidatos = new int[ncandidatos];
    }

    public int getNcandidatos() {
        return ncandidatos;
    }

    public void setNcandidatos(int ncandidatos) {
        this.ncandidatos = ncandidatos;
    }

    public int getVotosBrancos() {
        return votosBrancos;
    }

    public void setVotosBrancos(int votosBrancos) {
        this.votosBrancos = votosBrancos;
    }

    public int getVotosNulos() {
        return votosNulos;
    }

    public void setVotosNulos(int votosNulos) {
        this.votosNulos = votosNulos;
    }

    public int[] getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(int[] candidatos) {
        this.candidatos = candidatos;
    }

    public void votar(Scanner sc, int voto) {

        if (!apurada) {
            for (int i = 0; i < ncandidatos; i++) {
                System.out.println("Digite " + i + " para votar no candidato Número " + i);
            }
            do {
                voto = sc.nextInt();
            } while (voto < 0 || voto > (ncandidatos - 1));

            candidatos[voto] += 1;
            System.out.println("Voto contabilizado!");
        } else {
            System.out.println("A votação já foi apurada. Não é possível votar.");
        }
    }

    public void votarBranco() {
        if (!apurada) {
            this.votosBrancos += 1;
            System.out.println("Voto em branco contabilizado!");
        } else {
            System.out.println("A votação já foi apurada. Não é possível votar.");
        }
    }

    public void votarNulo() {
        if (!apurada) {
            this.votosNulos += 1;
            System.out.println("Voto nulo contabilizado!");
        } else {
            System.out.println("A votação já foi apurada. Não é possível votar.");
        }
    }

    public void apurarEleicao() {
        if (!apurada) {
            System.out.println("Eleição apurada!");
        } else {
            System.out.println("A eleição já foi apurada.");
        }
        this.apurada = true;

    }

    public void mostrarResultados() {
        if (apurada) {
            int maior = 0;
            for (int i = 1; i < ncandidatos; i++) {
                if (this.candidatos[maior] < this.candidatos[i]) {
                    maior = i;
                }
            }
            for (int i = 0; i < ncandidatos; i++) {
                System.out.println("Candidato " + i + " ||| Votos: " + candidatos[i]);
            }

            System.out.println("Votos em branco ||| " + getVotosBrancos());
            System.out.println("Votos nulos ||| " + getVotosNulos());

            System.out.println("O vencedor foi o Candidato " + maior);
        } else {
            System.out.println("A eleição ainda não foi apurada.");
        }
    }
}
