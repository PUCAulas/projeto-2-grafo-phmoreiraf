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
                        System.out.println(peso);
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

            // O restante do seu código permanece o mesmo

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivoEntrada);
        }
    }
}

