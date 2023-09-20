package trabalhopraticografo;

import java.util.*;

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

    // Requisito (a): Verificar se existe estrada de qualquer cidade para qualquer
    // cidade.
    public boolean existeEstradaEntreCidades(Cidade cidadeOrigem, Cidade cidadeDestino) {
        Set<Cidade> visitadas = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        fila.offer(cidadeOrigem);
        visitadas.add(cidadeOrigem);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            if (atual.equals(cidadeDestino)) {
                return true;
            }

            for (Cidade vizinho : atual.vizinhos.keySet()) {
                if (!visitadas.contains(vizinho)) {
                    fila.offer(vizinho);
                    visitadas.add(vizinho);
                }
            }
        }

        return false;
    }

    // Requisito (b): Identificar cidades inacessíveis a partir da cidade sede.
    public List<Cidade> identificarCidadesInacessiveis(Cidade cidadeSede) {
        List<Cidade> cidadesInacessiveis = new ArrayList<>();
        Set<Cidade> visitadas = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        fila.offer(cidadeSede);
        visitadas.add(cidadeSede);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            for (Cidade vizinho : atual.vizinhos.keySet()) {
                if (!visitadas.contains(vizinho)) {
                    fila.offer(vizinho);
                    visitadas.add(vizinho);
                }
            }
        }

        // Todas as cidades acessíveis foram visitadas. As inacessíveis estão na lista
        // de não visitadas.
        for (Cidade cidade : cidades) {
            if (!visitadas.contains(cidade)) {
                cidadesInacessiveis.add(cidade);
            }
        }

        return cidadesInacessiveis;
    }

    // Requisito (c): Recomendar visitação em todas as cidades e estradas.
    public List<Cidade> recomendarVisitaTodasCidades(Cidade cidadeSede) {
        List<Cidade> rotaRecomendada = new ArrayList<>();
        Set<Cidade> visitadas = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        fila.offer(cidadeSede);
        visitadas.add(cidadeSede);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();
            rotaRecomendada.add(atual);

            for (Cidade vizinho : atual.vizinhos.keySet()) {
                if (!visitadas.contains(vizinho)) {
                    fila.offer(vizinho);
                    visitadas.add(vizinho);
                }
            }
        }

        return rotaRecomendada;
    }

    // Requisito (d): Recomendar uma rota para um passageiro que deseja visitar
    // todas as cidades.
    public List<Cidade> recomendarRotaPassageiro(Cidade cidadeSede) {
        List<Cidade> rotaRecomendada = new ArrayList<>();
        //Set<Cidade> visitadas = new HashSet<>();
        Map<Cidade, Integer> distancias = new HashMap<>();
        Map<Cidade, Cidade> predecessores = new HashMap<>();

        // Inicialização das distâncias com um valor elevado.
        for (Cidade cidade : cidades) {
            distancias.put(cidade, Integer.MAX_VALUE);
        }

        distancias.put(cidadeSede, 0);

        PriorityQueue<Cidade> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        filaPrioridade.offer(cidadeSede);

        while (!filaPrioridade.isEmpty()) {
            Cidade atual = filaPrioridade.poll();

            for (Map.Entry<Cidade, Integer> vizinhoEntry : atual.vizinhos.entrySet()) {
                Cidade vizinho = vizinhoEntry.getKey();
                int pesoAresta = vizinhoEntry.getValue();
                int novaDistancia = distancias.get(atual) + pesoAresta;

                if (novaDistancia < distancias.get(vizinho)) {
                    filaPrioridade.remove(vizinho); // Atualiza o valor na fila de prioridade.
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, atual);
                    filaPrioridade.offer(vizinho);
                }
            }
        }
        // Reconstrói a rota a partir dos predecessores.
        Cidade cidadeAtual = cidadeSede;
        while (predecessores.containsKey(cidadeAtual)) {
            rotaRecomendada.add(cidadeAtual);
            cidadeAtual = predecessores.get(cidadeAtual);
        }

        rotaRecomendada.add(cidadeSede); // Retornar à cidade sede no final da rota.

        Collections.reverse(rotaRecomendada);

        return rotaRecomendada;
    }

    public Cidade buscarCidadePorNome(String nome) {
        for (Cidade cidade : cidades) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null; // Retorna null se a cidade não for encontrada
    }
    /*
     * private Cidade encontrarCidadeMaisProximaNaoVisitada(Cidade cidade,
     * Set<Cidade> visitadas) {
     * Cidade maisProxima = null;
     * int menorDistancia = Integer.MAX_VALUE;
     * 
     * for (Map.Entry<Cidade, Integer> vizinhoEntry : cidade.vizinhos.entrySet()) {
     * Cidade vizinho = vizinhoEntry.getKey();
     * int distancia = vizinhoEntry.getValue();
     * 
     * if (!visitadas.contains(vizinho) && distancia < menorDistancia) {
     * maisProxima = vizinho;
     * menorDistancia = distancia;
     * }
     * }
     * 
     * return maisProxima;
     * }
     */

}
