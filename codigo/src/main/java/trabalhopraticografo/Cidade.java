package trabalhopraticografo;

import java.util.*;

public class Cidade {
    private static int proxId = 0;

    String nome;
    int id;
    Map<Cidade, Integer> vizinhos; // Mapeia as cidades vizinhas para suas distâncias.

    public Cidade(String nome) {
        this.id = proxId++;
        this.nome = nome;
        this.vizinhos = new HashMap<>();
    }

    public void adicionarVizinho(Cidade cidade, int distancia) {
        vizinhos.put(cidade, distancia);
    }

    public Map<Cidade, Integer> getVizinhos() {
        return vizinhos;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nome;
    }
}
