package trabalhopraticografo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/registro.txt";
        try {
            Scanner scanner = new Scanner(new File(arquivoEntrada));
            Grafo grafo = new Grafo();
            BFS bfs = new BFS(grafo);
            Cidade cidadeOrigem;
            Cidade cidadeDestino;

            // Processar as linhas do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(":");
                String nomeCidadeOrigem = partes[0].trim();
                String[] conexoes = partes[1].split(",");

                // Criar a cidade de origem
                cidadeOrigem = new Cidade(nomeCidadeOrigem);
                grafo.adicionarCidade(cidadeOrigem);

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

                    Aresta aresta = new Aresta(cidadeOrigem, new Cidade(nomeCidadeDestino), peso);
                    grafo.adicionarAresta(aresta);
                }
                
            }
            scanner.close();

            // Menu de operações
            Scanner sc = new Scanner(System.in);
            int escolha;
            do {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Busca em largura.");
                System.out.println("2 - Busca em profundidade.");
                System.out.println("3 - Verificar existência de estrada entre cidades.");
                System.out.println("4 - Identificar cidades inacessíveis a partir da cidade sede.");
                System.out.println("5 - Recomendar visitação a todas as cidades a partir da cidade sede.");
                System.out.println("6 - Recomendar rota para um passageiro que deseja visitar todas as cidades.");
                System.out.println("7 - Sair");
                System.out.print("Opção: ");
                escolha = sc.nextInt();

                switch (escolha) {
                    case 1:
                    System.out.print("Digite o nome da cidade de origem para a busca em largura: ");
                    sc.nextLine(); // Limpar o buffer
                    String nomeCidadeOrigem = sc.nextLine();
                    cidadeOrigem = grafo.buscarCidadePorNome(nomeCidadeOrigem);
                    if (cidadeOrigem != null) {
                        List<Cidade> resultadoBFS = bfs.buscaEmLargura(cidadeOrigem);
                        System.out.println("Resultado da busca em largura a partir de " + cidadeOrigem.getNome() + ":");
                        for (Cidade cidade : resultadoBFS) {
                            System.out.println("- " + cidade.getNome());
                        }
                    } else {
                        System.out.println("Cidade de origem não encontrada.");
                    }
                    break;
                    case 2:
                    System.out.print("Digite o nome da cidade de origem para a busca em profundidade: ");
                        sc.nextLine(); // Limpar o buffer
                        String nomeCidadeOrigemP = sc.nextLine();
                        Cidade cidadeOrigemP = grafo.buscarCidadePorNome(nomeCidadeOrigemP);

                        if (cidadeOrigemP != null) {
                            List<Cidade> resultadoDFS = bfs.buscaEmProfundidade(cidadeOrigemP);
                            System.out.println("Resultado da busca em profundidade a partir de " + cidadeOrigemP.getNome() + ":");
                            for (Cidade cidade : resultadoDFS) {
                                System.out.println("- " + cidade.getNome());
                            }
                        } else {
                            System.out.println("Cidade de origem não encontrada.");
                        }
                    break;
                    case 3:
                        System.out.print("Digite o nome da cidade de origem: ");
                        sc.nextLine(); // Limpar o buffer
                        String origem = sc.nextLine();
                        System.out.print("Digite o nome da cidade de destino: ");
                        String destino = sc.nextLine();

                        cidadeOrigem = grafo.buscarCidadePorNome(origem);
                        cidadeDestino = grafo.buscarCidadePorNome(destino);

                        if (cidadeOrigem != null && cidadeDestino != null) {
                            if (grafo.existeEstradaEntreCidades(cidadeOrigem, cidadeDestino)) {
                                System.out.println("Existe uma estrada entre " + cidadeOrigem.getNome() + " e " + cidadeDestino.getNome());
                            } else {
                                System.out.println("Não existe uma estrada entre " + cidadeOrigem.getNome() + " e " + cidadeDestino.getNome());
                            }
                        } else {
                            System.out.println("Cidade de origem ou cidade de destino não encontrada.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o nome da cidade sede: ");
                        sc.nextLine(); // Limpar o buffer
                        String sede = sc.nextLine();
                        Cidade cidadeSede = grafo.buscarCidadePorNome(sede);

                        if (cidadeSede != null) {
                            List<Cidade> cidadesInacessiveis = grafo.identificarCidadesInacessiveis(cidadeSede);
                            if (!cidadesInacessiveis.isEmpty()) {
                                System.out.println("Cidades inacessíveis a partir de " + cidadeSede.getNome() + ":");
                                for (Cidade inacessivel : cidadesInacessiveis) {
                                    System.out.println("- " + inacessivel.getNome());
                                }
                            } else {
                                System.out.println("Todas as cidades são acessíveis a partir de " + cidadeSede.getNome());
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
                        }
                        break;
                    case 5:
                        System.out.print("Digite o nome da cidade sede: ");
                        sc.nextLine(); // Limpar o buffer
                        String cidadeSedeRecomendacao = sc.nextLine();
                        Cidade cidadeSedeRecomendacaoObj = grafo.buscarCidadePorNome(cidadeSedeRecomendacao);

                        if (cidadeSedeRecomendacaoObj != null) {
                            List<Cidade> recomendacaoVisita = grafo.recomendarVisitaTodasCidades(cidadeSedeRecomendacaoObj);
                            System.out.println("Recomendação de visitação a partir de " + cidadeSedeRecomendacaoObj.getNome() + ":");
                            for (Cidade cidade : recomendacaoVisita) {
                                System.out.println("- " + cidade.getNome());
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
                        }
                        break;
                    case 6:
                    System.out.print("Digite o nome da cidade sede: ");
                    sc.nextLine(); // Limpar o buffer
                    String cidadeSedeRota = sc.nextLine();
                    Cidade cidadeSedeRotaObj = grafo.buscarCidadePorNome(cidadeSedeRota);

                    if (cidadeSedeRotaObj != null) {
                        List<Cidade> rotaPassageiro = grafo.recomendarRotaPassageiro(cidadeSedeRotaObj);
                        System.out.println("Rota recomendada para um passageiro que deseja visitar todas as cidades:");
                        for (Cidade cidade : rotaPassageiro) {
                            System.out.println("- " + cidade.getNome());
                        }
                    } else {
                        System.out.println("Cidade sede não encontrada.");
                    }
                    case 7:
                        System.out.println("Saindo do programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Digite um número de 1 a 5.");
                }
            } while (escolha != 7);

            sc.close();

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }

        
    }
}

