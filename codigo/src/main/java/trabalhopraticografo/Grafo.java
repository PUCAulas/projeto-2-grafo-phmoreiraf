package trabalhopraticografo;

import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {

    List<Cidade> cidades;
    List<Aresta> arestas;

    public Grafo() {
        cidades = new ArrayList<>();
        arestas = new ArrayList<>();
    }

    public void adicionarCidade(Cidade cidade) {
        cidades.add(cidade);
    }

    public void adicionarAresta(Aresta aresta) {
        arestas.add(aresta);
    }

    

    private Cidade encontrarCidadeMaisProximaNaoVisitada(Cidade cidade, Set<Cidade> visitadas) {
        Cidade maisProxima = null;
        int menorDistancia = Integer.MAX_VALUE;

        for (Map.Entry<Cidade, Integer> vizinhoEntry : cidade.vizinhos.entrySet()) {
            Cidade vizinho = vizinhoEntry.getKey();
            int distancia = vizinhoEntry.getValue();

            if (!visitadas.contains(vizinho) && distancia < menorDistancia) {
                maisProxima = vizinho;
                menorDistancia = distancia;
            }
        }

        return maisProxima;
    }

}
