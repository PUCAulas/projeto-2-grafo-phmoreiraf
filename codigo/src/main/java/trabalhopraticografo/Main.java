package trabalhopraticografo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        Scanner scanner1 = new Scanner(System.in);

        String arquivoEntrada = "";
        int numArquivo;

        System.out.println(
                "Selecione o arquivo que deseja usar: (1 - registro.txt, 2 - cidades.txt, 3 - cidades2.txt, 4 - hamiltoniano.txt) ");
        numArquivo = scanner1.nextInt();

        if (numArquivo == 1) {
            arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/registro.txt";
        } else if (numArquivo == 2) {
            arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/cidades.txt";
        } else if (numArquivo == 3) {
            arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/cidades2.txt";
        } else if (numArquivo == 4) {
            arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/hamiltoniano.txt";
        } else {
            System.out.println("Digite entre os arquivos (1 a 4");
        }

        Grafo grafo = new Grafo();
        Cidade cidadeOrigem;

        try {

            Scanner scanner = new Scanner(new File(arquivoEntrada));

            // Processar as linhas do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(":");
                String nomeCidadeOrigem = partes[0].trim();
                String[] conexoes = partes[1].split(",");

                // Criar a cidade de origem
                cidadeOrigem = grafo.buscarCidadePorNome(nomeCidadeOrigem);
                if (cidadeOrigem == null) {
                    cidadeOrigem = new Cidade(nomeCidadeOrigem);
                    grafo.adicionarCidade(cidadeOrigem);
                }

                // Processar as conexões da cidade de origem
                for (String conexao : conexoes) {
                    // Use uma expressão regular para extrair o nome da cidade e o peso
                    String[] infoConexao = conexao.trim().split("\\s*\\(\\s*|\\s*\\)\\s*");

                    if (infoConexao.length != 2) {
                        System.err.println("Formato de conexão inválido: " + conexao);
                        continue;
                    }
                    String nomeCidadeDestino = infoConexao[0].trim();

                    int peso;
                    try {
                        peso = Integer.parseInt(infoConexao[1].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter peso para número inteiro: " + infoConexao[1]);
                        continue; // Pule esta iteração do loop e continue com a próxima conexão
                    }

                    Cidade cidadeDestino = grafo.buscarCidadePorNome(nomeCidadeDestino);
                    if (cidadeDestino == null) {
                        cidadeDestino = new Cidade(nomeCidadeDestino);
                        grafo.adicionarCidade(cidadeDestino);
                    }

                    Aresta aresta = new Aresta(cidadeOrigem, cidadeDestino, peso);
                    grafo.adicionarAresta(aresta);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }

        // Menu de operações
        Scanner sc = new Scanner(System.in);
        int escolha;
        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Verificar existência de estrada entre cidades.");
            System.out.println("2 - Identificar cidades inacessíveis a partir da cidade sede.");
            System.out.println("3 - Recomendar visitação a todas as cidades a partir da cidade sede.");
            System.out.println("4 - Recomendar rota para um passageiro que deseja visitar todas as cidades.");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");
            escolha = sc.nextInt();

            switch (escolha) {
                case 1:
                    sc = new Scanner(System.in);
                    System.out.println("Digite o nome da primeira cidade:");
                    String nomeCidade1 = sc.nextLine();
                    System.out.println("Digite o nome da segunda cidade:");
                    String nomeCidade2 = sc.nextLine();
                    Cidade cidade1 = grafo.buscarCidadePorNome(nomeCidade1);
                    Cidade cidade2 = grafo.buscarCidadePorNome(nomeCidade2);

                    if (cidade1 != null && cidade2 != null) {
                        if (grafo.existeEstradaEntreCidades(cidade1, cidade2)) {
                            System.out.println("Existe uma estrada entre " + cidade1 + " e " + cidade2 + ".");
                        } else {
                            System.out.println("Não existe uma estrada entre " + cidade1 + " e " + cidade2 + ".");
                        }
                    } else {
                        System.out.println("Uma ou ambas as cidades não foram encontradas.");
                    }
                    break;
                case 2:
                    // CORRIGIR NOS METODOS
                    sc = new Scanner(System.in);
                    System.out.println("Digite o nome da cidade sede:");
                    String nomeCidadeSede = sc.nextLine();
                    Cidade cidadeSede = grafo.buscarCidadePorNome(nomeCidadeSede);

                    if (cidadeSede != null) {
                        List<Cidade> inacessiveis = grafo.cidadesInacessiveis(cidadeSede);

                        if (inacessiveis.isEmpty()) {
                            System.out.println("Todas as cidades são acessíveis a partir da cidade sede.");
                        } else {
                            System.out.println("As seguintes cidades são inacessíveis a partir da cidade sede:");
                            for (Cidade inacessivel : inacessiveis) {
                                System.out.println(inacessivel.getNome());
                            }
                        }
                    } else {
                        System.out.println("Cidade sede não encontrada.");
                    }
                    break;
                case 3:
                    // COLOCAR CIDADE SEDE NO METODO E USAR O SCANNER NO MAIN

                    sc = new Scanner(System.in);
                    List<Cidade> caminhoMinimo = grafo.recomendarVisitaTodasCidades(grafo.getCidades().get(0));
                    if (caminhoMinimo == null) {
                        System.out.println("Não foi possível encontrar um caminho que visite todas as cidades.");
                    } else {
                        System.out.println("Recomendação de visitação em todas as cidades e estradas:");
                        for (Cidade cidade : caminhoMinimo) {
                            System.out.println(cidade.getNome());
                        }
                    }
                    break;
                case 4:
                    // COLOCAR CIDADE SEDE NO METODO E USAR O SCANNER NO MAIN

                    sc = new Scanner(System.in);
                    List<Cidade> cicloHamiltoniano = grafo.cicloHamiltoniano();
                    if (cicloHamiltoniano == null) {
                        System.out.println("Não foi possível encontrar um ciclo hamiltoniano.");
                    } else {
                        System.out.println("Recomendação de rota para visitar todas as cidades:");
                        for (Cidade cidade : cicloHamiltoniano) {
                            System.out.println(cidade.getNome());
                        }
                    }
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Digite um número de 1 a 5.");
            }
        } while (escolha != 5);

        sc.close();
        scanner1.close();
    }
}
