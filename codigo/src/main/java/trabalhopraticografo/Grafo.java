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
        List<Cidade> rotaPassageiro = new ArrayList<>();
        if (cidades.size() <= 1) {
            return rotaPassageiro; // Não é possível criar uma rota com apenas uma cidade
        }

        List<Cidade> visitacao = recomendarVisitaTodasCidades(cidadeSede);
        if (visitacao.isEmpty()) {
            return rotaPassageiro; // Não é possível criar uma rota se nenhuma cidade for acessível
        }

        // Remover a cidade sede da lista, pois o passageiro já está na cidade sede
        visitacao.remove(cidadeSede);

        // Criar um grafo Hamiltoniano a partir das cidades visitadas
        List<Cidade> grafoHamiltoniano = new ArrayList<>(visitacao);
        grafoHamiltoniano.add(cidadeSede);

        // Calcular a rota Hamiltoniana
        List<Cidade> rotaHamiltoniana = encontrarRotaHamiltoniana(grafoHamiltoniano);

        if (rotaHamiltoniana == null) {
            System.out.println("Não é possível encontrar uma rota Hamiltoniana a partir da cidade sede.");
            return rotaPassageiro;
        }

        // Ajustar a rota para começar na cidade sede
        int indiceCidadeSede = rotaHamiltoniana.indexOf(cidadeSede);
        if (indiceCidadeSede != -1) {
            List<Cidade> rotaFinal = new ArrayList<>();
            int tamanho = rotaHamiltoniana.size();

            for (int i = 0; i < tamanho; i++) {
                rotaFinal.add(rotaHamiltoniana.get((i + indiceCidadeSede) % tamanho));
            }

            return rotaFinal;
        }

        return rotaPassageiro;
    }

    private List<Cidade> encontrarRotaHamiltoniana(List<Cidade> grafo) {
        int totalCidades = grafo.size();
        List<Cidade> rotaAtual = new ArrayList<>();
        Set<Cidade> visitadas = new HashSet<>();

        rotaAtual.add(grafo.get(0));
        visitadas.add(grafo.get(0));

        boolean encontrouRota = encontrarRotaHamiltonianaRecursiva(grafo, rotaAtual, visitadas, totalCidades);

        if (encontrouRota) {
            return rotaAtual;
        } else {
            return null;
        }
    }

    private boolean encontrarRotaHamiltonianaRecursiva(List<Cidade> grafo, List<Cidade> rotaAtual, Set<Cidade> visitadas, int totalCidades) {
        if (rotaAtual.size() == totalCidades) {
            // Todas as cidades foram visitadas
            return true;
        }

        Cidade cidadeAtual = rotaAtual.get(rotaAtual.size() - 1);
        for (Cidade vizinho : cidadeAtual.vizinhos.keySet()) {
            if (!visitadas.contains(vizinho)) {
                rotaAtual.add(vizinho);
                visitadas.add(vizinho);

                if (encontrarRotaHamiltonianaRecursiva(grafo, rotaAtual, visitadas, totalCidades)) {
                    return true;
                }

                rotaAtual.remove(rotaAtual.size() - 1);
                visitadas.remove(vizinho);
            }
        }

        return false;
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


    public boolean isHamiltoniano() {
        if (cidades.isEmpty()) {
            return false; // O grafo não possui cidades.
        }

        // Comece com a primeira cidade como ponto de partida
        Cidade cidadeInicial = cidades.get(0);

        // Inicie a busca por um ciclo Hamiltoniano a partir da cidade inicial
        return isHamiltonianoRecursivo(cidadeInicial, cidadeInicial, new HashSet<>(), 1);
    }

    private boolean isHamiltonianoRecursivo(Cidade cidadeAtual, Cidade cidadeInicial, Set<Cidade> visitadas, int contador) {
        visitadas.add(cidadeAtual);

        if (contador == cidades.size() && cidadeAtual.vizinhos.containsKey(cidadeInicial)) {
            // Encontrou um ciclo Hamiltoniano
            return true;
        }

        for (Cidade vizinho : cidadeAtual.vizinhos.keySet()) {
            if (!visitadas.contains(vizinho)) {
                if (isHamiltonianoRecursivo(vizinho, cidadeInicial, new HashSet<>(visitadas), contador + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

	
	}
    


