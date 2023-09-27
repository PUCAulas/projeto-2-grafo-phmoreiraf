package trabalhopraticografo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String arquivoEntrada = "/src/main/java/trabalhopraticografo/arquivo/registro.txt";

        try {
            // Criar um Scanner para ler o arquivo
            Scanner scanner = new Scanner(new File(arquivoEntrada));

            // Criar um grafo
            Grafo grafo = new Grafo();

            // Ler a cidade sede da rodoviária
            String cidadeSedeNome = scanner.nextLine();
            Cidade cidadeSede = new Cidade(cidadeSedeNome, 0);

            // Adicionar a cidade sede ao grafo
            grafo.adicionarCidade(cidadeSede);

            // Ler as cidades e suas conexões a partir do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(":");
                String nomeCidade = partes[0].trim();
                String[] conexoes = partes[1].split(",");

                Cidade cidade = new Cidade(nomeCidade, 0);
                grafo.adicionarCidade(cidade);

                for (String conexao : conexoes) {
                    String[] infoConexao = conexao.trim().split("\\s+");
                    String cidadeDestinoNome = infoConexao[0].trim();
                    int peso = Integer.parseInt(infoConexao[1].replace("(", "").replace(")", "").trim());

                    // Criar uma aresta e adicionar ao grafo
                    Aresta aresta = new Aresta(cidade, new Cidade(cidadeDestinoNome, 0), peso);
                    grafo.adicionarAresta(aresta);
                }
            }

            // Menu de operações
            Scanner input = new Scanner(System.in);
            int escolha;

            do {
                System.out.println("\nEscolha uma operação:");
                System.out.println("1. Verificar se existe estrada entre cidades");
                System.out.println("2. Identificar cidades inacessíveis");
                System.out.println("3. Recomendar visita a todas as cidades");
                System.out.println("4. Recomendar rota para um passageiro");
                System.out.println("5. Sair");
                System.out.print("Escolha: ");
                escolha = input.nextInt();

                switch (escolha) {
                    case 1:
                        System.out.print("Informe o nome da cidade de origem: ");
                        input.nextLine(); // Consumir a quebra de linha
                        String origemNome = input.nextLine();
                        System.out.print("Informe o nome da cidade de destino: ");
                        String destinoNome = input.nextLine();

                        Cidade origem = grafo.buscarCidadePorNome(origemNome);
                        Cidade destino = grafo.buscarCidadePorNome(destinoNome);

                        if (origem != null && destino != null) {
                            boolean existeEstrada = grafo.existeEstradaEntreCidades(origem, destino);
                            if (existeEstrada) {
                                System.out.println("Existe estrada entre " + origemNome + " e " + destinoNome);
                            } else {
                                System.out.println("Não existe estrada entre " + origemNome + " e " + destinoNome);
                            }
                        } else {
                            System.out.println("Cidades não encontradas.");
                        }
                        break;
                    case 2:
                        System.out.print("Informe o nome da cidade sede: ");
                        input.nextLine(); // Consumir a quebra de linha
                        String sedeNome = input.nextLine();

                        Cidade cidadeSedeInacessivel = grafo.buscarCidadePorNome(sedeNome);

                        if (cidadeSedeInacessivel != null) {
                            List<Cidade> cidadesInacessiveis = grafo.identificarCidadesInacessiveis(cidadeSedeInacessivel);
                            if (!cidadesInacessiveis.isEmpty()) {
                                System.out.println("Cidades inacessíveis a partir de " + sedeNome + ": ");
                                for (Cidade cidadeInacessivel : cidadesInacessiveis) {
                                    System.out.println(cidadeInacessivel.getNome());
                                }
                            } else {
                                System.out.println("Todas as cidades são acessíveis a partir de " + sedeNome);
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
                        }
                        break;
                    case 3:
                        System.out.print("Informe o nome da cidade sede: ");
                        input.nextLine(); // Consumir a quebra de linha
                        String sedeRecomendacaoNome = input.nextLine();

                        Cidade cidadeSedeRecomendacao = grafo.buscarCidadePorNome(sedeRecomendacaoNome);

                        if (cidadeSedeRecomendacao != null) {
                            List<Cidade> recomendacaoVisita = grafo.recomendarVisitaTodasCidades(cidadeSedeRecomendacao);
                            if (!recomendacaoVisita.isEmpty()) {
                                System.out.println("Recomendação de visitação em todas as cidades: ");
                                for (Cidade cidadeVisita : recomendacaoVisita) {
                                    System.out.println(cidadeVisita.getNome());
                                }
                            } else {
                                System.out.println("Não foi possível recomendar visitação.");
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
                        }
                        break;
                    case 4:
                        System.out.print("Informe o nome da cidade sede: ");
                        input.nextLine(); // Consumir a quebra de linha
                        String sedeRotaNome = input.nextLine();

                        Cidade cidadeSedeRota = grafo.buscarCidadePorNome(sedeRotaNome);

                        if (cidadeSedeRota != null) {
                            List<Cidade> rotaPassageiro = grafo.recomendarRotaPassageiro(cidadeSedeRota);
                            if (!rotaPassageiro.isEmpty()) {
                                System.out.println("Rota recomendada para o passageiro: ");
                                for (Cidade cidadeRota : rotaPassageiro) {
                                    System.out.println(cidadeRota.getNome());
                                }
                            } else {
                                System.out.println("Não foi possível recomendar uma rota.");
                            }
                        } else {
                            System.out.println("Cidade sede não encontrada.");
                        }
                        break;
                    case 5:
                        System.out.println("Saindo do programa.");
                        break;
                    default:
                        System.out.println("Escolha inválida. Tente novamente.");
                        break;
                }
            } while (escolha != 5);

            // Feche os Scanners
            scanner.close();
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }
    }

    /*private static void lerArquivoDeDados(String arquivo, Grafo grafo) {
        // Implemente a leitura do arquivo e a criação das cidades e arestas
    }*/
}
