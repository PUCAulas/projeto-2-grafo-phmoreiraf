package trabalhopraticografo;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        lerArquivoDeDados("dados.txt", grafo);

        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("Menu:");
            System.out.println("1. Verificar existência de estrada entre cidades");
            System.out.println("2. Identificar cidades inacessíveis");
            System.out.println("3. Recomendar visitação em todas as cidades e estradas");
            System.out.println("4. Recomendar rota para passageiro");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Implemente a verificação de estrada entre cidades
                    break;
                case 2:
                    // Implemente a identificação de cidades inacessíveis
                    break;
                case 3:
                    // Implemente a recomendação de visitação
                    break;
                case 4:
                    // Implemente a recomendação de rota para passageiro
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (escolha != 5);

        scanner.close();
    }

    private static void lerArquivoDeDados(String arquivo, Grafo grafo) {
        // Implemente a leitura do arquivo e a criação das cidades e arestas
    }
}
