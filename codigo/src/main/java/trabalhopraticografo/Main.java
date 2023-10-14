package trabalhopraticografo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String arquivoEntrada = "codigo/src/main/java/trabalhopraticografo/arquivo/registro copy.txt";
        try {
            Scanner scanner = new Scanner(new File(arquivoEntrada));
            Grafo grafo = new Grafo();

            // Processar as linhas do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(":");
                String nomeCidadeOrigem = partes[0].trim();
                String[] conexoes = partes[1].split(",");

                // Criar a cidade de origem
                Cidade cidadeOrigem = new Cidade(nomeCidadeOrigem);
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

                    // Criar uma aresta e adicionar ao grafo
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
                System.out.println("1 - Verificar existência de estrada entre cidades.");
                System.out.println("2 - Identificar cidades inacessíveis a partir da cidade sede.");
                System.out.println("3 - Recomendar visitação a todas as cidades a partir da cidade sede.");
                System.out.println("4 - Recomendar rota para um passageiro a partir da cidade sede.");
                System.out.println("5. Sair");
                System.out.print("Opção: ");
                escolha = sc.nextInt();

                switch (escolha) {
                    case 1:
                        // Requisito (a): Verificar se existe estrada de qualquer cidade para qualquer outra.
                        System.out.println("Digite o nome da cidade de origem:");
                        String origem = scanner.next().toLowerCase();
                        System.out.println("Digite o nome da cidade de destino:");
                        String destino = scanner.next().toLowerCase();

                        Cidade cidadeOrigem = grafo.buscarCidadePorNome(origem);
                        Cidade cidadeDestino = grafo.buscarCidadePorNome(destino);

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
                    case 2:
                        // Requisito (b): Identificar cidades inacessíveis a partir da cidade sede.
                        System.out.println("Digite o nome da cidade sede:");
                        String sede = scanner.next().toLowerCase();
                        Cidade cidadeSede = grafo.buscarCidadePorNome(sede);

                        if (cidadeSede != null) {
                            List<Cidade> cidadesInacessiveis = grafo.identificarCidadesInacessiveis(cidadeSede);
                            if (cidadesInacessiveis.size() > 0) {
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
                    case 3:
                        // Requisito (c): Recomendar visitação a todas as cidades a partir da cidade sede.
                        System.out.println("Digite o nome da cidade sede:");
                        String cidadeSedeRecomendacao = scanner.next().toLowerCase();
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
                    case 4:
                        // Requisito (d): Recomendar rota para um passageiro a partir da cidade sede.
                        System.out.println("Digite o nome da cidade sede:");
                        String cidadeSedeRota = scanner.next().toLowerCase();
                        Cidade cidadeSedeRotaObj = grafo.buscarCidadePorNome(cidadeSedeRota);

                        if (cidadeSedeRotaObj != null) {
                            List<Cidade> rotaPassageiro = grafo.recomendarRotaPassageiro(cidadeSedeRotaObj);
                            System.out.println("Rota recomendada para o passageiro a partir de " + cidadeSedeRotaObj.getNome() + ":");
                            for (Cidade cidade : rotaPassageiro) {
                                System.out.println("- " + cidade.getNome());
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
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

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }
    }
}

