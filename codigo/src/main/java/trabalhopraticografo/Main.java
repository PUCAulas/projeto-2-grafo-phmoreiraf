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
        String nomeCidadeInicial;

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
            System.out.println("2 - Listar cidades diretamente inacessíveis a partir da cidade sede.");
            System.out.println("3 - Listar cidades completamente inacessíveis a partir da cidade sede.");
            System.out.println("4 - Recomendar visitação a todas as cidades a partir da cidade sede.");
            System.out.println("5 - Recomendar rota para um passageiro que deseja visitar todas as cidades.");
            System.out.println("6 - Sair");
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
                    sc = new Scanner(System.in);
                    System.out.println("Digite o nome da cidade sede para encontrar as cidades inacessíveis:");
                    String nomeCidadeSede = sc.nextLine();
                    Cidade cidadeSede = grafo.buscarCidadePorNome(nomeCidadeSede);

                    if (cidadeSede != null) {
                        List<Cidade> inacessiveis = grafo.cidadesDiretamenteInacessiveis(cidadeSede);
                        System.out
                                .println("Cidades diretamente inacessíveis a partir de " + cidadeSede.getNome() + ":");

                        if (inacessiveis.isEmpty()) {
                            System.out.println("Nenhuma cidade diretamente inacessível encontrada.");
                        } else {
                            for (Cidade inacessivel : inacessiveis) {
                                System.out.println(inacessivel.getNome());
                            }
                        }
                    } else {
                        System.out.println("Cidade sede não encontrada!");
                    }
                    break;

                case 3:
                    sc = new Scanner(System.in);
                    System.out.println(
                            "Digite o nome da cidade sede para encontrar as cidades completamente inacessíveis:");
                    nomeCidadeSede = sc.nextLine();
                    cidadeSede = grafo.buscarCidadePorNome(nomeCidadeSede);

                    if (cidadeSede != null) {
                        List<Cidade> inacessiveis = grafo.cidadesCompletamenteInacessiveis(cidadeSede);
                        System.out
                                .println(
                                        "Cidades completamente inacessíveis a partir de " + cidadeSede.getNome() + ":");

                        if (inacessiveis.isEmpty()) {
                            System.out.println("Nenhuma cidade completamente inacessível encontrada.");
                        } else {
                            for (Cidade inacessivel : inacessiveis) {
                                System.out.println(inacessivel.getNome());
                            }
                        }
                    } else {
                        System.out.println("Cidade sede não encontrada!");
                    }
                    break;

                case 4:
                    // CORRIGIR CASO ESTEJA ERRADO TANTO NO MAIN QUANTO NO METODO

                    sc = new Scanner(System.in);
                    // Perguntar ao usuário de qual cidade eles gostariam de começar a visitação
                    System.out.println("De qual cidade você gostaria de começar a visitação?");
                    nomeCidadeInicial = sc.nextLine();

                    cidadeSede = grafo.buscarCidadePorNome(nomeCidadeInicial);

                    if (cidadeSede != null) {
                        // Visitar todas as cidades a partir da cidade escolhida pelo usuário
                        grafo.visitarTodas(nomeCidadeInicial);
                    } else {
                        System.out.println("Cidade não encontrada!");
                    }

                    break;
                case 5:
                    // COLOCAR CIDADE SEDE NO METODO E USAR O SCANNER NO MAIN

                    sc = new Scanner(System.in);
                    // Perguntar ao usuário de qual cidade eles gostariam de começar a visitação
                    System.out.println("De qual cidade você gostaria de começar a visitação?");

                    nomeCidadeInicial = sc.nextLine();

                    cidadeSede = grafo.buscarCidadePorNome(nomeCidadeInicial);

                    if (cidadeSede != null) {

                        List<Cidade> rota = grafo.encontrarCicloHamiltoniano(cidadeSede);

                        if (rota != null) {

                            String soma = "";

                            int distanciaTotal = 0;

                            for (int i = 0; i < rota.size(); i++) {

                                Cidade origem = rota.get(i);

                                Cidade destino = rota.get((i + 1) % rota.size()); // Para retornar à cidade sede

                                int distancia = origem.getVizinhos().get(destino);

                                System.out.println(
                                        origem.getNome() + " -> " + destino.getNome() + " (" + distancia + " KM)");

                                if (!soma.isEmpty()) {

                                    soma += " + ";

                                }

                                soma += distancia;

                                distanciaTotal += distancia;

                            }

                            System.out.print("Soma das distâncias: " + soma + " = " + distanciaTotal + " KM\n");
                            System.out.println("Distância total: " + distanciaTotal + " KM");
                        } else {
                            System.out.println(
                                    "Não foi possível encontrar um ciclo Hamiltoniano a partir da cidade sede.");
                        }
                    } else {
                        System.out.println("Cidade não encontrada!");
                    }

                    break;

                case 6:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Digite um número de 1 a 6.");
            }
        } while (escolha != 6);

        sc.close();
        scanner1.close();
    }
}
