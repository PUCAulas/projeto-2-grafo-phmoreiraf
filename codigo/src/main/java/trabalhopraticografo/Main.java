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
                    String[] infoConexao = conexao.trim().split("\\s+");
                    String nomeCidadeDestino = infoConexao[0].trim();

                    int peso;
                    try {
                        peso = Integer.parseInt(infoConexao[1].replace("(", "").replace(")", "").trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter peso para número inteiro: " + infoConexao[1]);
                        continue; // Pule esta iteração do loop e continue com a próxima conexão
                    }

                    // Criar uma aresta e adicionar ao grafo
                    Aresta aresta = new Aresta(cidadeOrigem, new Cidade(nomeCidadeDestino), peso);
                    grafo.adicionarAresta(aresta);
                }
            }

            // Menu de operações
            Scanner sc = new Scanner(System.in);
            int escolha;

            do {
                System.out.println("\nEscolha uma operação:");
                System.out.println("1. Verificar se existe estrada entre cidades");
                System.out.println("2. Identificar cidades inacessíveis");
                System.out.println("3. Recomendar visita a todas as cidades");
                System.out.println("4. Recomendar rota para um passageiro");
                System.out.println("5. Sair");
                System.out.print("Escolha: ");
                escolha = sc.nextInt();
                sc.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Informe o nome da cidade de origem: ");
                        String origemNome = sc.nextLine().toLowerCase(); // Converter para letras minúsculas
                        System.out.print("Informe o nome da cidade de destino: ");
                        String destinoNome = sc.nextLine().toLowerCase(); // Converter para letras minúsculas

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
                        String sedeNome = sc.nextLine().toLowerCase(); // Converter para letras minúsculas

                        Cidade cidadeSedeInacessivel = grafo.buscarCidadePorNome(sedeNome);

                        if (cidadeSedeInacessivel != null) {
                            List<Cidade> cidadesInacessiveis = grafo
                                    .identificarCidadesInacessiveis(cidadeSedeInacessivel);
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
                        String sedeRecomendacaoNome = sc.nextLine().toLowerCase(); // Converter para letras minúsculas

                        Cidade cidadeSedeRecomendacao = grafo.buscarCidadePorNome(sedeRecomendacaoNome);

                        if (cidadeSedeRecomendacao != null) {
                            List<Cidade> recomendacaoVisita = grafo
                                    .recomendarVisitaTodasCidades(cidadeSedeRecomendacao);
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
                        String sedeRotaNome = sc.nextLine().toLowerCase(); // Converter para letras minúsculas

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
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }
    }

}
