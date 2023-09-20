package trabalhopraticografo;

import java.util.*;

public class Cidade {
    String nome;
    int id;
    Map<Cidade, Integer> vizinhos;  // Mapeia as cidades vizinhas para suas distâncias.
    //List<Cidade> cidades;  // Lista de cidade

    public Cidade(String nome, int id) {
        this.nome = nome;
        this.id = id;
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
