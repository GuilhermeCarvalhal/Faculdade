package modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Imobiliaria {

    private List<Imovel> lista_de_imoveis = new ArrayList<>();

    public Imobiliaria() {
    }

    public List<Imovel> getLista_de_imoveis() {
        return lista_de_imoveis;
    }

    public void adicionarImovel(Scanner sc) {
        boolean eR = false;
        int codigo;
        do {
            System.out.println("Digite o código do imóvel: ");
            codigo = sc.nextInt();

            for (Imovel imovel : lista_de_imoveis) {
                if (codigo == imovel.getCodigo()) {
                    eR = true;
                    System.out.println("Código já existente! Tente novamente.");
                }
            }
        } while (eR);

        System.out.println("Digite o valor da área construída: ");
        float area1 = sc.nextFloat();

        System.out.println("Digite o valor da área total: ");
        float area2 = sc.nextFloat();

        System.out.println("Digite o número de quartos: ");
        int nQuartos = sc.nextInt();

        System.out.println("Digite 0 para Casa ou 1 para Apartamento: ");
        int tipo = sc.nextInt();

        System.out.println("Digite o preço do imóvel: ");
        float preco = sc.nextFloat();

        sc.nextLine(); // Limpar o buffer

        System.out.println("Digite o nome da cidade: ");
        String cidade = sc.nextLine();

        System.out.println("Digite o nome do bairro: ");
        String bairro = sc.nextLine();

        Endereco eTemp = new Endereco(cidade, bairro);
        Imovel iTemp = new Imovel(codigo, area1, area2, nQuartos, tipo, preco, eTemp);

        lista_de_imoveis.add(iTemp);

        System.out.println("Adicionado com sucesso!");

    }

    public void removerImovel(Scanner sc) {
        System.out.println("==============================" + "\n");
        System.out.println("Digite o código referente ao imóvel que deseja remover: ");

        int codigo = sc.nextInt();

        lista_de_imoveis.removeIf(imovel -> imovel.getCodigo() == codigo);
    }

    public void alterarImovel(Scanner sc) {
        System.out.println("==============================" + "\n");
        System.out.println("Digite o código referente ao imóvel que deseja alterar: ");

        int codigo = sc.nextInt();

        for (Imovel imovel : lista_de_imoveis) {
            if (codigo == imovel.getCodigo()) {

            }
        }
    }

    // Fazer verificação.
    public void listarImoveis() {
        int i = 1;
        for (Imovel imovel : lista_de_imoveis) {
            System.out.println("========= Imóvel " + i + "=========");

            System.out.println("Código: " + imovel.getCodigo());
            System.out.println("Área construída: " + imovel.getAreaconstruida());
            System.out.println("Área total: " + imovel.getAreatotal());
            System.out.println("Número de quartos: " + imovel.getNumeroquartos());
            System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
            System.out.println("Preço: " + imovel.getPreco());
            System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());
            i++;
        }
    }

    public void buscarTipo(Scanner sc) {
        int tipo = -1;
        do {

            System.out.println("==============================" + "\n");
            System.out.println("Digite 0 para buscar por Casa.");
            System.out.println("Digite 1 para buscar por Apartamento.");

            tipo = sc.nextInt();

        } while (tipo != 0 && tipo != 1);

        for (Imovel imovel : lista_de_imoveis) {
            if (tipo == imovel.getTipo()) {

                System.out.println("==============================" + "\n");
                System.out.println("Código: " + imovel.getCodigo());
                System.out.println("Área construída: " + imovel.getAreaconstruida());
                System.out.println("Área total: " + imovel.getAreatotal());
                System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                System.out.println("Preço: " + imovel.getPreco());
                System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

            }
        }
    }

    public void buscarCidade(Scanner sc) {
        // Lista temporária para armazenar nomes de cidades sem repetição
        List<String> lista_cidade = new ArrayList<>();

        // Foreach verificando se existem repetições.
        for (Imovel imovel : lista_de_imoveis) {
            boolean cidadeEncontrada = false;

            for (String cidade : lista_cidade) {
                if (cidade.equals(imovel.getCidade())) {
                    cidadeEncontrada = true;
                }
            }
            if (!cidadeEncontrada) {
                lista_cidade.add(imovel.getCidade());
            }
        }

        // Printando as cidades sem repetições.
        System.out.println("==================" + "\n");
        System.out.println("Lista de cidades: ");

        for (String cidade : lista_cidade) {
            System.out.println(cidade);
        }
        System.out.println("");

        System.out.println("Digite o nome da cidade que deseja: ");

        String eCidade = sc.nextLine();

        for (Imovel imovel : lista_de_imoveis) {
            if (imovel.getCidade().equals(eCidade)) {

                System.out.println("==============================" + "\n");
                System.out.println("Código: " + imovel.getCodigo());
                System.out.println("Área construída: " + imovel.getAreaconstruida());
                System.out.println("Área total: " + imovel.getAreatotal());
                System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                System.out.println("Preço: " + imovel.getPreco());
                System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

            }
        }

    }

    public void buscarBairro(Scanner sc) {
        // Evitando repetição
        List<Endereco> lista_endereco = new ArrayList<>();

        for (Imovel imovel : lista_de_imoveis) {
            boolean enderecoEncontrado = false;

            for (Endereco endereco : lista_endereco) {
                if (endereco.equals(imovel.getLocalizacao())) {
                    enderecoEncontrado = true;
                }
            }
            if (!enderecoEncontrado) {
                lista_endereco.add(imovel.getLocalizacao());
            }
        }

        // Printando os bairros e cidades sem repetições.
        System.out.println("==================" + "\n");
        System.out.println("Lista de bairros e cidades: ");

        for (Endereco endereco : lista_endereco) {
            System.out.println("Bairro: " + endereco.getBairro() + " | Cidade = " + endereco.getCidade());
        }
        System.out.println("");

        System.out.println("Digite o nome do bairro desejado: ");

        String eBairro = sc.nextLine();

        for (Imovel imovel : lista_de_imoveis) {
            if (imovel.getBairro().equals(eBairro)) {

                System.out.println("==============================" + "\n");
                System.out.println("Código: " + imovel.getCodigo());
                System.out.println("Área construída: " + imovel.getAreaconstruida());
                System.out.println("Área total: " + imovel.getAreatotal());
                System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                System.out.println("Preço: " + imovel.getPreco());
                System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());
            }
        }
    }

    public void buscarPreco(Scanner sc) {
        System.out.println("==============================" + "\n");
        System.out.println("Digite o número antes do ponto - referente a faixa de valor você deseja: ");
        System.out.println("1. de 0 a R$100.000,00");
        System.out.println("2. de R$100.000,01 a R$200.000,00");
        System.out.println("3. acima de 200.000,01");

        int menu = sc.nextInt();

        System.out.println("==============================" + "\n");
        switch (menu) {
            case 1:
                System.out.println("Imóveis na faixa de valor de 0 a R$100.000,00: ");
                for (Imovel imovel : lista_de_imoveis) {
                    if ((imovel.getPreco() >= 0) && (imovel.getPreco() <= 100000.00)) {

                        System.out.println("==============================" + "\n");
                        System.out.println("Código: " + imovel.getCodigo());
                        System.out.println("Área construída: " + imovel.getAreaconstruida());
                        System.out.println("Área total: " + imovel.getAreatotal());
                        System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                        System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                        System.out.println("Preço: " + imovel.getPreco());
                        System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

                    }
                }
                break;
            case 2:
                System.out.println("Imóveis na faixa de valor de R$100.000,01 a R$200.000,00 ");
                for (Imovel imovel : lista_de_imoveis) {
                    if ((imovel.getPreco() >= 100000.01) && (imovel.getPreco() <= 200000.00)) {

                        System.out.println("==============================" + "\n");
                        System.out.println("Código: " + imovel.getCodigo());
                        System.out.println("Área construída: " + imovel.getAreaconstruida());
                        System.out.println("Área total: " + imovel.getAreatotal());
                        System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                        System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                        System.out.println("Preço: " + imovel.getPreco());
                        System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

                    }
                }
                break;
            case 3:
                System.out.println("Imóveis na faixa de valor acima de 200.000,01 ");
                for (Imovel imovel : lista_de_imoveis) {
                    if (imovel.getPreco() > 200000.01) {

                        System.out.println("==============================" + "\n");
                        System.out.println("Código: " + imovel.getCodigo());
                        System.out.println("Área construída: " + imovel.getAreaconstruida());
                        System.out.println("Área total: " + imovel.getAreatotal());
                        System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                        System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                        System.out.println("Preço: " + imovel.getPreco());
                        System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

                    }
                }
                break;
            default:
                System.out.println("Alternativa inválida, tente novamente.");
        }
    }

    public void buscarQuarto(Scanner sc) {
        System.out.println("==============================" + "\n");
        System.out.println("Digite o valor mínimo de quartos que o imóvel deve ter: ");

        int quartos = sc.nextInt();

        System.out.println("Imóveis com no mínimo " + quartos + " quartos:");
        for (Imovel imovel : lista_de_imoveis) {
            if (quartos == imovel.getNumeroquartos()) {

                System.out.println("==============================" + "\n");
                System.out.println("Código: " + imovel.getCodigo());
                System.out.println("Área construída: " + imovel.getAreaconstruida());
                System.out.println("Área total: " + imovel.getAreatotal());
                System.out.println("Número de quartos: " + imovel.getNumeroquartos());
                System.out.println("0 para Casa, 1 para Apto: " + imovel.getTipo());
                System.out.println("Preço: " + imovel.getPreco());
                System.out.println("Cidade: " + imovel.getCidade() + " Bairro: " + imovel.getBairro());

            }
        }

    }

    public void cheat() {
        Endereco aEnd = new Endereco("SP", "Capa");
        Endereco bEnd = new Endereco("SP", "Capa");
        Endereco cEnd = new Endereco("ARU", "Mato");

        Imovel a = new Imovel(10, 20, 30, 3, 1, 90000, aEnd);
        Imovel b = new Imovel(11, 30, 40, 4, 1, 150000, bEnd);
        Imovel c = new Imovel(12, 40, 50, 5, 0, 200001, cEnd);
        Imovel d = new Imovel(13, 50, 60, 5, 0, 200001, cEnd);

        lista_de_imoveis.add(a);
        lista_de_imoveis.add(b);
        lista_de_imoveis.add(c);
        lista_de_imoveis.add(d);

        System.out.println("Cheat instalado!");
    }
}
