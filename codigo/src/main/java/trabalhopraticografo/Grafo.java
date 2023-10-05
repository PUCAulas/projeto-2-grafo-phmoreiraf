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

    public List<Cidade> getCidades() {
        return cidades;
    }

    // Requisito (a): Verificar se existe estrada de qualquer cidade para qualquer
    public boolean existeEstradaEntreCidades(Cidade cidadeOrigem, Cidade cidadeDestino) {
        if (cidadeOrigem == null || cidadeDestino == null) {
            throw new IllegalArgumentException("Cidades de origem e destino não podem ser nulas.");
        }
        Set<Cidade> visitadas = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();
    
        fila.offer(cidadeOrigem);
        visitadas.add(cidadeOrigem);
    
        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();
    
            if (atual.equals(cidadeDestino)) {
                return true; // Existe estrada entre as cidades
            }
    
            for (Cidade vizinho : atual.vizinhos.keySet()) {
                if (!visitadas.contains(vizinho)) {
                    fila.offer(vizinho);
                    visitadas.add(vizinho);
                }
            }
        }
    
        return false; // Não existe estrada entre as cidades
    }

    // Requisito (b): Identificar cidades inacessíveis a partir da cidade sede.
    public List<Cidade> identificarCidadesInacessiveis(Cidade cidadeSede) {
        if (cidadeSede == null) {
            throw new IllegalArgumentException("Cidade sede não pode ser nula.");
        }
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
        if (cidadeSede == null) {
            throw new IllegalArgumentException("Cidade sede não pode ser nula.");
        }
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

        if (cidadeSede == null) {
            throw new IllegalArgumentException("Cidade sede não pode ser nula.");
        }
        
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
        if (nome == null) {
            throw new IllegalArgumentException("Nome da cidade não pode ser nulo.");
        }
    
        for (Cidade cidade : cidades) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null; // Retorna null se a cidade não for encontrada
    }

    public Cidade encontrarCidadeMaisProxima(Cidade origem) {
        Map<Cidade, Integer> distancias = new HashMap<>();
        Set<Cidade> visitadas = new HashSet<>();

        // Inicializa as distâncias com um valor máximo (infinito)
        for (Cidade cidade : cidades) {
            distancias.put(cidade, Integer.MAX_VALUE);
        }

        // A distância da cidade de origem para ela mesma é zero
        distancias.put(origem, 0);

        while (!visitadas.containsAll(cidades)) {
            Cidade cidadeMaisProxima = null;
            int distanciaMinima = Integer.MAX_VALUE;

            for (Cidade cidade : cidades) {
                if (!visitadas.contains(cidade) && distancias.get(cidade) < distanciaMinima) {
                    cidadeMaisProxima = cidade;
                    distanciaMinima = distancias.get(cidade);
                }
            }

            if (cidadeMaisProxima == null) {
                break; // Todas as cidades inalcançáveis foram visitadas
            }

            visitadas.add(cidadeMaisProxima);

            for (Map.Entry<Cidade, Integer> vizinhoEntry : cidadeMaisProxima.vizinhos.entrySet()) {
                Cidade vizinho = vizinhoEntry.getKey();
                int pesoAresta = vizinhoEntry.getValue();
                int distanciaTotal = distancias.get(cidadeMaisProxima) + pesoAresta;

                if (distanciaTotal < distancias.get(vizinho)) {
                    distancias.put(vizinho, distanciaTotal);
                }
            }
        }

        // Encontre a cidade mais próxima que ainda não foi visitada
        Cidade cidadeMaisProximaNaoVisitada = null;
        int distanciaMinimaNaoVisitada = Integer.MAX_VALUE;

        for (Cidade cidade : cidades) {
            if (!visitadas.contains(cidade) && distancias.get(cidade) < distanciaMinimaNaoVisitada) {
                cidadeMaisProximaNaoVisitada = cidade;
                distanciaMinimaNaoVisitada = distancias.get(cidade);
            }
        }

        return cidadeMaisProximaNaoVisitada;
    }
    

}
