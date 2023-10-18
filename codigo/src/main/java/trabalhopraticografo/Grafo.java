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
        aresta.getOrigem().adicionarVizinho(aresta.getDestino(), aresta.getPeso());
        aresta.getDestino().adicionarVizinho(aresta.getOrigem(), aresta.getPeso());
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    // Requisito (a): Verificar se existe estrada de qualquer cidade para qualquer
    public boolean existeEstradaEntreCidades(Cidade origem, Cidade destino) {
        if (origem != null && destino != null) {
            return origem.getVizinhos().containsKey(destino);
        } else {
            return false;
        }
    }

    public boolean existeEstradaDeQualquerParaQualquer() {
        List<Cidade> cidades = getCidades();

        for (Cidade cidadeOrigem : cidades) {
            for (Cidade cidadeDestino : cidades) {
                if (cidadeOrigem != cidadeDestino && !existeEstradaEntreCidades(cidadeOrigem, cidadeDestino)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Listar todas as cidades DIRETAMENTE inacessíveis a partir de uma cidade sede
    public List<Cidade> cidadesDiretamenteInacessiveis(Cidade cidadeSede) {
        List<Cidade> inacessiveis = new ArrayList<>();
        
        if (cidadeSede == null) {
            return inacessiveis;
        }
        
        Set<Cidade> acessiveis = new HashSet<>();
        
        for (Cidade cidade : cidadeSede.getVizinhos().keySet()) {
            acessiveis.add(cidade);
        }
        
        for (Cidade cidade : cidades) {
            if (!cidade.equals(cidadeSede) && !acessiveis.contains(cidade)) {
                inacessiveis.add(cidade);
            }
        }
        
        return inacessiveis;
    }

    // Requisito (b): Listar todas as cidades inacessíveis a partir de uma cidade sede
    public List<Cidade> cidadesCompletamenteInacessiveis(Cidade cidadeSede) {
        List<Cidade> inacessiveis = new ArrayList<>();
        
        if (cidadeSede == null) {
            return inacessiveis;
        }
        
        Set<Cidade> acessiveis = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();
        
        acessiveis.add(cidadeSede);
        fila.add(cidadeSede);
        
        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();
            
            for (Cidade vizinho : atual.getVizinhos().keySet()) {
                if (!acessiveis.contains(vizinho)) {
                    acessiveis.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }
        
        for (Cidade cidade : cidades) {
            if (!acessiveis.contains(cidade)) {
                inacessiveis.add(cidade);
            }
        }
        
        return inacessiveis;
    }


    // Requisito (c): Recomendar visitação em todas as cidades e estradas.
    public List<Cidade> recomendarVisitaTodasCidades(Cidade origem) {
        if (origem == null || !cidades.contains(origem)) {
            return Collections.emptyList();
        }

        Map<Cidade, Integer> distancias = new HashMap<>();
        Map<Cidade, Cidade> anteriores = new HashMap<>();
        PriorityQueue<Cidade> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (Cidade cidade : cidades) {
            if (cidade == origem) {
                distancias.put(cidade, 0);
            } else {
                distancias.put(cidade, Integer.MAX_VALUE);
            }
            fila.add(cidade);
        }

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            for (Map.Entry<Cidade, Integer> vizinho : atual.getVizinhos().entrySet()) {
                int distanciaAlternativa = distancias.get(atual) + vizinho.getValue();
                if (distanciaAlternativa < distancias.get(vizinho.getKey())) {
                    fila.remove(vizinho.getKey());
                    distancias.put(vizinho.getKey(), distanciaAlternativa);
                    anteriores.put(vizinho.getKey(), atual);
                    fila.add(vizinho.getKey());
                }
            }
        }

        List<Cidade> caminho = new ArrayList<>();
        for (Cidade cidade : anteriores.keySet()) {
            caminho.add(cidade);
        }

        return caminho;
    }

    // Requisito (d): Recomendar uma rota para um passageiro que deseja visitar
    // todas as cidades.

    public List<Cidade> cicloHamiltoniano() {
        List<Cidade> solucao = new ArrayList<>();

        if (cidades.size() > 0) {
            solucao.add(cidades.get(0));

            if (cicloHamiltoniano(solucao, cidades.get(0))) {
                return solucao;
            }
        }

        return null;
    }

    private boolean cicloHamiltoniano(List<Cidade> solucao, Cidade cidadeAtual) {
        if (solucao.size() == cidades.size()) {
            return cidadeAtual.getVizinhos().containsKey(solucao.get(0));
        }

        for (Cidade vizinho : cidadeAtual.getVizinhos().keySet()) {
            if (!solucao.contains(vizinho)) {
                solucao.add(vizinho);

                if (cicloHamiltoniano(solucao, vizinho)) {
                    return true;
                }

                solucao.remove(vizinho);
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

    public boolean isHamiltoniano(List<Cidade> rota) {
        Set<Cidade> visitadas = new HashSet<>(rota);

        if (visitadas.size() != cidades.size() + 1) {
            return false;
        }

        if (!rota.get(0).equals(rota.get(rota.size() - 1))) {
            return false;
        }

        Set<Cidade> cidadesVisitadas = new HashSet<>(rota);
        Cidade cidadeAnterior = null;

        for (Cidade cidade : rota) {
            if (cidadeAnterior != null && !cidadeAnterior.vizinhos.containsKey(cidade)) {
                return false;
            }

            cidadeAnterior = cidade;
        }

        return cidadesVisitadas.size() == cidades.size();
    }
    
    public List<Cidade> obterTodasAsCidades() {
        List<Cidade> todasAsCidades = new ArrayList<>();

        for (Cidade cidade : cidades) {
            todasAsCidades.add(cidade);
        }

        return todasAsCidades;
    }
}