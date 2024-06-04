import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        List<String> nomes = new ArrayList<>();

        // 1. Crie uma classe que leia nomes de pessoas a partir do usuário e grave os
        // dados lidos em um arquivo, repita essa operação até o usuário digitar SAIR.
        // Depois leia o arquivo escrito.
        // Lendo nomes do usuário e escrevendo no arquivo
        FileWriter writer = new FileWriter("nomes.txt");

        try {
            String nome;
            do {
                System.out.println("Digite um nome (ou digite 'SAIR' para sair):");
                nome = sc.nextLine();
                if (!nome.equalsIgnoreCase("SAIR")) {
                    nomes.add(nome);
                    writer.write(nome + "\n");
                }
            } while (!nome.equalsIgnoreCase("SAIR"));
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        // Lendo o arquivo e exibindo os nomes
        try (FileReader reader = new FileReader("nomes.txt");
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            String linha;
            System.out.println("Nomes lidos do arquivo:");
            while ((linha = bufferedReader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        // 2. Faça um programa que copie em um novo arquivo os nomes armazenados no
        // arquivo criado no exercício anterior.

        try (FileWriter copyWriter = new FileWriter("copianomes.txt");
                FileReader reader1 = new FileReader("nomes.txt");
                BufferedReader buffCopy = new BufferedReader(reader1)) {
            String linhacopia;
            while ((linhacopia = buffCopy.readLine()) != null) {
                copyWriter.write(linhacopia + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao copiar o arquivo : " + e.getMessage());
        }
        // 3. Faça um programa que receba um nome através do usuário e verifique se está
        // no arquivo criado no exercício 1. Caso o nome se encontra no arquivo mostrar
        // uma mensagem na tela “Nome já cadastrado”. Se não, armazenar no arquivo.

        try (FileReader reader1 = new FileReader("nomes.txt");
                BufferedReader buffCopy = new BufferedReader(reader1)) {

            String nomeU;
            boolean cadastro = false;
            System.out.println("Digite um nome para verificar se foi cadastrado no sistema: ");
            nomeU = sc.nextLine();

            String linhacopia;
            while ((linhacopia = buffCopy.readLine()) != null) {
                if (nomeU.equals(linhacopia)) {
                    System.out.println("Nome já cadastrado.");
                    cadastro = true;
                } else if (!cadastro) {
                    try (FileWriter appendWriter = new FileWriter("nomes.txt", true)) {
                        appendWriter.write(nomeU + "\n");
                        System.out.println("Nome adicionado com sucesso!");
                        break;
                    } catch (IOException e) {
                        System.err.println("Erro ao adicionar nome ao arquivo : " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4 - Altere o programa anterior e faça que o mesmo execute várias vezes,até
        // que o
        // usuário digite SAIR.

        String nomeU;
        boolean nomeEncontrado;

        do {
            System.out.println("Digite um nome (ou digite 'SAIR' para sair): ");
            nomeU = sc.nextLine();

            if (nomeU.equalsIgnoreCase("SAIR")) {
                break;
            }
            nomeEncontrado = false;

            try (FileReader reader = new FileReader("nomes.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader)) {

                String linha;
                while ((linha = bufferedReader.readLine()) != null) {
                    if (nomeU.equalsIgnoreCase(linha)) {
                        nomeEncontrado = true;
                        System.out.println("Nome já cadastrado.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }

            if (!nomeEncontrado) {
                try (FileWriter appendWriter = new FileWriter("nomes.txt", true)) {
                    appendWriter.write(nomeU + "\n");
                    System.out.println("Nome adicionado com sucesso!");
                } catch (IOException e) {
                    System.err.println("Erro ao adicionar nome ao arquivo : " + e.getMessage());
                }
            }
        } while (true);
        sc.close();
    }
}
