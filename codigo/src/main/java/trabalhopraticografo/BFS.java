import java.util.*;

class BFS {
    private Grafo grafo;

    public BFS(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Cidade> buscaEmLargura(Cidade cidadeOrigem) {
        List<Cidade> resultadoBFS = new ArrayList<>();
        Queue<Cidade> fila = new LinkedList<>();
        Set<Cidade> visitadas = new HashSet<>();

        fila.offer(cidadeOrigem);
        visitadas.add(cidadeOrigem);

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
