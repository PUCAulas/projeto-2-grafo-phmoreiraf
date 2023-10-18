package trabalhopraticografo;

import java.util.*;

public class BFS {
    private Grafo grafo;

    public BFS(Grafo grafo) {
        this.grafo = grafo;
    }

    // Requisito (b): Identificar cidades inacessíveis a partir da cidade sede. BFS
    public List<Cidade> buscaEmLargura(Cidade origemBFS) {
        List<Cidade> resultadoBFS = new ArrayList<>();
        Queue<Cidade> fila = new LinkedList<>();
        Set<Cidade> visitadas = new HashSet<>();

        fila.offer(origemBFS);
        visitadas.add(origemBFS);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();
            resultadoBFS.add(atual);

            for (Cidade vizinho : atual.vizinhos.keySet()) {
                if (!visitadas.contains(vizinho)) {
                    fila.offer(vizinho);
                    visitadas.add(vizinho);
                }
            }
        }

        return resultadoBFS;
    }

    public List<Cidade> buscaEmProfundidade(Cidade cidadeOrigem) {
        List<Cidade> resultadoDFS = new ArrayList<>();
        Set<Cidade> visitadas = new HashSet<>();
        buscaEmProfundidadeRecursiva(cidadeOrigem, visitadas, resultadoDFS);
        return resultadoDFS;
    }

    private void buscaEmProfundidadeRecursiva(Cidade cidade, Set<Cidade> visitadas, List<Cidade> resultadoDFS) {
        visitadas.add(cidade);
        resultadoDFS.add(cidade);

        for (Cidade vizinho : cidade.vizinhos.keySet()) {
            if (!visitadas.contains(vizinho)) {
                buscaEmProfundidadeRecursiva(vizinho, visitadas, resultadoDFS);
            }
        }
    }
}
