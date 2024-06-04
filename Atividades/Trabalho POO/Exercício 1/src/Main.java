import modelos.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Imobiliaria imobiliaria = new Imobiliaria();
        Scanner sc = new Scanner(System.in);
        // Declarando um loop para permitir acesso contínuo ao sistema até o usuário se
        // satisfazer.
        int menu, menu2 = -1;
        do {

            System.out.println("=========== IMOBILIÁRIA ===========" + "\n");
            System.out.println(
                    "Seja bem vindo(a) ao sistema da imobiliária, leia o menu e digite o número correspondente a ação desejada: "
                            + "\n");
            System.out.println("Digite 1 para cadastrar um novo imóvel.");
            System.out.println("Digite 2 para remover um imóvel já existente.");
            System.out.println("Digite 3 para alterar um imóvel já existente.");
            System.out.println("Digite 4 para consultar os imóveis disponíveis para venda." + "\n");
            System.out.println("Digite 0 para sair do sistema.");
            System.out.println("===================================");

            menu = sc.nextInt();

            //
            switch (menu) {
                case 1:
                    imobiliaria.adicionarImovel(sc);
                    break;
                case 2:
                    imobiliaria.removerImovel(sc);
                    break;
                case 3:
                    imobiliaria.alterarImovel(sc);
                    break;
                case 4:
                    System.out.println("Como você deseja buscar o imóvel?" + "\n");
                    System.out.println("1. Buscar pelo tipo do imóvel.");
                    System.out.println("2. Buscar por cidades.");
                    System.out.println("3. Buscar por bairros.");
                    System.out.println("4. Buscar por faixa de preço.");
                    System.out.println("5. Buscar pelo número mínimo de quartos desejado.");
                    System.out.println("6. Listar todos os imóveis.");

                    menu2 = sc.nextInt();

                    sc.nextLine();

                    switch (menu2) {
                        case 1:
                            imobiliaria.buscarTipo(sc);
                            break;
                        case 2:
                            imobiliaria.buscarCidade(sc);
                            break;
                        case 3:
                            imobiliaria.buscarBairro(sc);
                            break;
                        case 4:
                            imobiliaria.buscarPreco(sc);
                            break;
                        case 5:
                            imobiliaria.buscarQuarto(sc);
                            break;
                        case 6:
                            imobiliaria.listarImoveis();
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                    break;
                case 9:
                    imobiliaria.cheat();
                    break;
                case 0:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (menu != 0);
        sc.close();
    }
}
