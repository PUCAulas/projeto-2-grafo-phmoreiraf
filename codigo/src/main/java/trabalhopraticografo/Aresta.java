package trabalhopraticografo;

public class Aresta {
    Cidade origem;
    Cidade destino;
    int peso;

    public Aresta(Cidade origem, Cidade destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origem.getNome() + " -> " + destino.getNome() + " (Peso: " + peso + ")";
    }
}